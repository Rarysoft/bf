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

public class StringInputTest {
    @Test
    public void readWhenStringIsEmptyReturnsZero() {
        StringInput stringInput = new StringInput("");

        int result = stringInput.read();

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void readWhenStringIsEmptyAndHeaderIsSkippedThrowsSTB() {
        StringInput stringInput = new StringInput("", true);

        assertThrows(STB.class, stringInput::read);
    }

    @Test
    public void readAfterHeaderReadWhenStringIsEmptyThrowsSTB() {
        StringInput stringInput = new StringInput("");
        stringInput.read();

        assertThrows(STB.class, stringInput::read);
    }

    @Test
    public void readAfterHeaderReadReturnsFirstByte() {
        StringInput stringInput = new StringInput("Test input");
        stringInput.read();

        int result = stringInput.read();

        assertThat(result).isEqualTo('T');
    }

    @Test
    public void readWhenHeaderSkippedReturnsFirstByte() {
        StringInput stringInput = new StringInput("Test input", true);

        int result = stringInput.read();

        assertThat(result).isEqualTo('T');
    }

    @Test
    public void readNumberOfTimesSpecifiedByHeaderReturnsAllFileBytes() {
        StringInput stringInput = new StringInput("Test input");
        int length = stringInput.read();
        int[] results = new int[length];

        for (int index = 0; index < length; index ++) {
            results[index] = stringInput.read();
        }

        assertThat(results).hasSize(10).containsExactly('T', 'e', 's', 't', ' ', 'i', 'n', 'p', 'u', 't');
    }

    @Test
    public void readTenTimesWhenHeaderSkippedReturnsAllFileBytes() {
        StringInput stringInput = new StringInput("Test input", true);
        int[] results = new int[10];

        for (int index = 0; index < 10; index ++) {
            results[index] = stringInput.read();
        }

        assertThat(results).hasSize(10).containsExactly('T', 'e', 's', 't', ' ', 'i', 'n', 'p', 'u', 't');
    }

    @Test
    public void readAfterLastByteReadThrowsSTB() {
        StringInput stringInput = new StringInput("Test input");
        int length = stringInput.read();

        for (int index = 0; index < length; index ++) {
            stringInput.read();
        }

        assertThrows(STB.class, stringInput::read);
    }

    @Test
    public void readAfterLastByteReadWhenHeaderSkippedThrowsSTB() {
        StringInput stringInput = new StringInput("Test input", true);

        for (int index = 0; index < 10; index ++) {
            stringInput.read();
        }

        assertThrows(STB.class, stringInput::read);
    }
}
