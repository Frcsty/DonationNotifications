package com.github.frcsty.donationnotifications.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Color {

    private static final Pattern HEX_PATTERN = Pattern.compile("#<([A-Fa-f0-9]){6}>");

    /**
     * Formats hex and '&' codes
     * @param s Un-Formatted string
     * @return Formatted string
     */
    public static String translate(String s) {
        Matcher matcher = HEX_PATTERN.matcher(s);

        while (matcher.find()) {
            String hexString = matcher.group();

            hexString = "#" + hexString.substring(2, hexString.length() - 1);
            final ChatColor hex = ChatColor.of(hexString);
            final String before = s.substring(0, matcher.start());
            final String after = s.substring(matcher.end());

            s = before + hex + after;
            matcher = HEX_PATTERN.matcher(s);
        }

        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
