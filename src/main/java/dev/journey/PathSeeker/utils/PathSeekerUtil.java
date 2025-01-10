package dev.journey.PathSeeker.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PathSeekerUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    private String lastSeen;
    private String firstSeen;
    private long playtime;

    public PathSeekerUtil() {
        this.lastSeen = "";
        this.firstSeen = "";
        this.playtime = 0;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    public long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public static String randomColorCode() {
        String[] colorCodes = {"§4", "§c", "§6", "§e", "§2", "§a", "§b", "§3", "§1", "§9", "§d", "§5", "§7", "§8", "§0"};
        return colorCodes[(int) (Math.random() * colorCodes.length)];
    }

    public static void sendPlayerMessage(String message) {
        if (mc.player != null) {
            mc.player.sendMessage(Text.of(message), false);
        }
    }

    public static void logError(String message) {
        System.err.println("[PathSeeker] " + message);
    }

    public static String formatDate(String timestamp) {
        try {
            Instant instant = Instant.parse(timestamp);
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.US);
            return zonedDateTime.format(formatter);
        } catch (Exception e) {
            logError("Failed to parse date: " + timestamp);
            return "Invalid date";
        }
    }

    public static String formatPlaytime(long seconds) {
        if (seconds <= 0) return "none";

        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;

        if (days >= 30) {
            return (days / 30) + " months";
        } else if (days >= 7) {
            return (days / 7) + " weeks";
        } else if (days > 0) {
            return days + " days";
        } else if (hours > 0) {
            return hours + " hours";
        } else if (minutes > 0) {
            return minutes + " minutes";
        } else {
            return seconds + " seconds";
        }
    }

    public String getFormattedLastSeen() {
        return formatDate(this.lastSeen);
    }

    public String getFormattedFirstSeen() {
        return formatDate(this.firstSeen);
    }

    public String getFormattedPlaytime() {
        return formatPlaytime(this.playtime);
    }

    public void updateTimeInfo(String lastSeen, String firstSeen, long playtime) {
        this.lastSeen = lastSeen;
        this.firstSeen = firstSeen;
        this.playtime = playtime;
    }
}