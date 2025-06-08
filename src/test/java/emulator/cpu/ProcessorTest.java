package emulator.cpu;

import emulator.instructions.InstructionParser;
import emulator.memory.DataMemory;
import emulator.memory.ProgramMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessorTest {

    @Test
    public void testRegisterOperations() {
        DataMemory dataMemory = new DataMemory();
        ProgramMemory programMemory = new ProgramMemory();
        Processor processor = new Processor(dataMemory, programMemory);

        processor.writeToRegister(0, 42, "BYTE");
        assertEquals(42, processor.readFromRegister(0, "BYTE"));

        processor.writeToRegister(1, -10, "BYTE");
        assertEquals(-10, (byte) processor.readFromRegister(1, "BYTE"));
    }

    Processor processor;
    String pathToTestFolder = "src/main/resources/fileTests";

    @BeforeEach
    void setUp() {
        processor = new Processor(new DataMemory(), new ProgramMemory());
        processor.writeToRegister(0, 10, "BYTE");
        processor.writeToRegister(1, 5, "BYTE");
        processor.writeToRegister(2, 0, "BYTE");
        processor.writeToRegister(3, 0, "BYTE");
    }

    private void helperTestMethod(String testFileStringPath, String expectedFileStringPath) {
        InstructionParser.parseInstructions(testFileStringPath, processor);
        processor.executeInstructions();

        try {
            String expectedOutput = Files.readString(new File(expectedFileStringPath).toPath()).trim();
            String actualOutput = Arrays.toString(processor.getAllRegisters());
            assertEquals(expectedOutput, actualOutput);
        } catch (IOException e) {
            System.out.println("File not loaded correctly");
        }
    }

    @Test
    public void test1() {
        String testFileStringPath = pathToTestFolder + File.separator + "test1.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test1.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test2() {
        String testFileStringPath = pathToTestFolder + File.separator + "test2.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test2.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test3() {
        String testFileStringPath = pathToTestFolder + File.separator + "test3.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test3.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test4() {
        String testFileStringPath = pathToTestFolder + File.separator + "test4.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test4.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test5() {
        String testFileStringPath = pathToTestFolder + File.separator + "test5.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test5.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test6() {
        String testFileStringPath = pathToTestFolder + File.separator + "test6.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test6.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test7() {
        String testFileStringPath = pathToTestFolder + File.separator + "test7.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test7.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test8() {
        String testFileStringPath = pathToTestFolder + File.separator + "test8.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test8.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test9() {
        String testFileStringPath = pathToTestFolder + File.separator + "test9.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test9.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test10() {
        String testFileStringPath = pathToTestFolder + File.separator + "test10.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test10.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test11() {
        String testFileStringPath = pathToTestFolder + File.separator + "test11.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test11.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test12() {
        String testFileStringPath = pathToTestFolder + File.separator + "test12.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test12.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test13() {
        String testFileStringPath = pathToTestFolder + File.separator + "test13.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test13.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test14() {
        String testFileStringPath = pathToTestFolder + File.separator + "test14.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test14.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test15() {
        String testFileStringPath = pathToTestFolder + File.separator + "test15.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test15.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test16() {
        String testFileStringPath = pathToTestFolder + File.separator + "test16.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test16.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test17() {
        String testFileStringPath = pathToTestFolder + File.separator + "test17.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test17.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }

    @Test
    public void test18() {
        String testFileStringPath = pathToTestFolder + File.separator + "test18.txt";
        String expectedFileStringPath = pathToTestFolder + File.separator + "test18.expected";
        helperTestMethod(testFileStringPath, expectedFileStringPath);
    }
}