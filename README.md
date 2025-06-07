# CPU Emulator

A Java-based CPU emulator with support for custom instruction sets, memory management, and multi-level cache simulation. This project is designed for educational purposes, allowing users to experiment with assembly-like instructions, memory operations, and cache replacement policies.

## Features

- **Custom Assembly-like Instruction Set:** Supports MOV, ADD, SUB, MUL, DIV, AND, OR, XOR, NOT, CMP, JMP, JE, JNE, JGE, JL, IN, OUT, HALT, and label instructions.
- **Memory Management:** Implements both data and program memory with support for different data sizes (BYTE, WORD, DWORD, QWORD).
- **Multi-level Cache Simulation:** Configurable cache levels, sizes, associativities, line sizes, and replacement policies (LRU, OPTIMAL).
- **Unit Tests:** Comprehensive JUnit tests for all major components.
- **Instruction File Loader:** Load and execute instructions from text files.

## Project Structure

```
src/
  main/
    java/
      emulator/
        cache/         # Cache and set implementations
        cpu/           # Processor logic
        instructions/   # Instruction parsing and execution
        memory/        # Data and program memory
    resources/
      fileTests/       # Example instruction and expected output files
  test/
    java/
      emulator/        # Unit tests
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Build

Clone the repository and build with Maven:

```sh
git clone https://github.com/danitdrvc/CPU-Emulator.git
cd CPUemulator
mvn clean package
```

### Run

```sh
java -cp target/CPUemulator-1.0-SNAPSHOT.jar emulator.Emulator \
  -f src/main/resources/fileTests/test1.txt \
  -cl 2 \
  -cs 8,8 \
  -ca 2,2 \
  -ls 4 \
  -rp LRU
```

**Options:**

- `-f <file>`: Path to instruction file
- `-cl <levels>`: Number of cache levels
- `-cs <sizes>`: Cache sizes (comma-separated)
- `-ca <associativities>`: Associativities (comma-separated)
- `-ls <line size>`: Cache line size in bytes
- `-rp <policy>`: Replacement policy (`LRU` or `OPTIMAL`)

### Testing

Run all unit tests with:

```sh
mvn test
```

## Example

Instruction files are located in [`src/main/resources/fileTests/`](src/main/resources/fileTests/). Each `.txt` file contains a program, and the corresponding `.expected` file contains the expected register output.

## License

This project is licensed under the MIT License.

---

*Created for educational purposes. Contributions welcome!*
