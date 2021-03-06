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

public class FileInputTest {
    @Test
    public void constructorWhenFileNotFoundThrowsSTB() {
        assertThrows(STB.class, () -> new FileInput("src/test/resources/not-exists.txt"));
    }

    @Test
    public void readWhenFileIsEmptyReturnsZero() {
        FileInput fileInput = new FileInput("src/test/resources/empty.txt");

        int result = fileInput.read();

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void readWhenFileIsEmptyAndHeaderIsSkippedThrowsSTB() {
        FileInput fileInput = new FileInput("src/test/resources/empty.txt", true);

        assertThrows(STB.class, fileInput::read);
    }

    @Test
    public void readAfterHeaderReadWhenFileIsEmptyThrowsSTB() {
        FileInput fileInput = new FileInput("src/test/resources/empty.txt");
        fileInput.read();

        assertThrows(STB.class, fileInput::read);
    }

    @Test
    public void readAfterHeaderReadReturnsFirstByte() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt");
        fileInput.read();

        int result = fileInput.read();

        assertThat(result).isEqualTo('T');
    }

    @Test
    public void readWhenHeaderSkippedReturnsFirstByte() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt", true);

        int result = fileInput.read();

        assertThat(result).isEqualTo('T');
    }

    @Test
    public void readNumberOfTimesSpecifiedByHeaderReturnsAllFileBytes() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt");
        int length = fileInput.read();
        int[] results = new int[length];

        for (int index = 0; index < length; index ++) {
            results[index] = fileInput.read();
        }

        assertThat(results).hasSize(10).containsExactly('T', 'e', 's', 't', ' ', 'i', 'n', 'p', 'u', 't');
    }

    @Test
    public void readTenTimesWhenHeaderSkippedReturnsAllFileBytes() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt", true);
        int[] results = new int[10];

        for (int index = 0; index < 10; index ++) {
            results[index] = fileInput.read();
        }

        assertThat(results).hasSize(10).containsExactly('T', 'e', 's', 't', ' ', 'i', 'n', 'p', 'u', 't');
    }

    @Test
    public void readAfterLastByteReadThrowsSTB() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt");
        int length = fileInput.read();

        for (int index = 0; index < length; index ++) {
            fileInput.read();
        }

        assertThrows(STB.class, fileInput::read);
    }

    @Test
    public void readAfterLastByteReadWhenHeaderSkippedThrowsSTB() {
        FileInput fileInput = new FileInput("src/test/resources/input.txt", true);

        for (int index = 0; index < 10; index ++) {
            fileInput.read();
        }

        assertThrows(STB.class, fileInput::read);
    }
}
