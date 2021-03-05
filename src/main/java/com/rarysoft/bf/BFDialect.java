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
 * Defines the brainfuck commands.
 */
public class BFDialect {
    /**
     * The increment command (<code>+</code>): Increment the memory cell at the pointer.
     */
    public static final char INCREMENT = '+';

    /**
     * The decrement command (<code>-</code>): Decrement the memory cell at the pointer.
     */
    public static final char DECREMENT = '-';

    /**
     * The increment pointer command (<code>&gt;</code>): Move the pointer to the right.
     */
    public static final char INCREMENT_POINTER = '>';

    /**
     * The decrement pointer command (<code>&lt;</code>): Move the pointer to the left.
     */
    public static final char DECREMENT_POINTER = '<';

    /**
     * The start loop command (<code>[</code>): Jump past the matching <code>]</code> if the cell
     * at the pointer is 0.
     */
    public static final char START_LOOP = '[';

    /**
     * The end loop command (<code>]</code>): Jump back to the matchin <code>[</code> if the cell
     * at the pointer is nonzero.
     */
    public static final char END_LOOP = ']';

    /**
     * The input command (<code>,</code>): Input a character and store it in the cell at the pointer.
     */
    public static final char INPUT = ',';

    /**
     * The output command (<code>.</code>): Output the character signified by the cell at the pointer.
     */
    public static final char OUTPUT = '.';
}
