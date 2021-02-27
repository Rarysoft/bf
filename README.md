# BF

A [brainfuck](https://esolangs.org/wiki/Brainfuck) interpreter.

## Usage

To execute brainfuck code, create an instance of `BF` and pass the code as a `String` into the instance's `run` method.
An instance of `BF` requires an `Executor` and a `Looper`. The standard implementations are the `BFExecutor` and
`BFLooper` classes. To instantiate a `BFExecutor`, implementations of the `Input`, `Output`, and `Memory` interfaces
are required. The only provided `Input` implementation is the `NullInput`, which immediately returns a `null` input
(`0x00`) whenever input is read. The only provided `Output` implementation is `ConsoleOutput`, which prints directly
to the system console. The only provided `Memory` implementations are `Unsigned8BitMemory` and `Unsigned16BitMemory`,
both of which have 30,000 memory addresses, but differ in the values that can be stored in memory, with 8-bit values
(0 to 255) possible in each memory cell of `Unsigned8BitMemory`, and 16-bit values (0 to 65535) possible in each
memory cell of `Unsigned16BitMemory`. Most users of the `BFExecutor` will use `Unsigned16BitMemory` and provide their
own `Input` and `Output` implementations. Non-interactive programs can use the `NullInput` implementation, and simply
provide an `Output` implementation, unless console output is sufficient.

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
be thrown if the BF code is invalid.
