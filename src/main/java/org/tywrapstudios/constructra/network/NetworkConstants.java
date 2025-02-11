package org.tywrapstudios.constructra.network;

import net.minecraft.util.Identifier;

import static org.tywrapstudios.constructra.Constructra.id;

public class NetworkConstants {
    public static final Identifier NODE_QUERY_REQUEST_C2S = id("node_query_request_c2s");
    public static final Identifier NODE_QUERY_REQUEST_S2C = id("node_query_request_s2c");
    public static final Identifier HARVEST_START_EVENT = id("start_resource_harvest");
    public static final Identifier HARVEST_END_EVENT = id("end_resource_harvest");
    public static final Identifier PORTABLE_MINER_PICKUP_REQUEST = id("portable_miner_pickup_request");
}
