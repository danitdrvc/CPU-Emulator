package emulator.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cache {
    private final int size; // Number of cache lines
    private final int associativity;
    private final int lineSize; // In bytes
    private final int numSets;

    private final List<Set> sets;

    public Cache(int size, int associativity, int lineSize) {
        this.size = size;
        this.associativity = associativity;
        this.lineSize = lineSize;
        this.numSets = size / associativity;

        sets = new ArrayList<>(numSets);
        for (int i = 0; i < numSets; i++) {
            sets.add(new Set(associativity));
        }
    }

    public boolean containsBlock(long blockAddress) {
        long setIndex = getSetIndex(blockAddress);
        return sets.get((int)setIndex).containsBlock(blockAddress);
    }

    public void updateLRU(long blockAddress) {
        long setIndex = getSetIndex(blockAddress);
        sets.get((int)setIndex).updateLRU(blockAddress);
    }

    public void addBlockLRU(long blockAddress) {
        long setIndex = getSetIndex(blockAddress);
        sets.get((int)setIndex).addBlockLRU(blockAddress);
    }

    public void addBlockOptimal(long blockAddress, Map<Long, Integer> nextAccessTimes) {
        long setIndex = getSetIndex(blockAddress);
        sets.get((int)setIndex).addBlockOptimal(blockAddress, nextAccessTimes);
    }

    public List<Long> getCacheBlocks() {
        List<Long> allBlocks = new ArrayList<>();
        for (Set set : sets) {
            allBlocks.addAll(set.getBlocks());
        }
        return allBlocks;
    }

    private long getSetIndex(long blockAddress) {
        return blockAddress % numSets;
    }

    public int getLineSize() {
        return lineSize;
    }
}
