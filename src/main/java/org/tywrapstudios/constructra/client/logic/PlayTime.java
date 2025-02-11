package org.tywrapstudios.constructra.client.logic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.tywrapstudios.constructra.ConstructraClient;
import org.tywrapstudios.constructra.config.ConstructraClientConfig;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class PlayTime {
    public static long playTime;
    public static double seconds;
    public static double minutes;
    public static double hours;
    // This better never become >1 :skull:
    public static double days;

    public static final String TITLE = "text.constructra.reminders.title";
    public static final String DESC_2020 = "text.constructra.reminders.2020";
    public static final String DESC_BREAK = "text.constructra.reminders.break";
    public static final String DESC_BREAK$0 = "text.constructra.reminders.break.variant.0";
    public static final String DESC_BREAK$1 = "text.constructra.reminders.break.variant.1";
    public static final String DESC_BREAK$2 = "text.constructra.reminders.break.variant.2";

    public static void initializeClient() {
        playTime = 0;

        ClientTickEvents.END_CLIENT_TICK.register(tickedClient -> {
            ConstructraClientConfig.PlayTimeSafetyConfig cc = ConstructraClient.config().play_time_safety;
            playTime++;
            seconds = (double) playTime / 20;
            minutes = seconds / 60;
            hours = minutes / 60;
            days = hours / 24;

            Text title = Text.translatable(TITLE);

            if (hours % 2 == 0) {
                if (!cc.send_break_reminders) return;
                sendBreakNotification(tickedClient, title);
                return;
            }

            if (minutes % cc.interval == 0) {
                if (!cc.send_2020_reminders) return;
                send2020Notification(tickedClient, cc, title);
            }
        });
    }

    public static void send2020Notification(MinecraftClient client, ConstructraClientConfig.PlayTimeSafetyConfig cc, Text title) {
        Text text = Text.translatable(DESC_2020, cc.interval, minutes);
        client.getToastManager().add(
                SystemToast.create(client, new SystemToast.Type(15000L), title, text)
        );
    }

    public static void sendBreakNotification(MinecraftClient client, Text title) {
        String usedTime;
        if (days % 1 == 0) usedTime = days + " days";
        else usedTime = hours + " hours";

        String variant = switch (new Random().nextInt(2)) {
            case 0 -> DESC_BREAK$0;
            case 1 -> DESC_BREAK$1;
            default -> DESC_BREAK$2;
        };

        Text text = Text
                .translatable(DESC_BREAK, usedTime)
                .append("\n")
                .append(Text.translatable(variant));

        client.getToastManager().add(
                SystemToast.create(client, new SystemToast.Type(15000L), title, text)
        );
    }
}
