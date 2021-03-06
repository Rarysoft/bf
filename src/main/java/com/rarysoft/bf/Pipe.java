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

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of both the {@link Input} and {@link Output} to allow the output of
 * one program to be piped in as input to another program. Optional header data can be
 * provided to indicate to the program accepting the data as input how much data can be
 * read.
 */
public class Pipe implements Input, Output {
    private final List<Integer> data = new ArrayList<>();

    private boolean headerRead = false;

    /**
     * Creates an instance with header data.
     */
    public Pipe() {
        this(false);
    }

    /**
     * Creates an instance. The second argument indicates whether or not to skip header data.
     * A value of <code>true</code> will skip the header, and a value of <code>false</code>
     * will include a header.
     *
     * @param skipHeader Whether or not to skip the header.
     */
    public Pipe(boolean skipHeader) {
        this.headerRead = skipHeader;
    }

    @Override
    public int read() {
        if (! headerRead) {
            headerRead = true;
            return data.size();
        }
        if (data.isEmpty()) {
            throw new STB("Illegal read operation");
        }
        return data.remove(0);
    }

    @Override
    public void write(int value) {
        data.add(value);
    }
}
