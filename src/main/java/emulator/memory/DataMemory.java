package emulator.memory;

import java.util.HashMap;

public class DataMemory {
    // first 48 bits - page number
    // last 16 bits - offset
    private final HashMap<Long, Byte[]> memoryMap = new HashMap<>();

    public void write(long address, long value, String size) {
        // Extract the page number (first 48 bits) and offset (last 16 bits)
        long pageNumber = address >>> 16; // Shift right by 16 bits to get the page number
        int offset = (int) (address & 0xFFFF); // Mask with 0xFFFF to get the last 16 bits (offset)

        // Determine the number of bytes to write based on the size
        int bytesToWrite = mapDataSizeToInt(size);

        // Check if the write operation will cross a page boundary
        if (offset + bytesToWrite > (1 << 16)) {
            // Calculate the number of bytes that fit in the current page
            int bytesInCurrentPage = (1 << 16) - offset;
            int bytesInNextPage = bytesToWrite - bytesInCurrentPage;

            // Write the bytes that fit in the current page
            writeToPage(pageNumber, offset, value, bytesInCurrentPage);

            // Write the remaining bytes to the next page
            writeToPage(pageNumber + 1, 0, value >>> (bytesInCurrentPage * 8), bytesInNextPage);
        } else {
            // Write all bytes to the current page
            writeToPage(pageNumber, offset, value, bytesToWrite);
        }
    }

    private void writeToPage(long pageNumber, int offset, long value, int bytesToWrite) {
        // Ensure the page exists in the memory map
        Byte[] page = memoryMap.computeIfAbsent(pageNumber, k -> new Byte[1 << 16]);

        // Write the specified number of bytes to the page
        for (int i = 0; i < bytesToWrite; i++) {
            page[offset + i] = (byte) ((value >> (i * 8)) & 0xFF);
        }
    }

    public long read(long address, String size) {
        // Extract the page number (first 48 bits) and offset (last 16 bits)
        long pageNumber = address >>> 16; // Shift right by 16 bits to get the page number
        int offset = (int) (address & 0xFFFF); // Mask with 0xFFFF to get the last 16 bits (offset)

        // Determine the number of bytes to read based on the size
        int bytesToRead = mapDataSizeToInt(size);

        // Check if the read operation will cross a page boundary
        if (offset + bytesToRead > (1 << 16)) {
            // Calculate the number of bytes that fit in the current page
            int bytesInCurrentPage = (1 << 16) - offset;
            int bytesInNextPage = bytesToRead - bytesInCurrentPage;

            // Read the bytes from the current page
            long valueFromCurrentPage = readFromPage(pageNumber, offset, bytesInCurrentPage);

            // Read the remaining bytes from the next page
            long valueFromNextPage = readFromPage(pageNumber + 1, 0, bytesInNextPage);

            // Combine the bytes into a single value
            return valueFromCurrentPage | (valueFromNextPage << (bytesInCurrentPage * 8));
        } else {
            // Read all bytes from the current page
            return readFromPage(pageNumber, offset, bytesToRead);
        }
    }

    private long readFromPage(long pageNumber, int offset, int bytesToRead) {
        // Check if the page exists in the memory map
        Byte[] page = memoryMap.get(pageNumber);
        if (page == null) {
            return 0;
        }

        // Read the specified number of bytes from the page
        long value = 0;
        for (int i = 0; i < bytesToRead; i++) {
            byte b = (page[offset + i] != null) ? page[offset + i] : 0; // Handle null bytes
            value |= (b & 0xFFL) << (i * 8);
        }
        return value;
    }

    public int mapDataSizeToInt(String size) {
        switch (size) {
            case "BYTE":
                return 1;
            case "WORD":
                return 2;
            case "DWORD":
                return 4;
            case "QWORD":
                return 8;
            default:
                throw new IllegalArgumentException("Invalid size: " + size);
        }
    }
}
