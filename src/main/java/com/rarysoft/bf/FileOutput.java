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

import java.io.*;

/**
 * An implementation of {@link Output} that writes to a file. Existing files will always
 * be overwritten.
 */
public class FileOutput implements Output {
    private final OutputStream outputStream;

    /**
     * Creates an instance that writes input to the specified file. If the file already
     * exists, it will be overwritten.
     *
     * @param filename The file to write data to.
     */
    public FileOutput(String filename) {
        this(filename, false);
    }

    /**
     * Creates an instance that writes input to the specified file. If the second argument
     * is <code>true</code>, data will be appended to the file if it already exists. If the
     * second argument is <code>false</code>, the file will be overwritten if it already
     * exists. In either case, if the file does not exist, a new file will be created.
     *
     * @param filename The file to write data to.
     * @param append Whether or not to append to existing data.
     */
    public FileOutput(String filename, boolean append) {
        try {
            this.outputStream = new FileOutputStream(filename, append);
        }
        catch (IOException e) {
            throw new STB(e.getMessage());
        }
    }

    @Override
    public void write(int value) {
        try {
            outputStream.write(value);
        }
        catch (IOException e) {
            throw new STB("Unrecoverable file write error");
        }
    }
}
