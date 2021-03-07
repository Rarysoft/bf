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
 * An implementation of {@link Input} that reads from a predefined <code>String</code>.
 * Instances can be created with or without header data. With header data, the first read
 * will return the size of the <code>String</code>. This size indicates the number of
 * additional reads that can safely be executed before the end of the <code>String</code>
 * is reached. Without a header, the <code>String</code> length must be known.
 */
public class StringInput implements Input {
    private final String data;

    private boolean headerRead;
    private int index;

    /**
     * Creates an instance that reads input from the specified <code>String</code>.
     *
     * @param data The <code>String</code> to read data from.
     */
    public StringInput(String data) {
        this(data, false);
    }

    /**
     * Creates an instance that reads input from the specified <code>String</code>. If the
     * second argument is <code>true</code>, the header indicating the length of the data
     * will not be included, and instead the first read will return the first byte in the
     * <code>String</code>.
     *
     * @param data The <code>String</code> to read data from.
     * @param skipHeader Whether or not to skip the header.
     */
    public StringInput(String data, boolean skipHeader) {
        this.data = data;
        this.headerRead = skipHeader;
        this.index = 0;
    }

    @Override
    public int read() {
        if (! headerRead) {
            headerRead = true;
            return data.length();
        }
        if (index >= data.length()) {
            throw new STB("Read past end of data");
        }
        int value = data.charAt(index);
        index ++;
        return value;
    }
}
