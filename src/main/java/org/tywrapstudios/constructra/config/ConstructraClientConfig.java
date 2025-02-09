package org.tywrapstudios.constructra.config;

import blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.tywrapstudios.blossombridge.api.config.ConfigClass;

@Environment(EnvType.CLIENT)
public class ConstructraClientConfig implements ConfigClass {
    @Comment("""
            This file has all the configuration options for Constructra on the client side.
            Changes here will not affect a dedicated server (nor other players online)!
            
            The following value is irrelevant, do not change it, though.""")
    public String run_info = "None";
    @Comment("""
            The Unit System to use for Measurements.
            Choices: METRIC, FREEDOM
            Type: String (from set)
            Default: METRIC""")
    public String measure_units = "METRIC";
    @Comment("Config related to your Play Time Safety.")
    public PlayTimeSafetyConfig play_time_safety = new PlayTimeSafetyConfig();
    public static class PlayTimeSafetyConfig {
        @Comment("""
                Whether to send 2020 reminders.
                After 20 minutes of playing, look away for 20 seconds.
                Type: Boolean
                Default: false""")
        public boolean send_2020_reminders = false;
        @Comment("""
                The amount of minutes between 2020 reminders.
                Only applies if send_2020_reminders is true.
                Type: Integer
                Range: >0-60
                Default: 20""")
        public int interval = 20;
        @Comment("""
                Whether to send reminders to take small breaks after a long while of playing.
                Type: Boolean
                Default: false""")
        public boolean send_break_reminders = false;
    }

    @Override
    public void validate() {
        if (play_time_safety.interval <=0 || play_time_safety.interval > 60) {
            play_time_safety.interval = 20;
            //throw new InvalidConfigFileException("Option out of range (>0-60): " + play_time_safety.playing_interval);
        }
    }
}
