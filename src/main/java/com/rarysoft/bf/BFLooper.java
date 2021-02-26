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

public class BFLooper implements Looper {
    @Override
    public int findEndOfLoopDelta(String code, int position) {
        validateArgs(code, position);
        boolean done = false;
        int delta = 0;
        int loopCount = 1;
        while (! done) {
            delta ++;
            if (position + delta == code.length()) {
                throw new STB("Loop has no end");
            }
            char token = code.charAt(position + delta);
            if (token == BFDialect.END_LOOP) {
                loopCount --;
                if (loopCount == 0) {
                    done = true;
                    continue;
                }
            }
            if (token == BFDialect.START_LOOP) {
                loopCount ++;
            }
        }
        return delta;
    }

    @Override
    public int findStartOfLoopDelta(String code, int position) {
        validateArgs(code, position);
        boolean done = false;
        int delta = 0;
        int loopCount = 1;
        while (! done) {
            delta --;
            if (position + delta < 0) {
                throw new STB("Loop has no start");
            }
            char token = code.charAt(position + delta);
            if (token == BFDialect.START_LOOP) {
                loopCount --;
                if (loopCount == 0) {
                    done = true;
                    continue;
                }
            }
            if (token == BFDialect.END_LOOP) {
                loopCount ++;
            }
        }
        return delta;
    }

    private void validateArgs(String code, int position) {
        if (code == null) {
            throw new STB("Code is missing");
        }
        if (position < 0) {
            throw new STB("Position before start of code");
        }
        if (position >= code.length()) {
            throw new STB("Position after end of code");
        }
    }
}
