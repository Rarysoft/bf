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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * An implementation of {@link Input} that reads from a file. Instances can be created with
 * or without header data. With header data, the first read will return the size of the
 * file. This size indicates the number of additional reads that can safely be executed
 * before the end of file is reached. Without a header, the file size must be known.
 */
public class FileInput implements Input {
    private final int length;
    private final InputStream inputStream;

    private boolean headerRead = false;

    /**
     * Creates an instance that reads input from the specified file. An {@link STB} will be
     * thrown if the file does not exist or is too large. The maximum size that can be read
     * is 2,147,483,647 bytes (approximately 2 GB).
     *
     * @param filename The file to read data from.
     */
    public FileInput(String filename) {
        this(filename, false);
    }

    /**
     * Creates an instance that reads input from the specified file. An {@link STB} will be
     * thrown if the file does not exist or is too large. The maximum size that can be read
     * is 2,147,483,647 bytes (approximately 2 GB). If the second argument is <code>true</code>,
     * the header indicating the size of the file will not be included, and instead the first
     * read will return the first byte in the file.
     *
     * @param filename The file to read data from.
     * @param skipHeader Whether or not to skip the header.
     */
    public FileInput(String filename, boolean skipHeader) {
        File file = new File(filename);
        if (! file.exists() || ! file.isFile()) {
            throw new STB("File not found");
        }
        if (file.length() > Integer.MAX_VALUE) {
            throw new STB("File is too large");
        }
        this.length = (int) file.length();
        try {
            this.inputStream = new FileInputStream(file);
        }
        catch (IOException e) {
            throw new STB(e.getMessage());
        }
        this.headerRead = skipHeader;
    }

    /**
     * Reads a single byte from the file. If there is header data available, then the first
     * call to this method will return the number of remaining reads before end of file is
     * reached. All subsequent reads will return a single byte of data from the file. If no
     * header is available, the first read will return the first byte of data from the file.
     * This method will throw an {@link STB} if an attempt is made to read past the end of
     * the file, or if some other internal file read error occurs.
     *
     * @return The next byte of data in the file, or the size of the file in bytes if there
     * is a header and it has not yet been read.
     */
    @Override
    public int read() {
        if (! headerRead) {
            headerRead = true;
            return length;
        }
        try {
            int value = inputStream.read();
            if (value < 0) {
                throw new STB("Read past end of file");
            }
            return value;
        }
        catch (IOException e) {
            throw new STB("Unrecoverable file read error");
        }
    }
}
