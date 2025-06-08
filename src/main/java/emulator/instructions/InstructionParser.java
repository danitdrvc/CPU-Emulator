package emulator.instructions;

import emulator.cpu.Processor;

import java.io.BufferedReader;
import java.io.FileReader;

public class InstructionParser {

    public static void parseInstructions(String filePath, Processor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                processor.getProgramMemory().add(line);
            }
        } catch (Exception e) {
            System.out.println("File not loaded correctly");
        }
    }

    public static void processInstruction(String line, Processor processor) {
        String[] parts = line.split("\\s+");
        String instruction = parts[0].toUpperCase();

        switch (instruction) {
            case "MOV":
                handleMOV(parts, processor);
                break;
            case "ADD":
                handleADD(parts, processor);
                break;
            case "SUB":
                handleSUB(parts, processor);
                break;
            case "MUL":
                handleMUL(parts, processor);
                break;
            case "DIV":
                handleDIV(parts, processor);
                break;
            case "AND":
                handleAND(parts, processor);
                break;
            case "OR":
                handleOR(parts, processor);
                break;
            case "XOR":
                handleXOR(parts, processor);
                break;
            case "NOT":
                handleNOT(parts, processor);
                break;
            case "CMP":
                handleCMP(parts, processor);
                break;
            case "JMP":
                handleJMP(parts, processor);
                break;
            case "JE":
                handleJE(parts, processor);
                break;
            case "JNE":
                handleJNE(parts, processor);
                break;
            case "JGE":
                handleJGE(parts, processor);
                break;
            case "JL":
                handleJL(parts, processor);
                break;
            case "IN":
                handleIN(parts, processor);
                break;
            case "OUT":
                handleOUT(parts, processor);
                break;
            case "HALT":
                handleHALT(processor);
                break;
            default:
                // Check if the instruction is a label
                if (instruction.matches("\\w+:")) {
                    handleLabel(processor);
                    break;
                } else {
                    System.out.println("Unknown instruction: " + instruction);
                }
                break;
        }
    }

    private static void handleMOV(String[] parts, Processor processor) {
        // MOV <dataSize> <destination>, <source>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("MOV expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("MOV expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.mov(processor, parts[1], noComma, parts[3]);
    }

    private static void handleADD(String[] parts, Processor processor) {
        // ADD <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("ADD expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("ADD expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.add(processor, parts[1], noComma, parts[3]);
    }

    private static void handleSUB(String[] parts, Processor processor) {
        // SUB <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("SUB expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("SUB expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.sub(processor, parts[1], noComma, parts[3]);
    }

    private static void handleMUL(String[] parts, Processor processor) {
        // MUL <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("MUL expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("MUL expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.mul(processor, parts[1], noComma, parts[3]);
    }

    private static void handleDIV(String[] parts, Processor processor) {
        // DIV <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("DIV expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("DIV expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.div(processor, parts[1], noComma, parts[3]);
    }

    private static void handleAND(String[] parts, Processor processor) {
        // AND <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("AND expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("AND expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.and(processor, parts[1], noComma, parts[3]);
    }

    private static void handleOR(String[] parts, Processor processor) {
        // OR <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("OR expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("OR expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.or(processor, parts[1], noComma, parts[3]);
    }

    private static void handleXOR(String[] parts, Processor processor) {
        // XOR <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("XOR expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("XOR expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.xor(processor, parts[1], noComma, parts[3]);
    }

    private static void handleNOT(String[] parts, Processor processor) {
        // NOT <dataSize> <op>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 3) {
            throw new IllegalArgumentException("NOT expects 1 operand");
        }
        InstructionSet.not(processor, parts[1], parts[2]);
    }

    private static void handleCMP(String[] parts, Processor processor) {
        // CMP <dataSize> <op1>, <op2>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 4) {
            throw new IllegalArgumentException("CMP expects 2 operands");
        }
        if (!parts[2].endsWith(",")) {
            throw new IllegalArgumentException("CMP expects a comma separated list");
        }
        String noComma = parts[2].substring(0, parts[2].length() - 1);
        InstructionSet.cmp(processor, parts[1], noComma, parts[3]);
    }

    private static void handleJMP(String[] parts, Processor processor) {
        // JMP <label>

        if (parts.length != 2) {
            throw new IllegalArgumentException("JMP expects 1 operand");
        }
        InstructionSet.jmp(processor, parts[1]);
    }

    private static void handleJE(String[] parts, Processor processor) {
        // JE <dataSize> <label>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 3) {
            throw new IllegalArgumentException("JE expects 2 operands");
        }
        InstructionSet.je(processor, parts[1], parts[2]);
    }


    private static void handleJNE(String[] parts, Processor processor) {
        // JNE <dataSize> <label>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 3) {
            throw new IllegalArgumentException("JNE expects 2 operand");
        }
        InstructionSet.jne(processor, parts[1], parts[2]);
    }

    private static void handleJGE(String[] parts, Processor processor) {
        // JGE <dataSize> <label>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 3) {
            throw new IllegalArgumentException("JGE expects 2 operand");
        }
        InstructionSet.jge(processor, parts[1], parts[2]);
    }

    private static void handleJL(String[] parts, Processor processor) {
        // JGE <dataSize> <label>

        if (!(parts[1].equals("BYTE") || parts[1].equals("WORD") || parts[1].equals("DWORD") || parts[1].equals("QWORD"))) {
            throw new IllegalArgumentException("Data size not valid");
        }
        if (parts.length != 3) {
            throw new IllegalArgumentException("JL expects 2 operand");
        }
        InstructionSet.jl(processor, parts[1], parts[2]);
    }

    private static void handleIN(String[] parts, Processor processor) {
        // IN <op>
        if (parts.length != 2) {
            throw new IllegalArgumentException("IN expects 1 operand");
        }
        InstructionSet.in(processor, parts[1]);
    }

    private static void handleOUT(String[] parts, Processor processor) {
        // OUT <op>
        if (parts.length != 2) {
            throw new IllegalArgumentException("OUT expects 1 operand");
        }
        InstructionSet.out(processor, parts[1]);
    }

    private static void handleHALT(Processor processor) {
        InstructionSet.halt(processor);
    }

    private static void handleLabel(Processor processor) { InstructionSet.label(processor); }
}
