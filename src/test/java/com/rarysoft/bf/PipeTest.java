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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class PipeTest {
    @Test
    public void readWhenNothingWrittenReturnsSizeOfZero() {
        Pipe pipe = new Pipe();

        int result = pipe.read();

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void readWhenNothingWrittenAndHeaderSkippedThrowsSTB() {
        Pipe pipe = new Pipe(true);

        assertThrows(STB.class, pipe::read);
    }

    @Test
    public void readAfterHeaderReadWhenNothingWrittenThrowsSTB() {
        Pipe pipe = new Pipe();
        pipe.read();

        assertThrows(STB.class, pipe::read);
    }

    @Test
    public void writeWritesValueAndReadReturnsSizeOfOne() {
        Pipe pipe = new Pipe();

        pipe.write('Z');

        assertThat(pipe.read()).isEqualTo(1);
    }

    @Test
    public void writeWritesValueAndReadAfterHeaderReadReturnsValueWritten() {
        Pipe pipe = new Pipe();

        pipe.write('Z');
        pipe.read();

        assertThat(pipe.read()).isEqualTo('Z');
    }

    @Test
    public void writeWritesValueAndReadWhenHeaderSkippedReturnsValueWritten() {
        Pipe pipe = new Pipe(true);

        pipe.write('Z');

        assertThat(pipe.read()).isEqualTo('Z');
    }

    @Test
    public void readAfterWritingFourValuesReturnsSizeOfFour() {
        Pipe pipe = new Pipe();
        pipe.write('T');
        pipe.write('e');
        pipe.write('s');
        pipe.write('t');

        int result = pipe.read();

        assertThat(result).isEqualTo(4);
    }
}
