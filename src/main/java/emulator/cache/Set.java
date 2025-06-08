package emulator.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Set {
    private final int associativity;
    private final List<Long> blocks;
    private final Map<Long, Integer> accessOrder;

    public Set(int associativity) {
        this.associativity = associativity;
        this.blocks = new ArrayList<>(associativity);
        this.accessOrder = new HashMap<>();
    }

    public boolean containsBlock(long blockAddress) {
        return blocks.contains(blockAddress);
    }

    public void updateLRU(long blockAddress) {
        int currentTime = getCurrentTime();
        accessOrder.put(blockAddress, currentTime);
    }

    public void addBlockLRU(long blockAddress) {
        if (blocks.size() < associativity) {
            // Cache set not full
            blocks.add(blockAddress);
        } else {
            // Need to replace a block - find LRU block
            long lruBlock = findLRUBlock();
            int index = blocks.indexOf(lruBlock);
            blocks.set(index, blockAddress);
            accessOrder.remove(lruBlock);
        }
        // Update access time for the new block
        accessOrder.put(blockAddress, getCurrentTime());
    }

    public void addBlockOptimal(long blockAddress, Map<Long, Integer> nextAccessTimes) {
        if (blocks.size() < associativity) {
            // Cache set not full
            blocks.add(blockAddress);
        } else {
            // Need to replace a block - find the one accessed furthest in the future
            long victimBlock = findOptimalVictim(nextAccessTimes);
            int index = blocks.indexOf(victimBlock);
            blocks.set(index, blockAddress);
        }
    }

    public List<Long> getBlocks() {
        return new ArrayList<>(blocks);
    }

    private long findLRUBlock() {
        long lruBlock = -1;
        int oldestTime = Integer.MAX_VALUE;

        for (long block : blocks) {
            int accessTime = accessOrder.getOrDefault(block, -1);
            if (accessTime < oldestTime) {
                oldestTime = accessTime;
                lruBlock = block;
            }
        }

        return lruBlock;
    }

    private long findOptimalVictim(Map<Long, Integer> nextAccessTimes) {
        long victimBlock = -1;
        int furthestAccess = -1;

        for (long block : blocks) {
            int nextAccess = nextAccessTimes.getOrDefault(block, Integer.MAX_VALUE);
            if (nextAccess > furthestAccess) {
                furthestAccess = nextAccess;
                victimBlock = block;
            }
        }

        return victimBlock;
    }

    private int getCurrentTime() {
        return (int) System.nanoTime();
    }
}
