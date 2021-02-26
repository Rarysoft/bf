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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BFLooperTest {
    @InjectMocks
    private BFLooper bfLooper;

    @Test
    public void findEndOfLoopDeltaWhenCodeIsNullThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findEndOfLoopDelta(null, 0));
    }

    @Test
    public void findEndOfLoopDeltaWhenPositionIsNegativeThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findEndOfLoopDelta("[]", -1));
    }

    @Test
    public void findEndOfLoopDeltaWhenHasNoEndThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findEndOfLoopDelta("[", 0));
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsNoContentReturnsOne() {
        assertThat(bfLooper.findEndOfLoopDelta("[]+-", 0)).isEqualTo(1);
        assertThat(bfLooper.findEndOfLoopDelta("[]", 0)).isEqualTo(1);
        assertThat(bfLooper.findEndOfLoopDelta("+-[]+-", 2)).isEqualTo(1);
        assertThat(bfLooper.findEndOfLoopDelta("+-[]", 2)).isEqualTo(1);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[]]+-", 3)).isEqualTo(1);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[]]", 3)).isEqualTo(1);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsOneCharacterOfCommentReturnsTwo() {
        assertThat(bfLooper.findEndOfLoopDelta("[x]+-", 0)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("[x]", 0)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[x]+-", 2)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[x]", 2)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x]]+-", 3)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x]]", 3)).isEqualTo(2);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsOneCharacterOfCodeReturnsTwo() {
        assertThat(bfLooper.findEndOfLoopDelta("[-]+-", 0)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("[-]", 0)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[-]+-", 2)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[-]", 2)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[-]]+-", 3)).isEqualTo(2);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[-]]", 3)).isEqualTo(2);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsAnotherEmptyLoopReturnsThree() {
        assertThat(bfLooper.findEndOfLoopDelta("[[]]+-", 0)).isEqualTo(3);
        assertThat(bfLooper.findEndOfLoopDelta("[[]]", 0)).isEqualTo(3);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[]]+-", 2)).isEqualTo(3);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[]]", 2)).isEqualTo(3);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[]]]+-", 3)).isEqualTo(3);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[]]]", 3)).isEqualTo(3);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsAnotherLoopContainingOneCharacterOfCommentReturnsFour() {
        assertThat(bfLooper.findEndOfLoopDelta("[[x]]+-", 0)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("[[x]]", 0)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x]]+-", 2)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x]]", 2)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[x]]]+-", 3)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[x]]]", 3)).isEqualTo(4);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsAnotherLoopContainingOneCharacterOfCodeReturnsFour() {
        assertThat(bfLooper.findEndOfLoopDelta("[[-]]+-", 0)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("[[-]]", 0)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[-]]+-", 2)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[-]]", 2)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[-]]]+-", 3)).isEqualTo(4);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[[-]]]", 3)).isEqualTo(4);
    }

    @Test
    public void findEndOfLoopDeltaWhenLoopContainsAssortedNestedLoopsAndCodeReturnsCorrectValue() {
        assertThat(bfLooper.findEndOfLoopDelta("[x+-[x+-[]x+-[]]x+-]+-", 0)).isEqualTo(19);
        assertThat(bfLooper.findEndOfLoopDelta("[x+-[x+-[]x+-[]]x+-]", 0)).isEqualTo(19);
        assertThat(bfLooper.findEndOfLoopDelta("[x+-[x+-[]x+-[]]x+-]+-", 4)).isEqualTo(11);
        assertThat(bfLooper.findEndOfLoopDelta("[x+-[x+-[]x+-[]]x+-]", 4)).isEqualTo(11);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]+-", 3)).isEqualTo(19);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]", 3)).isEqualTo(19);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]+-", 7)).isEqualTo(11);
        assertThat(bfLooper.findEndOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]", 7)).isEqualTo(11);
    }

    @Test
    public void findStartOfLoopDeltaWhenCodeIsNullThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findStartOfLoopDelta(null, 0));
    }

    @Test
    public void findStartOfLoopDeltaWhenPositionIsNegativeThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findStartOfLoopDelta("[]", -1));
    }

    @Test
    public void findStartOfLoopDeltaWhenHasNoStartThrowsSTB() {
        Assert.assertThrows(STB.class, () -> bfLooper.findStartOfLoopDelta("]", 0));
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsNoContentReturnsNegativeOne() {
        assertThat(bfLooper.findStartOfLoopDelta("[]+-", 1)).isEqualTo(-1);
        assertThat(bfLooper.findStartOfLoopDelta("[]", 1)).isEqualTo(-1);
        assertThat(bfLooper.findStartOfLoopDelta("+-[]+-", 3)).isEqualTo(-1);
        assertThat(bfLooper.findStartOfLoopDelta("+-[]", 3)).isEqualTo(-1);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[]]+-", 4)).isEqualTo(-1);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[]]", 4)).isEqualTo(-1);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsOneCharacterOfCommentReturnsNegativeTwo() {
        assertThat(bfLooper.findStartOfLoopDelta("[x]+-", 2)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("[x]", 2)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[x]+-", 4)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[x]", 4)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x]]+-", 5)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x]]", 5)).isEqualTo(-2);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsOneCharacterOfCodeReturnsNegativeTwo() {
        assertThat(bfLooper.findStartOfLoopDelta("[-]+-", 2)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("[-]", 2)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[-]+-", 4)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[-]", 4)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[-]]+-", 5)).isEqualTo(-2);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[-]]", 5)).isEqualTo(-2);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsAnotherEmptyLoopReturnsNegativeThree() {
        assertThat(bfLooper.findStartOfLoopDelta("[[]]+-", 3)).isEqualTo(-3);
        assertThat(bfLooper.findStartOfLoopDelta("[[]]", 3)).isEqualTo(-3);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[]]+-", 5)).isEqualTo(-3);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[]]", 5)).isEqualTo(-3);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[]]]+-", 6)).isEqualTo(-3);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[]]]", 6)).isEqualTo(-3);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsAnotherLoopContainingOneCharacterOfCommentReturnsNegativeFour() {
        assertThat(bfLooper.findStartOfLoopDelta("[[x]]+-", 4)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("[[x]]", 4)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x]]+-", 6)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x]]", 6)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[x]]]+-", 7)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[x]]]", 7)).isEqualTo(-4);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsAnotherLoopContainingOneCharacterOfCodeReturnsNegativeFour() {
        assertThat(bfLooper.findStartOfLoopDelta("[[-]]+-", 4)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("[[-]]", 4)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[-]]+-", 6)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[-]]", 6)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[-]]]+-", 7)).isEqualTo(-4);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[[-]]]", 7)).isEqualTo(-4);
    }

    @Test
    public void findStartOfLoopDeltaWhenLoopContainsAssortedNestedLoopsAndCodeReturnsCorrectValue() {
        assertThat(bfLooper.findStartOfLoopDelta("[x+-[x+-[]x+-[]]x+-]+-", 19)).isEqualTo(-19);
        assertThat(bfLooper.findStartOfLoopDelta("[x+-[x+-[]x+-[]]x+-]", 19)).isEqualTo(-19);
        assertThat(bfLooper.findStartOfLoopDelta("[x+-[x+-[]x+-[]]x+-]+-", 15)).isEqualTo(-11);
        assertThat(bfLooper.findStartOfLoopDelta("[x+-[x+-[]x+-[]]x+-]", 15)).isEqualTo(-11);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]+-", 22)).isEqualTo(-19);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]", 22)).isEqualTo(-19);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]+-", 18)).isEqualTo(-11);
        assertThat(bfLooper.findStartOfLoopDelta("+-[[x+-[x+-[]x+-[]]x+-]]", 18)).isEqualTo(-11);
    }
}
