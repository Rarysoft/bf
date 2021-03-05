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
 * An interface for an {@link Executor} implementation to use for internal program memory.
 * This memory stores, in some unspecified internal format, the data values stored in cells
 * pointed to by the program pointer.
 */
public interface Memory {
    /**
     * Returns the value stored in the cell at the provided address, or pointer index. The
     * address must be within the range specified by the <code>minAddress</code> and
     * <code>maxAddress</code> values. By convention, if an address outside of the valid
     * range is specified, the implementation should throw an {@link STB}. Also by convention,
     * addresses not previously initialized should return 0.
     *
     * @param address The address to read the cell value from.
     * @return The value stored at the specified address.
     */
    int read(int address);

    /**
     * Stores the specified value in the cell at the provided address, or pointer index. The
     * address must be within the range specified by the <code>minAddress</code> and
     * <code>maxAddress</code> values. The value must be within the range specified by the
     * <code>minValue</code> and <code>maxValue</code> values. </code>By convention, if an
     * address outside of the valid range is specified, the implementation should throw an
     * {@link STB}. Also by convention, if a value outside of the valid range is specified,
     * the implementation should throw an {@link STB}.
     *
     * @param address The address to store the value in.
     * @param value The value to store at the specified address.
     */
    void write(int address, int value);

    /**
     * The lowest address value. The program pointer will initially point to this address.
     *
     * @return The lowest address value.
     */
    int minAddress();

    /**
     * The highest address value.
     *
     * @return The highest address value.
     */
    int maxAddress();

    /**
     * The lowest value that can be stored in a memory cell.
     *
     * @return The lowest value that can be stored in a memory cell.
     */
    int minValue();

    /**
     * The highest value that can be stored in a memory cell.
     *
     * @return The highest value that can be stored in a memory cell.
     */
    int maxValue();
}
