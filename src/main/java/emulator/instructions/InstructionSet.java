package emulator.instructions;

import emulator.cpu.Processor;

import java.io.IOException;

public class InstructionSet {
    public static void mov(Processor processor, String dataSize, String dest, String src) {
        // MOV <dataSize> <destination>, <source>

        // source can be a register, value, [address] and [register]
        long srcLong = getValueFromOperand(src, processor, dataSize);

        // destination can be a register, [address] and [register]
        if (dest.matches("R\\d")) {
            processor.writeToRegister(Integer.parseInt(dest.substring(1)), srcLong, dataSize);
        } else if (dest.matches("\\[0x[0-9A-Fa-f]+]")) {
            processor.writeToDataMemory(Long.parseLong(dest.substring(3, dest.length()-1), 16), srcLong, dataSize);
        } else if (dest.matches("\\[R\\d]")) {
            processor.writeToDataMemory(processor.readFromRegister(Integer.parseInt(dest.substring(2, dest.length()-1)), dataSize), srcLong, dataSize);
        } else {
            System.out.println("First operand of MOV not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void add(Processor processor, String dataSize, String op1, String op2) {
        // ADD <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot add two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, value + processor.readFromRegister(indexOp1, dataSize), dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, value + processor.readFromDataMemory(address, dataSize), dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, value + processor.readFromDataMemory(address, dataSize), dataSize);

        } else {
            System.out.println("First operand of ADD not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void sub(Processor processor, String dataSize, String op1, String op2) {
        // SUB <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot sub two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) - value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) - value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) - value, dataSize);

        } else {
            System.out.println("First operand of SUB not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void mul(Processor processor, String dataSize, String op1, String op2) {
        // MUL <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot mul two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) * value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) * value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) * value, dataSize);

        } else {
            System.out.println("First operand of MUL not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void div(Processor processor, String dataSize, String op1, String op2) {
        // DIV <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot div two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);
        if (value == 0L) {
            throw new ArithmeticException("Division by zero");
        }

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) / value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) / value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) / value, dataSize);

        } else {
            System.out.println("First operand of DIV not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void and(Processor processor, String dataSize, String op1, String op2) {
        // AND <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot logical and two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) & value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) & value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) & value, dataSize);

        } else {
            System.out.println("First operand of AND not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void or(Processor processor, String dataSize, String op1, String op2) {
        // OR <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot logical or two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) | value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) | value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) | value, dataSize);

        } else {
            System.out.println("First operand of OR not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    // XOR instruction
    public static void xor(Processor processor, String dataSize, String op1, String op2) {
        // XOR <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot logical xor two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            processor.writeToRegister(indexOp1, processor.readFromRegister(indexOp1, dataSize) ^ value, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) ^ value, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            processor.writeToDataMemory(address, processor.readFromDataMemory(address, dataSize) ^ value, dataSize);

        } else {
            System.out.println("First operand of XOR not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    // NOT instruction
    public static void not(Processor processor, String dataSize, String op) {
        // NOT <dataSize> <op>

        // op1 can be a register, [address] and [register]
        if (op.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op.substring(1));
            processor.writeToRegister(indexOp1, ~processor.readFromRegister(indexOp1, dataSize), dataSize);

        } else if (op.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op.substring(3, op.length()-1), 16);
            processor.writeToDataMemory(address, ~processor.readFromDataMemory(address, dataSize), dataSize);

        } else if (op.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op.substring(2, op.length()-1)), dataSize);
            processor.writeToDataMemory(address, ~processor.readFromDataMemory(address, dataSize), dataSize);

        } else {
            System.out.println("Operand of NOT not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void cmp(Processor processor, String dataSize, String op1, String op2) {
        // CMP <dataSize> <op1>, <op2>

        if (op1.startsWith("[") && op2.startsWith("[")) {
            throw new IllegalArgumentException("Cannot cmp two memory locations");
        }

        // op2 can be a register, value, [address] and [register]
        long value = getValueFromOperand(op2, processor, dataSize);

        // op1 can be a register, [address] and [register]
        if (op1.matches("R\\d")) {
            int indexOp1 = Integer.parseInt(op1.substring(1));
            long valueOfOp1 = processor.readFromRegister(indexOp1, dataSize);
            long comp = compareBasedOnType(valueOfOp1, value, dataSize);
            processor.writeToRegister(3, comp, dataSize);

        } else if (op1.matches("\\[0x[0-9A-Fa-f]+]")) {
            long address = Long.parseLong(op1.substring(3, op1.length()-1), 16);
            long valueOfOp1 = processor.readFromDataMemory(address, dataSize);
            long comp = compareBasedOnType(valueOfOp1, value, dataSize);
            processor.writeToRegister(3, comp, dataSize);

        } else if (op1.matches("\\[R\\d]")) {
            long address = processor.readFromRegister(Integer.parseInt(op1.substring(2, op1.length()-1)), dataSize);
            long valueOfOp1 = processor.readFromDataMemory(address, dataSize);
            long comp = compareBasedOnType(valueOfOp1, value, dataSize);
            processor.writeToRegister(3, comp, dataSize);

        } else {
            System.out.println("First operand of CMP not correct");
        }
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    public static void jmp(Processor processor, String label) {
        //JMP <label>

        int labelIndex = processor.getLabelIndex(label);
        if (labelIndex == -1) {
            throw new IllegalArgumentException("Label " + label + " not found");
        }

        processor.setProgramCounter(labelIndex+1);
    }

    // Conditional jump JE
    public static void je(Processor processor, String dataSize, String label) {
        //JE <dataSize> <label>

        if (processor.readFromRegister(3, dataSize) == 0) {
            jmp(processor, label);
        } else {
            processor.setProgramCounter(processor.getProgramCounter() + 1);
        }
    }

    // Conditional jump JNE
    public static void jne(Processor processor, String dataSize, String label) {
        // JNE <dataSize> <label>

        if (processor.readFromRegister(3, dataSize) != 0) {
            jmp(processor, label);
        } else {
            processor.setProgramCounter(processor.getProgramCounter() + 1);
        }
    }

    // Conditional jump JGE
    public static void jge(Processor processor, String dataSize, String label) {
        // JGE <dataSize> <label>

        switch (dataSize) {
            case "BYTE" -> {
                if ((processor.readFromRegister(3, dataSize) >> 7) % 2 == 0) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            case "WORD" -> {
                if ((processor.readFromRegister(3, dataSize) >> 15) % 2 == 0) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            case "DWORD" -> {
                if ((processor.readFromRegister(3, dataSize) >> 31) % 2 == 0) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            default -> {
                if ((processor.readFromRegister(3, dataSize) >> 63) % 2 == 0) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
        }
    }

    // Conditional jump JL
    public static void jl(Processor processor, String dataSize, String label) {
        // JL <dataSize> <label>

        switch (dataSize) {
            case "BYTE" -> {
                if ((processor.readFromRegister(3, dataSize) >> 7) % 2 == 1) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            case "WORD" -> {
                if ((processor.readFromRegister(3, dataSize) >> 15) % 2 == 1) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            case "DWORD" -> {
                if ((processor.readFromRegister(3, dataSize) >> 31) % 2 == 1) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
            default -> {
                if (processor.readFromRegister(3, dataSize) < 0) {
                    jmp(processor, label);
                } else {
                    processor.setProgramCounter(processor.getProgramCounter() + 1);
                }
            }
        }
    }

    public static void in(Processor processor, String register) {
        // IN <register>

        System.out.print("Enter a character: ");
        try {
            char inputChar = (char) System.in.read();
            System.in.read(); // To consume the newline
            processor.writeToRegister(Integer.parseInt(register.substring(1)), (long) inputChar, "QWORD");
            processor.setProgramCounter(processor.getProgramCounter() + 1);
        } catch (IOException e) {
            System.out.println("Problem with input");
        }
    }
    public static void out(Processor processor, String register) {
        // OUT <register>

        System.out.print((char) processor.readFromRegister(Integer.parseInt(register.substring(1)), "QWORD"));
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    // HALT instruction
    public static void halt(Processor processor) {
        System.out.println("Processor halted.");
        processor.setProgramCounter(processor.getProgramMemory().size());
    }

    // LABEL instruction
    public static void label(Processor processor) {
        processor.setProgramCounter(processor.getProgramCounter() + 1);
    }

    private static long getValueFromOperand(String operand, Processor processor, String dataSize) {
        long srcLong = 0L;
        if (operand.matches("R\\d")) {
            srcLong = processor.readFromRegister(Integer.parseInt(operand.substring(1)), dataSize);
        } else if (operand.matches("\\[0x[0-9A-Fa-f]+]")) {
            srcLong = processor.readFromDataMemory(Long.parseLong(operand.substring(3, operand.length()-1), 16), dataSize);
        } else if (operand.matches("\\[R\\d]")) {
            srcLong = processor.readFromDataMemory(processor.readFromRegister(Integer.parseInt(operand.substring(2, operand.length()-1)), dataSize), dataSize);
        } else if (operand.matches("\\d+")) {
            srcLong = Long.parseLong(operand);
        } else {
            System.out.println("Operand not correct: " + operand);
        }
        return srcLong;
    }

    private static int compareBasedOnType(long value1, long value2, String dataSize) {
        long mask;
        switch (dataSize) {
            case "BYTE":
                mask = 0xFFL; // Mask for 8 bits (1 byte)
                break;
            case "WORD":
                mask = 0xFFFFL; // Mask for 16 bits (2 bytes)
                break;
            case "DWORD":
                mask = 0xFFFFFFFFL; // Mask for 32 bits (4 bytes)
                break;
            case "QWORD":
                mask = 0xFFFFFFFFFFFFFFFFL; // Mask for 64 bits (8 bytes)
                break;
            default:
                throw new IllegalArgumentException("Invalid dataSize: " + dataSize);
        }

        // Apply the mask to both values
        long maskedValue1 = value1 & mask;
        long maskedValue2 = value2 & mask;

        // Compare the masked values
        if (maskedValue1 < maskedValue2) {
            return -1;
        } else if (maskedValue1 == maskedValue2) {
            return 0;
        } else {
            return 1;
        }
    }
}
