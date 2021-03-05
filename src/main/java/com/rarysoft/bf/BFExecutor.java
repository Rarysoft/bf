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
 * Executes the eight brainfuck commands using provided implementations of {@link Input},
 * {@link Output}, and {@link Memory}.
 *
 * @see com.rarysoft.bf.Executor
 */
public class BFExecutor implements Executor {
    private final Input input;
    private final Output output;
    private final Memory memory;

    private int pointer;

    /**
     * Creates an instance of the executor using the provided {@link Input}, {@link Output},
     * and {@link Memory} implementations.
     *
     * @param input The {@link Input} implementation to use.
     * @param output The {@link Output} implementation to use.
     * @param memory The {@link Memory} implementation to use.
     */
    public BFExecutor(Input input, Output output, Memory memory) {
        this.input = input;
        this.output = output;
        this.memory = memory;
        this.pointer = memory.minAddress();
    }

    /**
     * Gets the value of the pointer. This method is not normally used when executing code,
     * and is not used at all by the {@link BF} interpreter. It can be used for testing or to
     * otherwise gain insight into the internal workings of the executor.
     *
     * @return The value of the pointer.
     */
    public int getPointer() {
        return pointer;
    }

    /**
     * Sets the value of the pointer. This method is not normally used when executing code,
     * and is not used at all by the {@link BF} interpreter. It can be used for testing or to
     * manipulate the internal workings of the executor. Note that doing so is dangerous, and
     * may alter the code execution in ways that violate the brainfuck standard, and may result
     * in an {@link STB} being thrown.
     *
     * @param pointer The value to set the pointer to.
     */
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    @Override
    public void performIncrement() {
        int current = memory.read(pointer);
        if (current == memory.maxValue()) {
            memory.write(pointer, memory.minValue());
            return;
        }
        memory.write(pointer, current + 1);
    }

    @Override
    public void performDecrement() {
        int current = memory.read(pointer);
        if (current == memory.minValue()) {
            memory.write(pointer, memory.maxValue());
            return;
        }
        memory.write(pointer, current - 1);
    }

    @Override
    public void performIncrementPointer() {
        if (pointer == memory.maxAddress()) {
            pointer = memory.minAddress();
            return;
        }
        pointer ++;
    }

    @Override
    public void performDecrementPointer() {
        if (pointer == memory.minAddress()) {
            pointer = memory.maxAddress();
            return;
        }
        pointer --;
    }

    @Override
    public boolean performStartLoop() {
        return memory.read(pointer) == 0;
    }

    @Override
    public boolean performEndLoop() {
        return memory.read(pointer) != 0;
    }

    @Override
    public void performInput() {
        memory.write(pointer, input.read());
    }

    @Override
    public void performOutput() {
        output.write(memory.read(pointer));
    }
}
