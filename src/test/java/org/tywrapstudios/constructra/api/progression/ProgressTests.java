/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
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

package org.tywrapstudios.constructra.api.progression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tywrapstudios.constructra.Tester;

public class ProgressTests {
    @Test
    public void testProgression() {
        ProgressManager<Tester> manager = new ProgressManager<>();

        ProgressNode<Tester> node = manager
                .literal("yay")
                .then(manager
                        .literal("yay2")
                        .then(manager
                                .literal("yay3")
                                .then(manager
                                        .literal("yay4")
                                )
                        )
                ).build();

        ProgressNode<Tester> node2 = manager.literal("yay").build();
        ProgressNode<Tester> node3 = manager.literal("yay2").build();
        ProgressNode<Tester> node4 = manager.literal("yay3").build();
        ProgressNode<Tester> node5 = manager.literal("yay4").build();
        node2.addChild(node3);
        node3.addChild(node4);
        node4.addChild(node5);

        Assertions.assertEquals(node.print(), node2.print());

        manager.addRoot(node);
        manager.getRoot().print();
    }
}
