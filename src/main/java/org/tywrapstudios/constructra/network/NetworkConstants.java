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

package org.tywrapstudios.constructra.network;

import net.minecraft.util.Identifier;

import static org.tywrapstudios.constructra.Constructra.id;

public class NetworkConstants {
    public static final Identifier NODE_QUERY_REQUEST_C2S = id("node_query_request_c2s");
    public static final Identifier NODE_QUERY_REQUEST_S2C = id("node_query_request_s2c");
    public static final Identifier HARVEST_START_EVENT = id("start_resource_harvest");
    public static final Identifier HARVEST_END_EVENT = id("end_resource_harvest");
    public static final Identifier PORTABLE_MINER_PICKUP_REQUEST = id("portable_miner_pickup_request");
    public static final Identifier GRAB_ALL_REQUEST = id("grab_all_request");
}
