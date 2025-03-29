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

import java.util.*;
import java.util.function.Predicate;

public class ProgressBuilder<S> {
    private final String name;
    private final Set<ProgressBuilder<S>> children;
    private Predicate<S> requirement = (s) -> true;

    protected ProgressBuilder(String name) {
        this.name = name;
        this.children = new HashSet<>();
    }

    public static <S> ProgressBuilder<S> literal(String name) {
        return new ProgressBuilder<>(name);
    }

    public ProgressBuilder<S> then(ProgressBuilder<S> builder) {
        this.children.add(builder);
        return this;
    }

    public ProgressBuilder<S> then(ProgressNode<S> node) {
        ProgressBuilder<S> builder = new ProgressBuilder<>(node.pid);
        builder.requires(node.requirement);
        for (ProgressNode<S> child : node.children) {
            builder.then(child);
        }
        this.children.add(builder);
        return this;
    }

    public ProgressBuilder<S> requires(Predicate<S> requirement) {
        this.requirement = requirement;
        return this;
    }

    public ProgressNode<S> build() {
        return build(null);
    }

    private ProgressNode<S> build(String parentPath) {
        ProgressNode<S> node = new ProgressNode<>(this.name, this.requirement);

        // If we have a parent path, update this node's path
        if (parentPath != null) {
            node.modPath((s) -> parentPath + "." + node.pid);
        }

        // When building children, pass this node's path as parent path
        for (ProgressBuilder<S> childBuilder : this.children) {
            node.addChild(childBuilder.build(node.path), (s) -> s);
        }

        return node;
    }
}
