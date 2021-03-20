/*
 * MIT License
 *
 * Copyright (c) 2021 Rarysoft Enterprises
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.rarysoft.bf;

/**
 * This is the core of the BF library, the brainfuck interpreter class. This is the class to
 * instantiate in order to execute brainfuck code. It requires an implementation of the {@link Executor}
 * interface and an implementation of {@link Looper}, but the default implementations ({@link BFExecutor}
 * and {@link BFLooper}) will be instantiated and used if the {@link BFExecutor}'s dependencies are
 * provided instead. These dependencies are implementations of {@link Input}, {@link Output}, and
 * {@link Memory}.
 */
public class BF {
    private final Executor executor;
    private final Looper looper;

    /**
     * Creates an instance of the interpreter that uses a {@link BFExecutor} and a {@link BFLooper}. The
     * {@link BFExecutor} instance will use the {@link NullInput}, {@link ConsoleOutput}, and
     * {@link Unsigned8BitMemory} implementations.
     */
    public BF() {
        this(new NullInput(), new ConsoleOutput(), new Unsigned8BitMemory());
    }

    /**
     * Creates an instance of the interpreter that uses the provided {@link Executor} and {@link Looper}
     * implementations.
     *
     * @param executor The {@link Executor} to use.
     * @param looper The {@link Looper} to use.
     */
    public BF(Executor executor, Looper looper) {
        this.executor = executor;
        this.looper = looper;
    }

    /**
     * Creates an instance of the interpreter that uses a {@link BFExecutor} and a {@link BFLooper}. The
     * {@link BFExecutor} instance will use the provided {@link Input}, {@link Output}, and {@link Memory}
     * implementations.
     *
     * @param input The {@link Input} implementation for the {@link BFExecutor} to use.
     * @param output The {@link Output} implementation for the {@link BFExecutor} to use.
     * @param memory The {@link Memory} implementation for the {@link BFExecutor} to use.
     */
    public BF(Input input, Output output, Memory memory) {
        this(new BFExecutor(input, output, memory, memory.minAddress()), new BFLooper());
    }

    /**
     * Runs a brainfuck program. If any coding errors are encountered while executing the code, an
     * {@link STB} will be thrown. As long as a non-null <code>String</code> is provided, this
     * method will attempt to execute the code, whether or not any actual brainfuck code is found.
     * An {@link STB} will only be thrown if invalid brainfuck code is found, such as incorrect
     * matching of <code>[</code> and <code>]</code> commands.
     *
     * @param code The brainfuck code to execute.
     */
    public void run(String code) {
        if (code == null) {
            throw new STB("Code is missing");
        }
        execute(code);
    }

    private void execute(String code) {
        int position = 0;
        while (position < code.length()) {
            int delta = perform(code, position);
            position += delta;
        }
    }

    private int perform(String code, int position) {
        char token = code.charAt(position);
        switch (token) {
            case BFDialect.INCREMENT:
                executor.performIncrement();
                break;
            case BFDialect.DECREMENT:
                executor.performDecrement();
                break;
            case BFDialect.INCREMENT_POINTER:
                executor.performIncrementPointer();
                break;
            case BFDialect.DECREMENT_POINTER:
                executor.performDecrementPointer();
                break;
            case BFDialect.START_LOOP:
                if (executor.performStartLoop()) {
                    return looper.findEndOfLoopDelta(code, position);
                }
                break;
            case BFDialect.END_LOOP:
                if (executor.performEndLoop()) {
                    return looper.findStartOfLoopDelta(code, position);
                }
                break;
            case BFDialect.INPUT:
                executor.performInput();
                break;
            case BFDialect.OUTPUT:
                executor.performOutput();
                break;
            default:
                break;
        }
        return 1;
    }
}
