package org.tywrapstudios.constructra.config;

import blue.endless.jankson.Comment;
import net.tywrapstudios.blossombridge.api.config.AbstractConfig;

public class ConstructraConfig extends AbstractConfig {
    @Comment("""
            This file has all the configuration options for Constructra.
            Do not change the config_version unless you know what you're doing or else some of your changes may be arbitrarily reset and we will use the default values.""")
    public String config_version = "1.0";
    @Comment("Config related to Resources.")
    public ResourceConfig resources = new ResourceConfig();
    public static class ResourceConfig {
        @Comment("""
                The default purity of each Resource in your world.
                Choices: NONE, IMPURE, NORMAL, PURE or RANDOM.
                Type: String (from Set)
                Default: RANDOM""")
        public String default_resource_purity = "RANDOM";
        @Comment("""
                Whether Nodes can be obstructed. When set to false, Nodes don't need to be destructed first in order to be harvested from.
                Type: Boolean
                Default: true""")
        public boolean do_obstructions = true;
        @Comment("""
                The amount of times you have to consecutively harvest from a Node to be able to de-obstruct it.
                Only applies if do_obstructions is true.
                Type: Integer
                Default: 3""")
        public int consecutive_harvests_for_destruction = 3;
        @Comment("Certain Modifiers for ResourceNode Purity Related Mechanics.")
        public PurityModifiers purity_modifiers = new PurityModifiers();
        public static class PurityModifiers {
            @Comment("""
                    Whether the Purity of a ResourceNode affects at which rate the resource is given.
                    If false is chosen, we will use the rate of a NORMAL node, so feel free to tweak that value.
                    ResourceNodes will still have their Purity displayed!
                    Type: Boolean
                    Default: true""")
            public boolean does_purity_affect_rate = true;
            @Comment("""
                    The Rate at which a PURE node should give its Resource.
                    Type: Double (in seconds)
                    Default: 0.8""")
            public double pure = 0.8;
            @Comment("""
                    The Rate at which a NORMAL node should give its Resource.
                    Type: Double (in seconds)
                    Default: 1.2""")
            public double normal = 1.2;
            @Comment("""
                    The Rate at which an IMPURE node should give its Resource.
                    Type: Double (in seconds)
                    Default: 1.9""")
            public double impure = 1.9;
        }
        @Comment("""
                Whether to display certain visual hints as to what block is the exact ResourceNode Centre. e.g. through the use of particles.
                Type: Boolean
                Default: false""")
        public boolean visualize_centres = false;
    }
    @Comment("Config related to Commands.")
    public CommandConfig commands = new CommandConfig();
    public static class CommandConfig {
        @Comment("""
                The command alias for the Constructra command. Change this if you're having compatibility issues with other mods.
                Type: String
                Default: ca""")
        public String command_alias = "ca";
        @Comment("""
                The permission level required to use the Node commands in general.
                Type: Integer
                Default: 2""")
        public int perm_lvl_nodes = 2;
        @Comment("""
                The permission level required to remove Nodes from the world. (Both nodes flush and nodes purge.)
                Note that this is a dangerous permission to give as it can break the entire world!
                Type: Integer
                Default: 4""")
        public int perm_lvl_nodes_removal = 4;
        @Comment("""
                The permission level required to reload the Constructra config.
                Type: Integer
                Default: 2""")
        public int perm_lvl_reload = 2;
    }
}
