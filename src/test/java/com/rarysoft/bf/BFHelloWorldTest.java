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
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BFHelloWorldTest {
    @Mock
    private Output output;

    @Test
    public void runHelloWorld() {
        Executor executor = new BFExecutor(new NullInput(), output, new Unsigned8BitMemory());
        Looper looper = new BFLooper();
        BF bf = new BF(executor, looper);

        bf.run(
                " 1 +++++ +++               Set Cell #0 to 8\n" +
                " 2 [\n" +
                " 3     >++++               Add 4 to Cell #1; this will always set Cell #1 to 4\n" +
                " 4     [                   as the cell will be cleared by the loop\n" +
                " 5         >++             Add 4*2 to Cell #2\n" +
                " 6         >+++            Add 4*3 to Cell #3\n" +
                " 7         >+++            Add 4*3 to Cell #4\n" +
                " 8         >+              Add 4 to Cell #5\n" +
                " 9         <<<<-           Decrement the loop counter in Cell #1\n" +
                "10     ]                   Loop till Cell #1 is zero\n" +
                "11     >+                  Add 1 to Cell #2\n" +
                "12     >+                  Add 1 to Cell #3\n" +
                "13     >-                  Subtract 1 from Cell #4\n" +
                "14     >>+                 Add 1 to Cell #6\n" +
                "15     [<]                 Move back to the first zero cell you find; this will\n" +
                "16                         be Cell #1 which was cleared by the previous loop\n" +
                "17     <-                  Decrement the loop Counter in Cell #0\n" +
                "18 ]                       Loop till Cell #0 is zero\n" +
                "19 \n" +
                "20 The result of this is:\n" +
                "21 Cell No :   0   1   2   3   4   5   6\n" +
                "22 Contents:   0   0  72 104  88  32   8\n" +
                "23 Pointer :   ^\n" +
                "24 \n" +
                "25 >>.                     Cell #2 has value 72 which is 'H'\n" +
                "26 >---.                   Subtract 3 from Cell #3 to get 101 which is 'e'\n" +
                "27 +++++ ++..+++.          Likewise for 'llo' from Cell #3\n" +
                "28 >>.                     Cell #5 is 32 for the space\n" +
                "29 <-.                     Subtract 1 from Cell #4 for 87 to give a 'W'\n" +
                "30 <.                      Cell #3 was set to 'o' from the end of 'Hello'\n" +
                "31 +++.----- -.----- ---.  Cell #3 for 'rl' and 'd'\n" +
                "32 >>+.                    Add 1 to Cell #5 gives us an exclamation point\n" +
                "33 >++.                    And finally a newline from Cell #6"
        );

        ArgumentCaptor<Integer> writeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(output, times(13)).write(writeCaptor.capture());
        List<Integer> writtenCharacters = writeCaptor.getAllValues();
        assertThat(writtenCharacters).isNotNull().containsExactly(
                (int) 'H',
                (int) 'e',
                (int) 'l',
                (int) 'l',
                (int) 'o',
                (int) ' ',
                (int) 'W',
                (int) 'o',
                (int) 'r',
                (int) 'l',
                (int) 'd',
                (int) '!',
                (int) '\n'
        );
    }
}