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
                Whether to send reminders to take small breaks after long whiles of playing.
                Type: Boolean
                Default: false""")
        public boolean send_break_reminders = false;
    }

    @Override
    public void validate() {
        if (play_time_safety.interval <=0 || play_time_safety.interval > 60) {
            play_time_safety.interval = 20;
        }
    }
}
