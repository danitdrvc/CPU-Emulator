package emulator.instructions;

import emulator.cpu.Processor;
import emulator.memory.DataMemory;
import emulator.memory.ProgramMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructionSetTest {

    private Processor processor;

    @BeforeEach
    void setUp() {
        processor = new Processor(new DataMemory(), new ProgramMemory());
        // Postavljanje početnih vrednosti registara
        processor.writeToRegister(0, 10, "BYTE");
        processor.writeToRegister(1, 5, "BYTE");
        processor.writeToRegister(2, 0, "BYTE");
        processor.writeToRegister(3, 0, "BYTE");
    }

    // --- MOV ---
    @Test
    void mov_RegisterToRegister() {
        // MOV BYTE R2, R0
        String dataSize = "BYTE";
        InstructionSet.mov(processor, dataSize, "R2", "R0");
        assertEquals(10, processor.readFromRegister(2, dataSize));
    }

    @Test
    void mov_RegisterToMemory() {
        // MOV BYTE [0x1000], R0
        String dataSize = "BYTE";
        InstructionSet.mov(processor, dataSize, "[0x1000]", "R0");
        assertEquals(10, processor.readFromDataMemory(0x1000L, dataSize));
    }

    @Test
    void mov_MemoryToRegister() {
        // MOV BYTE R2, [0x1000]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x1000, 7, dataSize);
        InstructionSet.mov(processor, dataSize, "R2", "[0x1000]");
        assertEquals(7, processor.readFromRegister(2, dataSize));
    }

    @Test
    void mov_AddressInRegisterToRegister() {
        // MOV BYTE R2, [R3]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 7, dataSize);
        processor.writeToRegister(3, 0x10, dataSize);
        InstructionSet.mov(processor, dataSize, "R2", "[R3]");
        assertEquals(7, processor.readFromRegister(2, dataSize));
    }

    @Test
    void mov_RegisterToRegisterQWORD() {
        // MOV QWORD R2, R0
        String dataSize = "QWORD";
        InstructionSet.mov(processor, dataSize, "R2", "R0");
        assertEquals(10, processor.readFromRegister(2, dataSize));
    }

    @Test
    void mov_RegisterToMemoryQWORD() {
        // MOV QWORD [0x1000], R0
        String dataSize = "QWORD";
        InstructionSet.mov(processor, dataSize, "[0xFFFD]", "R0");
        assertEquals(10, processor.readFromDataMemory(0xFFFDL, dataSize));
    }

    @Test
    void mov_MemoryToRegisterQWORD() {
        // MOV QWORD R2, [0xFFFD]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0xFFFD, 7, dataSize);
        InstructionSet.mov(processor, dataSize, "R2", "[0xFFFD]");
        assertEquals(7, processor.readFromRegister(2, dataSize));
    }

    @Test
    void mov_AddressInRegisterToRegisterQWORD() {
        // MOV QWORD R2, [R3]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0xFFFD, 7, dataSize);
        processor.writeToRegister(3, 0xFFFD, dataSize);
        InstructionSet.mov(processor, dataSize, "R2", "[R3]");
        assertEquals(7, processor.readFromRegister(2, dataSize));
    }

    // --- ADD ---
    @Test
    void add_RegisterToRegister() {
        // ADD BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.add(processor, dataSize, "R0", "R1");
        assertEquals(15, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_RegisterToMemory() {
        // ADD BYTE [0x10], R1
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.add(processor, dataSize, "[0x10]", "R1");
        assertEquals(20, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void add_MemoryToRegister() {
        // ADD BYTE R0, [0x10]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.add(processor, dataSize, "R0", "[0x10]");
        assertEquals(25, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_ConstantToRegister() {
        // ADD BYTE R0, 7
        String dataSize = "BYTE";
        InstructionSet.add(processor, dataSize, "R0", "7");
        assertEquals(17, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_ConstantToMemory() {
        // ADD BYTE [0x10], 7
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.add(processor, dataSize, "[0x10]", "7");
        assertEquals(22, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void add_RegisterToRegisterQWORD() {
        // ADD QWORD R0, R1
        String dataSize = "QWORD";
        InstructionSet.add(processor, dataSize, "R0", "R1");
        assertEquals(15, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_RegisterToMemoryQWORD() {
        // ADD QWORD [0x123456789], R1
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.add(processor, dataSize, "[0x123456789]", "R1");
        assertEquals(20, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    @Test
    void add_MemoryToRegisterQWORD() {
        // ADD QWORD R0, [0x123456789]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.add(processor, dataSize, "R0", "[0x123456789]");
        assertEquals(25, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_ConstantToRegisterQWORD() {
        // ADD QWORD R0, 7
        String dataSize = "QWORD";
        InstructionSet.add(processor, dataSize, "R0", "7");
        assertEquals(17, processor.readFromRegister(0, dataSize));
    }

    @Test
    void add_ConstantToMemoryQWORD() {
        // ADD QWORD [0x123456789], 7
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.add(processor, dataSize, "[0x123456789]", "7");
        assertEquals(22, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    // --- SUB ---
    @Test
    void sub_RegisterToRegister() {
        // SUB BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.sub(processor, dataSize, "R0", "R1");
        assertEquals(5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_RegisterToMemory() {
        // SUB BYTE [0x10], R1
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.sub(processor, dataSize, "[0x10]", "R1");
        assertEquals(10, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void sub_MemoryToRegister() {
        // SUB BYTE R0, [0x10]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 3, dataSize);
        InstructionSet.sub(processor, dataSize, "R0", "[0x10]");
        assertEquals(7, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_ConstantToRegister() {
        // SUB BYTE R0, 7
        String dataSize = "BYTE";
        InstructionSet.sub(processor, dataSize, "R0", "7");
        assertEquals(3, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_ConstantToMemory() {
        // SUB BYTE [0x10], 7
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.sub(processor, dataSize, "[0x10]", "7");
        assertEquals(8, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void sub_RegisterToRegisterQWORD() {
        // SUB QWORD R0, R1
        String dataSize = "QWORD";
        InstructionSet.sub(processor, dataSize, "R0", "R1");
        assertEquals(5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_RegisterToMemoryQWORD() {
        // SUB QWORD [0x123456789], R1
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.sub(processor, dataSize, "[0x123456789]", "R1");
        assertEquals(10, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    @Test
    void sub_MemoryToRegisterQWORD() {
        // SUB QWORD R0, [0x123456789]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.sub(processor, dataSize, "R0", "[0x123456789]");
        assertEquals(-5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_ConstantToRegisterQWORD() {
        // SUB QWORD R0, 7
        String dataSize = "QWORD";
        InstructionSet.sub(processor, dataSize, "R0", "7");
        assertEquals(3, processor.readFromRegister(0, dataSize));
    }

    @Test
    void sub_ConstantToMemoryQWORD() {
        // SUB QWORD [0x123456789], 7
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.sub(processor, dataSize, "[0x123456789]", "7");
        assertEquals(8, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    // --- MUL ---
    @Test
    void mul_RegisterToRegister() {
        // MUL BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.mul(processor, dataSize, "R0", "R1");
        assertEquals(50, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_RegisterToMemory() {
        // MUL BYTE [0x10], R1
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.mul(processor, dataSize, "[0x10]", "R1");
        assertEquals(75, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void mul_MemoryToRegister() {
        // MUL BYTE R0, [0x10]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 3, dataSize);
        InstructionSet.mul(processor, dataSize, "R0", "[0x10]");
        assertEquals(30, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_ConstantToRegister() {
        // MUL BYTE R0, 7
        String dataSize = "BYTE";
        InstructionSet.mul(processor, dataSize, "R0", "7");
        assertEquals(70, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_ConstantToMemory() {
        // MUL BYTE [0x10], 7
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.mul(processor, dataSize, "[0x10]", "7");
        assertEquals(105, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void mul_RegisterToRegisterQWORD() {
        // MUL QWORD R0, R1
        String dataSize = "QWORD";
        InstructionSet.mul(processor, dataSize, "R0", "R1");
        assertEquals(50, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_RegisterToMemoryQWORD() {
        // MUL QWORD [0x123456789], R1
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.mul(processor, dataSize, "[0x123456789]", "R1");
        assertEquals(75, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    @Test
    void mul_MemoryToRegisterQWORD() {
        // MUL QWORD R0, [0x123456789]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.mul(processor, dataSize, "R0", "[0x123456789]");
        assertEquals(150, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_ConstantToRegisterQWORD() {
        // MUL QWORD R0, 7
        String dataSize = "QWORD";
        InstructionSet.mul(processor, dataSize, "R0", "7");
        assertEquals(70, processor.readFromRegister(0, dataSize));
    }

    @Test
    void mul_ConstantToMemoryQWORD() {
        // MUL QWORD [0x123456789], 7
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.mul(processor, dataSize, "[0x123456789]", "7");
        assertEquals(105, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    // --- DIV ---
    @Test
    void div_RegisterToRegister() {
        // DIV BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.div(processor, dataSize, "R0", "R1");
        assertEquals(2, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_RegisterToMemory() {
        // DIV BYTE [0x10], R1
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.div(processor, dataSize, "[0x10]", "R1");
        assertEquals(3, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void div_MemoryToRegister() {
        // DIV BYTE R0, [0x10]
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 3, dataSize);
        InstructionSet.div(processor, dataSize, "R0", "[0x10]");
        assertEquals(3, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_ConstantToRegister() {
        // DIV BYTE R0, 2
        String dataSize = "BYTE";
        InstructionSet.div(processor, dataSize, "R0", "2");
        assertEquals(5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_ConstantToRegisterZERO() {
        // DIV BYTE R0, 0
        String dataSize = "BYTE";
        assertThrows(ArithmeticException.class, () -> {
            InstructionSet.div(processor, dataSize, "R0", "0");
        });
    }

    @Test
    void div_ConstantToMemory() {
        // DIV BYTE [0x10], 2
        String dataSize = "BYTE";
        processor.writeToDataMemory(0x10, 15, dataSize);
        InstructionSet.div(processor, dataSize, "[0x10]", "2");
        assertEquals(7, processor.readFromDataMemory(0x10, dataSize));
    }

    @Test
    void div_RegisterToRegisterQWORD() {
        // DIV QWORD R0, R1
        String dataSize = "QWORD";
        InstructionSet.div(processor, dataSize, "R0", "R1");
        assertEquals(2, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_RegisterToMemoryQWORD() {
        // DIV QWORD [0x123456789], R1
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.div(processor, dataSize, "[0x123456789]", "R1");
        assertEquals(3, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    @Test
    void div_MemoryToRegisterQWORD() {
        // DIV QWORD R0, [0x123456789]
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 2, dataSize);
        InstructionSet.div(processor, dataSize, "R0", "[0x123456789]");
        assertEquals(5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_ConstantToRegisterQWORD() {
        // DIV QWORD R0, 2
        String dataSize = "QWORD";
        InstructionSet.div(processor, dataSize, "R0", "2");
        assertEquals(5, processor.readFromRegister(0, dataSize));
    }

    @Test
    void div_ConstantToMemoryQWORD() {
        // DIV QWORD [0x123456789], 5
        String dataSize = "QWORD";
        processor.writeToDataMemory(0x123456789L, 15, dataSize);
        InstructionSet.div(processor, dataSize, "[0x123456789]", "5");
        assertEquals(3, processor.readFromDataMemory(0x123456789L, dataSize));
    }

    // --- AND ---
    @Test
    void and_RegisterToRegister() {
        // AND BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.and(processor, dataSize, "R0", "R1");
        assertEquals(0, processor.readFromRegister(0, dataSize));
    }

    // --- OR ---
    @Test
    void or_RegisterToRegister() {
        // OR BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.or(processor, dataSize, "R0", "R1");
        assertEquals(15, processor.readFromRegister(0, dataSize));
    }

    // --- XOR ---
    @Test
    void xor_RegisterToRegister() {
        // XOR BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.xor(processor, dataSize, "R0", "R1");
        assertEquals(15, processor.readFromRegister(0, dataSize));
    }

    // --- NOT ---
    @Test
    void not() {
        // not BYTE R0
        String dataSize = "BYTE";
        InstructionSet.not(processor, dataSize, "R0");
        assertEquals(245, processor.readFromRegister(0, dataSize));
    }

    @Test
    void notQWORD() {
        // not QWORD R0
        String dataSize = "QWORD";
        InstructionSet.not(processor, dataSize, "R0");
        assertEquals(-11, processor.readFromRegister(0, dataSize));
    }

    // --- CMP ---
    @Test
    void cmp_RegisterToRegisterEQUAL() {
        // CMP BYTE R0, R1
        String dataSize = "BYTE";
        processor.writeToRegister(1, 10, dataSize);
        InstructionSet.cmp(processor, dataSize, "R0", "R1");
        assertEquals(0, processor.readFromRegister(3, dataSize));
    }

    @Test
    void cmp_RegisterToRegisterGREATER() {
        // CMP BYTE R0, R1
        String dataSize = "BYTE";
        InstructionSet.cmp(processor, dataSize, "R0", "R1");
        assertTrue(processor.readFromRegister(3, dataSize) > 0);
    }

    @Test
    void cmp_RegisterToRegisterLESS() {
        // CMP BYTE R0, R1
        String dataSize = "BYTE";
        processor.writeToRegister(1, 15, dataSize);
        InstructionSet.cmp(processor, dataSize, "R0", "R1");
        assertTrue((byte) processor.readFromRegister(3, dataSize) < 0);
    }

    // --- JMP ---
    @Test
    void jmp() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test20.txt", processor);
        InstructionSet.jmp(processor, "label");
        assertEquals(4, processor.getProgramCounter());
    }

    // --- JE ---
    @Test
    void jeTRUE() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 0, "BYTE");
        InstructionSet.je(processor, "BYTE", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    @Test
    void jeTRUE_QWORD() {
        // writing QWORD content in R3 but reading content for cmp as BYTE
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, -1, "QWORD");
        InstructionSet.je(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    @Test
    void jeFALSE() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 1, "BYTE");
        InstructionSet.je(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    // --- JNE ---
    @Test
    void jneTRUE() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 0, "BYTE");
        InstructionSet.jne(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    @Test
    void jneFALSE() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 1, "BYTE");
        InstructionSet.jne(processor, "BYTE", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    // --- JGE ---
    @Test
    void jgeGREATER() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 1, "BYTE");
        InstructionSet.jge(processor, "BYTE", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    @Test
    void jgeLOWER() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, -1, "BYTE");
        InstructionSet.jge(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    @Test
    void jgeEQUAL() {
        // if content of R3 is byte, but we read it as qword
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 0, "BYTE");
        InstructionSet.jge(processor, "BYTE", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    @Test
    void jgeGREATER_QWORD() {
        // if content of R3 is byte, but we read it as qword
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, -1, "BYTE");
        InstructionSet.jge(processor, "QWORD", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    // --- JL ---
    @Test
    void jlGREATER() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 1, "BYTE");
        InstructionSet.jl(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    @Test
    void jlLOWER() {
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, -1, "BYTE");
        InstructionSet.jl(processor, "BYTE", "label");
        assertEquals(5, processor.getProgramCounter());
    }

    @Test
    void jlEQUAL() {
        // if content of R3 is byte, but we read it as qword
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, 0, "BYTE");
        InstructionSet.jl(processor, "BYTE", "label");
        assertEquals(1, processor.getProgramCounter());
    }

    @Test
    void jlGREATER_QWORD() {
        // if content of R3 is byte, but we read it as qword
        InstructionParser.parseInstructions("src/main/resources/fileTests/test21.txt", processor);
        processor.writeToRegister(3, -1, "BYTE");
        InstructionSet.jl(processor, "QWORD", "label");
        assertEquals(1, processor.getProgramCounter());
    }
}