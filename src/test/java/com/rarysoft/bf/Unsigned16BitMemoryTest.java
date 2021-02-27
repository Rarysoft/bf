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

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Unsigned16BitMemoryTest {
    private final Memory memory = new Unsigned16BitMemory();

    @Test
    public void readWhenAddressIsBelowMinimumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.read(-1));
    }

    @Test
    public void readWhenAddressIsAboveMaximumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.read(65536));
    }

    @Test
    public void readWhenAddressIsMinimumAndIsUninitializedReturnsZero() {
        int result = memory.read(0);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void readWhenAddressIsMaximumAndIsUninitializedReturnsZero() {
        int result = memory.read(65535);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void readWhenUninitializedReturnsZero() {
        int result = memory.read(100);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void writeWhenAddressIsBelowMinimumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.write(-1, 0));
    }

    @Test
    public void writeWhenAddressIsAboveMaximumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.write(65536, 0));
    }

    @Test
    public void writeWhenValueIsBelowMinimumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.write(0, -1));
    }

    @Test
    public void writeWhenValueIsAboveMaximumThrowsSTB() {
        Assert.assertThrows(STB.class, () -> memory.write(0, 65536));
    }

    @Test
    public void writeWhenAddressIsMinimumStoresValueForSubsequentReads() {
        memory.write(0, 42);
        int result = memory.read(0);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void writeWhenAddressIsMaximumStoresValueForSubsequentReads() {
        memory.write(65535, 42);
        int result = memory.read(65535);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void writeWhenValueIsMinimumStoresValueForSubsequentReads() {
        memory.write(0, 0);
        int result = memory.read(0);

        assertThat(result).isEqualTo(0);
    }

    @Test
    public void writeWhenValueIsMaximumStoresValueForSubsequentReads() {
        memory.write(0, 65535);
        int result = memory.read(0);

        assertThat(result).isEqualTo(65535);
    }

    @Test
    public void writeWhenUninitializedStoresValueForSubsequentReads() {
        memory.write(100, 42);
        int result = memory.read(100);

        assertThat(result).isEqualTo(42);
    }

    @Test
    public void writeWhenPreviouslyWrittenOverwritesPreviousValueForSubsequentReads() {
        memory.write(100, 42);
        memory.write(100, 43);
        int result = memory.read(100);

        assertThat(result).isEqualTo(43);
    }
}
