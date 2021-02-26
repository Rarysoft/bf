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

public class BFExecutor implements Executor {
    private final Input input;
    private final Output output;
    private final Memory memory;

    private int pointer;

    public BFExecutor(Input input, Output output, Memory memory) {
        this.input = input;
        this.output = output;
        this.memory = memory;
        this.pointer = 0;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    @Override
    public void performIncrement() {
        int current = memory.read(pointer);
        if (current == Integer.MAX_VALUE) {
            throw new STB(String.format("Maximum value exceeded at memory address %d", pointer));
        }
        memory.write(pointer, current + 1);
    }

    @Override
    public void performDecrement() {
        int current = memory.read(pointer);
        if (current == Integer.MIN_VALUE) {
            throw new STB(String.format("Minimum value exceeded at memory address %d", pointer));
        }
        memory.write(pointer, current - 1);
    }

    @Override
    public void performIncrementPointer() {
        if (pointer == Integer.MAX_VALUE) {
            throw new STB("Maximum address exceeded");
        }
        pointer ++;
    }

    @Override
    public void performDecrementPointer() {
        if (pointer == 0) {
            throw new STB("Minimum address exceeded");
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
    public void performRead() {
        memory.write(pointer, input.read());
    }

    @Override
    public void performWrite() {
        output.write(memory.read(pointer));
    }
}
