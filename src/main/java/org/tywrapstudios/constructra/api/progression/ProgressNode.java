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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProgressNode<S> {
    public final String pid;
    protected String path;
    protected final Set<ProgressNode<S>> children;
    protected boolean locked;
    protected boolean finished;
    protected final Predicate<S> requirement;

    public ProgressNode(String pid, Predicate<S> requirement) {
        this.pid = pid;
        this.path = pid;
        this.children = new HashSet<>();
        this.locked = true;
        this.finished = false;
        this.requirement = requirement;
    }

    public ProgressNode<S> addChild(ProgressNode<S> child) {
        this.children.add(child.modPath(this.path));
        return this;
    }

    public ProgressNode<S> addChild(ProgressNode<S> child, Function<String, String> path) {
        this.children.add(child.modPath(path));
        return this;
    }

    public ProgressNode<S> getChild(String pid) {
        for (ProgressNode<S> child : children) {
            if (child.pid.equals(pid)) {
                return child;
            }
            ProgressNode<S> found = child.getChild(pid);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    public boolean attemptFinish(S target) {
        if (!locked && this.requirement.test(target)) {
            this.finished = true;
            for (ProgressNode<S> child : children) {
                child.locked = false;
            }
            return true;
        }
        return false;
    }

    public ProgressNode<S> modPath(Function<String, String> path) {
        this.path = path.apply(this.path);
        return this;
    }

    public ProgressNode<S> modPath(String parentPath) {
        return this.modPath(s -> parentPath + "." + s);
    }

    public void print() {
        System.out.println(this);
        for (ProgressNode<S> child : children) {
            child.print();
        }
    }

    public String print(String origin) {
        origin = this.toString();
        for (ProgressNode<S> child : children) {
            origin += "\n" + child.print(origin);
        }
        return origin;
    }

    @Override
    public String toString() {
        return "ProgressNode{" +
                "pid='" + pid + '\'' +
                ", children=" + children.size() +
                ", locked=" + locked +
                ", finished=" + finished +
                "}<" + path + ">";
    }
}
