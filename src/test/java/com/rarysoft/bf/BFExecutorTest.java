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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BFExecutorTest {
    @Mock
    private Input input;
    @Mock
    private Output output;
    @Mock
    private Memory memory;

    @InjectMocks
    private BFExecutor bfExecutor;

    private final int POINTER = 42;

    @Before
    public void prepareBFExecutor() {
        bfExecutor.setPointer(POINTER);
    }

    @Test
    public void performIncrementIncrementsMemoryValueAtPointer() {
        when(memory.read(POINTER)).thenReturn(101);

        bfExecutor.performIncrement();

        verify(memory).write(POINTER, 102);
    }

    @Test
    public void performIncrementWhenMemoryValueAtMaximumValueThrowsSTB() {
        when(memory.read(POINTER)).thenReturn(Integer.MAX_VALUE);

        Assert.assertThrows(STB.class, () -> bfExecutor.performIncrement());
    }

    @Test
    public void performDecrementDecrementsMemoryValueAtPointer() {
        when(memory.read(POINTER)).thenReturn(102);

        bfExecutor.performDecrement();

        verify(memory).write(POINTER, 101);
    }

    @Test
    public void performDecrementWhenMemoryValueAtMinimumValueThrowsSTB() {
        when(memory.read(POINTER)).thenReturn(Integer.MIN_VALUE);

        Assert.assertThrows(STB.class, () -> bfExecutor.performDecrement());
    }

    @Test
    public void performIncrementPointerIncrementsPointer() {
        bfExecutor.performIncrementPointer();

        assertThat(bfExecutor.getPointer()).isEqualTo(POINTER + 1);
    }

    @Test
    public void performIncrementPointerWhenPointerAtEndThrowsSTB() {
        bfExecutor.setPointer(Integer.MAX_VALUE);

        Assert.assertThrows(STB.class, () -> bfExecutor.performIncrementPointer());
    }

    @Test
    public void performIDecrementPointerDecrementsPointer() {
        bfExecutor.performDecrementPointer();

        assertThat(bfExecutor.getPointer()).isEqualTo(POINTER - 1);
    }

    @Test
    public void performDecrementPointerWhenPointerAtStartThrowsSTB() {
        bfExecutor.setPointer(0);

        Assert.assertThrows(STB.class, () -> bfExecutor.performDecrementPointer());
    }

    @Test
    public void performStartLoopWhenMemoryAtPointerEqualsZeroReturnsTrue() {
        when(memory.read(POINTER)).thenReturn(0);

        boolean result = bfExecutor.performStartLoop();

        assertThat(result).isTrue();
    }

    @Test
    public void performStartLoopWhenMemoryAtPointerIsOneReturnsFalse() {
        when(memory.read(POINTER)).thenReturn(1);

        boolean result = bfExecutor.performStartLoop();

        assertThat(result).isFalse();
    }

    @Test
    public void performStartLoopWhenMemoryAtPointerIsGreaterThanOneReturnsFalse() {
        when(memory.read(POINTER)).thenReturn(101);

        boolean result = bfExecutor.performStartLoop();

        assertThat(result).isFalse();
    }

    @Test
    public void performStartLoopWhenMemoryAtPointerIsNegativeOneReturnsFalse() {
        when(memory.read(POINTER)).thenReturn(-1);

        boolean result = bfExecutor.performStartLoop();

        assertThat(result).isFalse();
    }

    @Test
    public void performStartLoopWhenMemoryAtPointerIsLessThanNegativeOneReturnsFalse() {
        when(memory.read(POINTER)).thenReturn(-101);

        boolean result = bfExecutor.performStartLoop();

        assertThat(result).isFalse();
    }

    @Test
    public void performEndLoopWhenMemoryAtPointerEqualsZeroReturnsFalse() {
        when(memory.read(POINTER)).thenReturn(0);

        boolean result = bfExecutor.performEndLoop();

        assertThat(result).isFalse();
    }

    @Test
    public void performEndLoopWhenMemoryAtPointerIsOneReturnsTrue() {
        when(memory.read(POINTER)).thenReturn(1);

        boolean result = bfExecutor.performEndLoop();

        assertThat(result).isTrue();
    }

    @Test
    public void performEndLoopWhenMemoryAtPointerIsGreaterThanOneReturnsTrue() {
        when(memory.read(POINTER)).thenReturn(101);

        boolean result = bfExecutor.performEndLoop();

        assertThat(result).isTrue();
    }

    @Test
    public void performEndLoopWhenMemoryAtPointerIsNegativeOneReturnsTrue() {
        when(memory.read(POINTER)).thenReturn(-1);

        boolean result = bfExecutor.performEndLoop();

        assertThat(result).isTrue();
    }

    @Test
    public void performEndLoopWhenMemoryAtPointerIsLessThanNegativeOneReturnsTrue() {
        when(memory.read(POINTER)).thenReturn(-101);

        boolean result = bfExecutor.performEndLoop();

        assertThat(result).isTrue();
    }

    @Test
    public void performReadAlwaysReadsFromInputAndWritesToMemoryAtPointer() {
        when(input.read()).thenReturn(101);

        bfExecutor.performRead();

        verify(memory).write(POINTER, 101);
    }

    @Test
    public void performWriteAlwaysWritesToOutputFromMemoryAtPointer() {
        when(memory.read(POINTER)).thenReturn(101);

        bfExecutor.performWrite();

        verify(output).write(101);
    }
}
