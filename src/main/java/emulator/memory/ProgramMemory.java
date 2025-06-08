package emulator.memory;

import java.util.ArrayList;

public class ProgramMemory {
    private final ArrayList<String> memoryMap;

    public ProgramMemory() {
        memoryMap = new ArrayList<>();
    }

    public String read(int address) {
        if (address < 0 || address >= memoryMap.size())
            throw new IllegalArgumentException("Invalid address: " + address);
        return memoryMap.get(address);
    }

    public void write(int address, String instruction) {
        if (address >= memoryMap.size())
            throw new IllegalArgumentException("Invalid address: " + address);
        memoryMap.set(address, instruction);
    }

    public void add(String instruction) {
        memoryMap.add(instruction);
    }

    public int size() {
        return memoryMap.size();
    }

    public int getLabelIndex(String label) {
        return memoryMap.indexOf(label + ":");
    }
}
