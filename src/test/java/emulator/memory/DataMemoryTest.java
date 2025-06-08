package emulator.memory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataMemoryTest {
    @Test
    public void testWriteRead1() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0x1000, 0xAA, "BYTE");
        assertEquals(0xAAL, dataMemory.read(0x1000, "BYTE"));
    }

    @Test
    public void testWriteRead2() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0x2000, 0xAABB, "WORD");
        assertEquals(0xaabbL, dataMemory.read(0x2000, "WORD"));
    }

    @Test
    public void testWriteRead3() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0x3000, 0xAABBCCDDL, "DWORD");
        assertEquals(0xaabbccddL, dataMemory.read(0x3000, "DWORD"));
    }

    @Test
    public void testWriteRead4() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0x4000, 0xAABBCCDDEEFF1122L, "QWORD");
        assertEquals(0xaabbccddeeff1122L, dataMemory.read(0x4000, "QWORD"));
    }

    @Test
    public void testWriteRead5() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0xFFFF, 0xAABB, "WORD");
        assertEquals(0xaabbL, dataMemory.read(0xFFFF, "WORD"));
    }

    @Test
    public void testWriteRead6() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0xFFFC, 0xAABBCCDDL, "DWORD");
        assertEquals(0xaabbccddL, dataMemory.read(0xFFFC, "DWORD"));
    }

    @Test
    public void testWriteRead7() {
        DataMemory dataMemory = new DataMemory();
        dataMemory.write(0xFFF8, 0xAABBCCDDEEFF1122L, "QWORD");
        assertEquals(0xaabbccddeeff1122L, dataMemory.read(0xFFF8, "QWORD"));
    }
}