package emulator.cpu;

import emulator.instructions.InstructionParser;
import emulator.memory.DataMemory;
import emulator.memory.ProgramMemory;

import java.util.ArrayList;

public class Processor {
    private final long[] registers;
    private long programCounter;
    private final DataMemory dataMemory;
    private final ProgramMemory programMemory;

    private final ArrayList<Long> accessedAddresses = new ArrayList<>();

    public Processor(DataMemory dataMemory, ProgramMemory programMemory) {
        this.registers = new long[4];
        this.programCounter = 0;
        this.dataMemory = dataMemory;
        this.programMemory = programMemory;
    }

    public long readFromDataMemory(long address, String dataSize) {
        accessedAddresses.add(address);
        return dataMemory.read(address, dataSize);
    }

    public void writeToDataMemory(long address, long value, String dataSize) {
        dataMemory.write(address, value, dataSize);
    }

    public void writeToRegister(int index, long value, String dataSize){
        validateRegisterIndex(index);

        int size = dataMemory.mapDataSizeToInt(dataSize);

        if (size == 1){
            registers[index] = registers[index] & 0xFFFFFFFFFFFFFF00L | (value & 0xFF);
        }
        else if (size == 2){
            registers[index] = registers[index] & 0xFFFFFFFFFFFF0000L | (value & 0xFFFF);
        }
        else if (size == 4){
            registers[index] = registers[index] & 0xFFFFFFFF00000000L | (value & 0xFFFFFFFFL);
        }
        else if (size == 8){
            registers[index] = value;
        }
        else {
            throw new IllegalStateException("Invalid size: " + size);
        }
    }

    public long readFromRegister(int index, String dataSize){
        validateRegisterIndex(index);

        int size = dataMemory.mapDataSizeToInt(dataSize);

        if (size == 1){
            return registers[index] & 0xFF;
        }
        else if (size == 2){
            return registers[index] & 0xFFFF;
        }
        else if (size == 4){
            return registers[index] & 0xFFFFFFFFL;
        }
        else if (size == 8){
            return registers[index];
        }
        else {
            throw new IllegalStateException("Invalid size: " + size);
        }
    }

    private void validateRegisterIndex(int index) {
        if (index < 0 || index >= registers.length) {
            throw new IllegalArgumentException("Invalid register index: " + index);
        }
    }

    public void executeInstructions() {
        while(programCounter < programMemory.size()) {
            InstructionParser.processInstruction(programMemory.read((int) programCounter), this);
        }
    }

    public long[] getAllRegisters() {
        return new long[]{
                readFromRegister(0, "QWORD"),
                readFromRegister(1, "QWORD"),
                readFromRegister(2, "QWORD"),
                readFromRegister(3, "QWORD")
        };
    }

    public ProgramMemory getProgramMemory() {
        return programMemory;
    }

    public long getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(long programCounter) {
        this.programCounter = programCounter;
    }

    public int getLabelIndex(String label) {
        return programMemory.getLabelIndex(label);
    }

    public ArrayList<Long> getAccessedAddresses() {
        return accessedAddresses;
    }
}

