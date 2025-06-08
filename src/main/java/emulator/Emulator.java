package emulator;

import emulator.cache.CacheSimulator;
import emulator.cpu.Processor;
import emulator.instructions.InstructionParser;
import emulator.memory.DataMemory;
import emulator.memory.ProgramMemory;

import java.util.ArrayList;
import java.util.Arrays;

public class Emulator {
    public static void main(String[] args) {
        // Default
        String instructionFile = "src/main/resources/fileTests/test1.txt";
        int levels = 2;
        int[] cacheSizes = {8, 8};
        int[] associativities = {2, 2};
        int lineSize = 4;
        String replacementPolicy = "LRU";

        // Parse command line arguments
        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-f":
                        instructionFile = args[++i];
                        break;
                    case "-cl":
                        levels = Integer.parseInt(args[++i]);
                        break;
                    case "-cs":
                        String[] sizes = args[++i].split(",");
                        cacheSizes = new int[sizes.length];
                        for (int j = 0; j < sizes.length; j++) {
                            cacheSizes[j] = Integer.parseInt(sizes[j]);
                        }
                        break;
                    case "-ca":
                        String[] assoc = args[++i].split(",");
                        associativities = new int[assoc.length];
                        for (int j = 0; j < assoc.length; j++) {
                            associativities[j] = Integer.parseInt(assoc[j]);
                        }
                        break;
                    case "-ls":
                        lineSize = Integer.parseInt(args[++i]);
                        break;
                    case "-rp":
                        replacementPolicy = args[++i];
                        if (!replacementPolicy.equals("LRU") && !replacementPolicy.equals("OPTIMAL")) {
                            throw new IllegalArgumentException("Replacement policy must be either LRU or OPTIMAL");
                        }
                        break;
                    default:
                        System.err.println("Unknown argument: " + args[i]);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
        }

        // Validate arguments
        if (levels != cacheSizes.length || levels != associativities.length) {
            System.err.println("Number of cache levels (" + levels + ") must match the number of cache sizes ("
                    + cacheSizes.length + ") and associativities (" + associativities.length + ")");
        }

        // Print configuration
        System.out.println("Running emulator with the following configuration:");
        System.out.println("Instruction file: " + instructionFile);
        System.out.println("Cache levels: " + levels);
        System.out.println("Cache sizes: " + Arrays.toString(cacheSizes));
        System.out.println("Associativities: " + Arrays.toString(associativities));
        System.out.println("Line size: " + lineSize);
        System.out.println("Replacement policy: " + replacementPolicy);
        System.out.println();

        // Initialize memory and caches
        DataMemory dataMemory = new DataMemory();
        ProgramMemory programMemory = new ProgramMemory();

        // Initialize processor
        Processor processor = new Processor(dataMemory, programMemory);

        processor.writeToRegister(0, 10, "BYTE");
        processor.writeToRegister(1, 5, "BYTE");
        processor.writeToRegister(2, 0, "BYTE");
        processor.writeToRegister(3, 0, "BYTE");

        // Load and execute instructions
        InstructionParser.parseInstructions(instructionFile, processor);
        processor.executeInstructions();

        // Display final register states
        System.out.println("\nFinal Register Values:");
        for (int i = 0; i < 4; i++) {
            System.out.printf("R%d: %d%n", i, processor.readFromRegister(i, "QWORD"));
        }

        // Cache simulation
        ArrayList<Long> addresses = processor.getAccessedAddresses();
        long[] addressesArray = new long[addresses.size()];
        for (int i = 0; i < addresses.size(); i++) {
            addressesArray[i] = addresses.get(i);
        }

        CacheSimulator simulator = new CacheSimulator(levels, cacheSizes, associativities, lineSize);
        simulator.simulateAccesses(addressesArray, replacementPolicy);
    }
//    java -cp out emulator.Emulator -f src/main/resources/fileTests/test1.txt -cl 2 -cs 8,8 -ca 2,2 -ls 4 -rp LRU

}