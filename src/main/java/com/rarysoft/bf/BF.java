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

public class BF {
    private final Executor executor;
    private final Looper looper;

    public BF(Executor executor, Looper looper) {
        this.executor = executor;
        this.looper = looper;
    }

    public void run(String code) {
        if (code == null) {
            throw new STB("Code is missing");
        }
        execute(code);
    }

    private void execute(String code) {
        int position = 0;
        while (position < code.length()) {
            int delta = perform(code, position);
            position += delta;
        }
    }

    private int perform(String code, int position) {
        char token = code.charAt(position);
        switch (token) {
            case BFDialect.INCREMENT:
                executor.performIncrement();
                break;
            case BFDialect.DECREMENT:
                executor.performDecrement();
                break;
            case BFDialect.INCREMENT_POINTER:
                executor.performIncrementPointer();
                break;
            case BFDialect.DECREMENT_POINTER:
                executor.performDecrementPointer();
                break;
            case BFDialect.START_LOOP:
                if (executor.performStartLoop()) {
                    return looper.findEndOfLoopDelta(code, position);
                }
                break;
            case BFDialect.END_LOOP:
                if (executor.performEndLoop()) {
                    return looper.findStartOfLoopDelta(code, position);
                }
                break;
            case BFDialect.INPUT:
                executor.performRead();
                break;
            case BFDialect.OUTPUT:
                executor.performWrite();
                break;
            default:
                break;
        }
        return 1;
    }
}
