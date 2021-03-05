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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BFTest {
    @Mock
    private Executor executor;
    @Mock
    private Looper looper;

    private BF bf;

    @Before
    public void initialize() {
        bf = new BF(executor, looper);
    }

    @Test
    public void runWhenCodeIsNullThrowsSTB() {
        assertThrows(STB.class, () -> bf.run(null));
    }

    @Test
    public void runWhenCodeIsEmptyDoesNothing() {
        bf.run("");

        verifyNoInteractions(executor);
        verifyNoInteractions(looper);
    }

    @Test
    public void runWhenCodeContainsOnlyIncrementPerformsIncrement() {
        bf.run("+");

        verify(executor).performIncrement();
        verifyNoInteractions(looper);
    }

    @Test
    public void runWhenCodeContainsOnlyDecrementPerformsDecrement() {
        bf.run("-");

        verify(executor).performDecrement();
        verifyNoInteractions(looper);
    }

    @Test
    public void runWhenCodeContainsOnlyIncrementPointerPerformsIncrementPointer() {
        bf.run(">");

        verify(executor).performIncrementPointer();
        verifyNoInteractions(looper);
    }

    @Test
    public void runWhenCodeContainsOnlyDecrementPointerPerformsDecrementPointer() {
        bf.run("<");

        verify(executor).performDecrementPointer();
        verifyNoInteractions(looper);
    }

    @Test
    public void runWhenCodeContainsOnlyEmptyLoopAndLoopIsToBeSkippedPerformsStartOfLoopAndJumpsToEndOfLoop() {
        String code = "[]";
        when(executor.performStartLoop()).thenReturn(true);
        when(executor.performEndLoop()).thenReturn(false);
        when(looper.findEndOfLoopDelta(code, 0)).thenReturn(1);

        bf.run(code);
    }

    @Test
    public void runWhenCodeContainsOnlyLoopWithContentAndLoopIsToBeSkippedPerformsStartOfLoopAndJumpsToEndOfLoop() {
        String code = "[+]";
        when(executor.performStartLoop()).thenReturn(true);
        when(executor.performEndLoop()).thenReturn(false);
        when(looper.findEndOfLoopDelta(code, 0)).thenReturn(2);

        bf.run(code);

        verify(executor, never()).performIncrement();
    }

    @Test
    public void runWhenCodeContainsOnlyLoopWithContentAndLoopIsToBeExecutedTwicePerformsLoopTwiceAndJumpsToEndOfLoopOnThirdIteration() {
        String code = "[+]";
        when(executor.performStartLoop()).thenReturn(false, false, true);
        when(executor.performEndLoop()).thenReturn(true, true, false);
        when(looper.findStartOfLoopDelta(code, 2)).thenReturn(-2);
        when(looper.findEndOfLoopDelta(code, 0)).thenReturn(2);

        bf.run(code);

        verify(executor, times(2)).performIncrement();
    }

    @Test
    public void runWhenCodeContainsNestedLoopsWithContentAndBothLoopsAreToBeExecutedTwiceEachPerformsLoopsTwiceAndJumpsToEndOfLoopsOnThirdIterations() {
        String code = "[+[-]]";
        when(executor.performStartLoop()).thenReturn(false, false, false, false, false, false, false);
        when(executor.performEndLoop()).thenReturn(true, true, false, true, true, false, false);
        when(looper.findStartOfLoopDelta(code, 4)).thenReturn(-2);
        when(looper.findStartOfLoopDelta(code, 5)).thenReturn(-5);

        bf.run(code);

        verify(executor, times(2)).performIncrement();
        verify(executor, times(5)).performDecrement();
    }

    @Test
    public void runWhenCodeContainsReadPerformsRead() {
        bf.run(",");

        verify(executor).performInput();
    }

    @Test
    public void runWhenCodeContainsWritePerformsWrite() {
        bf.run(".");

        verify(executor).performOutput();
    }
}
