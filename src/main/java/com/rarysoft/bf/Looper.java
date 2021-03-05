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
 * Manages loops.
 */
public interface Looper {
    /**
     * Returns the delta value, as a positive integer, indicating the distance to the end loop
     * command that matches the start loop command at the position indicated. It is not necessary,
     * although possible, to provide the entire code to this method. However, it is necessary to
     * provide at least the code beginning at the start of loop and continuing either all the way
     * to the end of the program, or at least far enough to locate the end of the loop. Since the
     * purpose of this method is to find the end of the loop, it is unlikely that callers will
     * know how much code to include in order to ensure location of the end of the loop, so this
     * method will usually be called with code that extends to the end of the program. There is
     * no benefit in performance if a subset of code is provided.
     *
     * @param code The code to examine to find the end of the loop.
     * @param position The index within the code that the loop starts at.
     * @return The delta to add to the index of the start of the loop to find the end of the loop.
     */
    int findEndOfLoopDelta(String code, int position);

    /**
     * Returns the delta value, as a negative integer, indicating the distance to the start loop
     * command that matches the end loop command at the position indicated. It is not necessary,
     * although possible, to provide the entire code to this method. However, it is necessary to
     * provide at least the code ending at the end of loop and beginning either all the way at
     * the start of the program, or at least far enough back to locate the start of the loop.
     * Since the purpose of this method is to find the start of the loop, it is unlikely that
     * callers will know how much code to include in order to ensure location of the start of
     * the loop, so this method will usually be called with code that extends to the start of
     * the program. There is no benefit in performance if a subset of code is provided.
     *
     * @param code The code to examine to find the start of the loop.
     * @param position The index within the code that the loop ends at.
     * @return The delta to add to the index of the end of the loop to find the start of the loop.
     */
    int findStartOfLoopDelta(String code, int position);
}
