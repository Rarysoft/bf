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

import java.util.HashMap;
import java.util.Map;

public abstract class HashMapMemory implements Memory {
    private final int minAddress;
    private final int maxAddress;
    private final int minValue;
    private final int maxValue;

    private final Map<Integer, Integer> bytes = new HashMap<>();

    protected HashMapMemory(int minAddress, int maxAddress, int minValue, int maxValue) {
        this.minAddress = minAddress;
        this.maxAddress = maxAddress;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public int read(int address) {
        if (address > maxAddress || address < minAddress) {
            throw new STB("Address out of range");
        }
        return bytes.getOrDefault(address, 0);
    }

    @Override
    public void write(int address, int value) {
        if (address > maxAddress || address < minAddress) {
            throw new STB("Address out of range");
        }
        if (value > maxValue || value < minValue) {
            throw new STB("Value out of range");
        }
        bytes.put(address, value);
    }

    @Override
    public int minAddress() {
        return minAddress;
    }

    @Override
    public int maxAddress() {
        return maxAddress;
    }

    @Override
    public int minValue() {
        return minValue;
    }

    @Override
    public int maxValue() {
        return maxValue;
    }
}
