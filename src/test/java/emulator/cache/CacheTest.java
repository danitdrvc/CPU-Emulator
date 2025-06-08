package emulator.cache;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    // Cache tests
    @Test
    public void testCacheCreation() {
        Cache cache = new Cache(8, 2, 64);
        assertEquals(64, cache.getLineSize());

        long blockAddress1 = 0; // Maps to set 0
        long blockAddress2 = 4; // Maps to set 0 (4 % 4 = 0)
        long blockAddress3 = 1; // Maps to set 1

        assertFalse(cache.containsBlock(blockAddress1));
        cache.addBlockLRU(blockAddress1);
        assertTrue(cache.containsBlock(blockAddress1));

        cache.addBlockLRU(blockAddress2);
        assertTrue(cache.containsBlock(blockAddress2));

        cache.addBlockLRU(blockAddress3);
        assertTrue(cache.containsBlock(blockAddress3));

        List<Long> blocks = cache.getCacheBlocks();
        assertEquals(3, blocks.size());
        assertTrue(blocks.contains(blockAddress1));
        assertTrue(blocks.contains(blockAddress2));
        assertTrue(blocks.contains(blockAddress3));
    }

    @Test
    public void testCacheLRUReplacement() {
        // Create a small cache with 2 sets and 2-way associativity
        Cache cache = new Cache(4, 2, 64);

        // Add blocks to set 0
        long block1 = 0; // Maps to set 0
        long block2 = 2; // Maps to set 0 (2 % 2 = 0)
        long block3 = 4; // Maps to set 0 (4 % 2 = 0)

        cache.addBlockLRU(block1);
        cache.addBlockLRU(block2);

        // Both blocks should be in the cache
        assertTrue(cache.containsBlock(block1));
        assertTrue(cache.containsBlock(block2));

        // Access block1 to make it MRU
        cache.updateLRU(block1);

        // Add another block to set 0, should evict block2 (LRU)
        cache.addBlockLRU(block3);

        // Check that block1 is still in cache but block2 is evicted
        assertTrue(cache.containsBlock(block1));
        assertFalse(cache.containsBlock(block2));
        assertTrue(cache.containsBlock(block3));
    }

    @Test
    public void testCacheOptimalReplacement() {
        // Create a small cache with 2 sets and 2-way associativity
        Cache cache = new Cache(4, 2, 64);

        // Add blocks to set 0
        long block1 = 0; // Maps to set 0
        long block2 = 2; // Maps to set 0 (2 % 2 = 0)
        long block3 = 4; // Maps to set 0 (4 % 2 = 0)

        cache.addBlockLRU(block1);
        cache.addBlockLRU(block2);

        // Create a map of next access times
        Map<Long, Integer> nextAccessTimes = new HashMap<>();
        nextAccessTimes.put(block1, 10); // block1 will be accessed at time 10
        nextAccessTimes.put(block2, 5);  // block2 will be accessed at time 5

        // Add block3 using OPTIMAL policy - should evict block1 since it's accessed further in future
        cache.addBlockOptimal(block3, nextAccessTimes);

        // Check that block2 is still in cache but block1 is evicted
        assertFalse(cache.containsBlock(block1));
        assertTrue(cache.containsBlock(block2));
        assertTrue(cache.containsBlock(block3));
    }

    // Set tests
    @Test
    public void testSetOperations() {
        Set set = new Set(2); // 2-way associative set

        // Test contains and add
        assertFalse(set.containsBlock(1));
        set.addBlockLRU(1);
        assertTrue(set.containsBlock(1));

        // Test LRU replacement
        set.addBlockLRU(2);
        set.updateLRU(1); // Make 1 most recently used
        set.addBlockLRU(3); // Should evict 2

        assertTrue(set.containsBlock(1));
        assertFalse(set.containsBlock(2));
        assertTrue(set.containsBlock(3));

        // Test getBlocks
        List<Long> blocks = set.getBlocks();
        assertEquals(2, blocks.size());
        assertTrue(blocks.contains(1L));
        assertTrue(blocks.contains(3L));
    }

    @Test
    public void testSetOptimalReplacement() {
        Set set = new Set(2); // 2-way associative set

        // Add initial blocks
        set.addBlockLRU(1);
        set.addBlockLRU(2);

        // Set up next access times
        Map<Long, Integer> nextAccessTimes = new HashMap<>();
        nextAccessTimes.put(1L, 100); // Block 1 accessed far in future
        nextAccessTimes.put(2L, 5);   // Block 2 accessed soon

        // Add new block with OPTIMAL policy
        set.addBlockOptimal(3, nextAccessTimes);

        // Should have evicted block 1
        assertFalse(set.containsBlock(1));
        assertTrue(set.containsBlock(2));
        assertTrue(set.containsBlock(3));
    }

    // CacheSimulator tests
    @Test
    public void testCacheSimulatorLRU() {
        // Create a simulator with 2 levels
        int[] sizes = {4, 8}; // 4 lines L1, 8 lines L2
        int[] associativities = {2, 4}; // 2-way L1, 4-way L2
        int lineSize = 64;

        CacheSimulator simulator = new CacheSimulator(2, sizes, associativities, lineSize);

        // Create a sequence of memory accesses
        // Addresses are in bytes, will be converted to block addresses
        long[] addresses = {
                0,      // Miss in both caches
                64,     // Miss in both caches
                0,      // Hit in L1
                128,    // Miss in both caches
                192,    // Miss in both caches
                0,      // Hit in L1
                64      // Hit in L1
        };

        // Redirect System.out to capture output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        simulator.simulateAccesses(addresses, "LRU");

        // Restore System.out
        System.setOut(System.out);

        String output = outContent.toString();
        assertTrue(output.contains("L1 Cache - Accesses: 7, Misses: 4"));
        assertTrue(output.contains("L2 Cache - Accesses: 4, Misses: 4"));
    }

    @Test
    public void testCacheSimulatorOptimal() {
        // Create a simulator with 1 level
        int[] sizes = {4}; // 4 lines L1
        int[] associativities = {2}; // 2-way L1
        int lineSize = 64;

        CacheSimulator simulator = new CacheSimulator(1, sizes, associativities, lineSize);

        // Create a sequence of memory accesses designed to test OPTIMAL
        // Will convert to block addresses in the simulator
        long[] addresses = {
                0,      // Block 0
                64,     // Block 1
                0,      // Block 0 again (soon)
                128,    // Block 2
                192,    // Block 3
                64,     // Block 1 again (soon)
                256,    // Block 4 - evicts block 2 or 3 (furthest access)
                0       // Block 0 again
        };

        // Redirect System.out to capture output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        simulator.simulateAccesses(addresses, "OPTIMAL");

        // Restore System.out
        System.setOut(System.out);

        String output = outContent.toString();
        // With optimal replacement, we expect fewer misses than LRU
        assertTrue(output.contains("L1 Cache - Accesses: 8, Misses: 5"));
    }
}