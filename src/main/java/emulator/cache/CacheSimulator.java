package emulator.cache;

import java.util.*;

public class CacheSimulator {
    private final int numLevels;
    private final Cache[] caches;

    public CacheSimulator(int levels, int[] cacheSizes, int[] associativities, int lineSize) {
        this.numLevels = levels;
        this.caches = new Cache[numLevels];

        for (int i = 0; i < numLevels; i++) {
            caches[i] = new Cache(cacheSizes[i], associativities[i], lineSize);
        }
    }

    public void simulateAccesses(long[] addresses, String replacementPolicy) {
        // If using OPTIMAL preprocess the future accesses
        Map<Long, List<Integer>> futureAccesses = null;
        if (replacementPolicy.equals("OPTIMAL")) {
            futureAccesses = new HashMap<>();
            for (int i = 0; i < addresses.length; i++) {
                long blockAddress = addresses[i] / caches[0].getLineSize();
                if (!futureAccesses.containsKey(blockAddress)) {
                    futureAccesses.put(blockAddress, new ArrayList<>());
                }
                futureAccesses.get(blockAddress).add(i);
            }

            // Process the lists to remove past accesses during simulation
            for (List<Integer> accessList : futureAccesses.values()) {
                Collections.sort(accessList);
            }
        }

        int[] accesses = new int[numLevels];
        int[] misses = new int[numLevels];

        for (int i = 0; i < addresses.length; i++) {
            long address = addresses[i];
            long blockAddress = address / caches[0].getLineSize();

            for (int level = 0; level < numLevels; level++) {
                accesses[level]++;

                if (caches[level].containsBlock(blockAddress)) {
                    // Update access history for LRU
                    if (replacementPolicy.equals("LRU")) {
                        caches[level].updateLRU(blockAddress);
                    }
                    break;
                } else {
                    misses[level]++;

                    // Need to add block to this cache level
                    if (replacementPolicy.equals("LRU")) {
                        caches[level].addBlockLRU(blockAddress);
                    } else if (replacementPolicy.equals("OPTIMAL")) {
                        // Calculate next access time for each block
                        Map<Long, Integer> nextAccessTimes = new HashMap<>();
                        for (long existingBlock : caches[level].getCacheBlocks()) {
                            List<Integer> future = futureAccesses.get(existingBlock);
                            int nextAccess = Integer.MAX_VALUE;

                            if (future != null) {
                                for (int accessPos : future) {
                                    if (accessPos > i) {
                                        nextAccess = accessPos;
                                        break;
                                    }
                                }
                            }

                            nextAccessTimes.put(existingBlock, nextAccess);
                        }

                        caches[level].addBlockOptimal(blockAddress, nextAccessTimes);
                    }
                }
            }
        }

        // Print miss rates
        System.out.println("Cache Simulation Results:");
        System.out.println("Replacement Policy: " + replacementPolicy);
        for (int i = 0; i < numLevels; i++) {
            double missRate = (double) misses[i] / accesses[i] * 100;
            System.out.printf("L%d Cache - Accesses: %d, Misses: %d, Miss Rate: %.2f%%\n",
                    i+1, accesses[i], misses[i], missRate);
        }
    }
}