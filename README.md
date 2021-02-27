# BF

A [brainfuck](https://esolangs.org/wiki/Brainfuck) interpreter library.

## Usage

To execute brainfuck code, create an instance of `BF` and pass the code as a `String` into the instance's `run` method.
An instance of `BF` requires an `Executor` and a `Looper`. The standard implementations are the `BFExecutor` and
`BFLooper` classes. To instantiate a `BFExecutor`, implementations of the `Input`, `Output`, and `Memory` interfaces
are required.

The only provided `Input` implementation is the `NullInput`, which immediately returns a `null` input (`0x00`) whenever
input is read.

The only provided `Output` implementation is `ConsoleOutput`, which prints directly to the system console.

There are two provided abstract base `Memory` classes for extension, and two concrete implementations of those base
classes. The concrete implementations are `Unsigned8BitMemory`, `Unsigned16BitMemory`, `Signed8BitMemory`, and
`Signed16BitMemory`, all of which are extensions of the abstract `HashMapMemory` class, which is, as its name suggests,
a `HashMap` based implementation. The `Unsigned8BitMemory` and `Unsigned16BitMemory` classes provide memory that store
unsigned 8-bit (0 to 255) or 16-bit (0 to 65,535) memory values, while the `Signed8BitMemory` and `Signed16BitMemory`
classes provide memory that store signed 8-bit (-127 to 128) or 16-bit (-32,768 to 32,767) memory values. Both of the
8-bit memory classes have a capacity of 30,000 memory cells, while the two 16-bit memory classes provide 65,536 memory
cells.

The `HashMapMemory` abstract class can be extended to implement any other sized unsigned or signed memory
implementations. The constructor accepts minimum and maximum addresses, and minimum and maximum values. However, the
`Memory` interface requires addresses and values to be Java `int` types, which effectively limits the possible range of
addresses and values to always be within the range of signed 32-bit integers (-2,147,483,648 to 2,147,483,647). It is
therefore impossible to create a `Memory` implementation that goes beyond that range, such as an unsigned 32-bit integer
memory implementation or any kind of floating-point memory. This is an intentional limitation. This is brainfuck after
all. It's not meant to be practical.

Most users of the `BFExecutor` will use one of the `HashMapMemory` implementations and provide their own `Input` and
`Output` implementations. Non-interactive programs can use the `NullInput` implementation, and simply provide an
`Output` implementation, unless console output is sufficient.

```java
Input input = new NullInput();
Output output = new ConsoleOutput();
Memory memory = new Unsigned16BitMemory();

Executor executor = new BFExecutor(input, output, memory);
Looper looper = new BFLooper();
BF bf = new BF(executor, looper);

bf.run("++-->><<[,.]");
```

## Exceptions

The standard exception thrown by `BF` is the `STB` ("shit the bed") exception, which is a `RuntimeException`. This will
be thrown if invalid BF code is found by the interpreter. Although the provided `Memory` implementations will throw an
`STB` on any attempt to reference addresses or values outside the valid range, it should be impossible for valid BF code
to result in an `STB`, as the interpreter implements a number of common brainfuck safeguards, such as wrapping the
memory pointer and values on overflow and underflow.
