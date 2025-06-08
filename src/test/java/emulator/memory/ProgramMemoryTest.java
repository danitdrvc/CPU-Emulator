package emulator.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProgramMemoryTest {
    ProgramMemory programMemory;

    @BeforeEach
    void setUp() {
        programMemory = new ProgramMemory();
        programMemory.add("Instruction1");
        programMemory.add("Instruction2");
        programMemory.add("Instruction3");
    }

    @Test
    void testReadValidAddress() {
        assertEquals("Instruction1", programMemory.read(0));
        assertEquals("Instruction2", programMemory.read(1));
        assertEquals("Instruction3", programMemory.read(2));
    }

    @Test
    void testReadInvalidAddressThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            programMemory.read(3); // Address out of bounds
        });
        assertEquals("Invalid address: 3", exception.getMessage());
    }

    @Test
    void testReadNegativeAddressThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            programMemory.read(-1); // Negative address
        });
        assertEquals("Invalid address: -1", exception.getMessage());
    }
}