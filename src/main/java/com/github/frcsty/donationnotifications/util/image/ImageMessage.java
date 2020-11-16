package com.github.frcsty.donationnotifications.util.image;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public final class ImageMessage {
    private final static char TRANSPARENT_CHAR = ' ';

    public String[] lines;

    ImageMessage(final BufferedImage image, final int height, final char imgChar) {
        final Color[][] chatColors = toChatColorArray(image, height);
        lines = toImgMessage(chatColors, imgChar);
    }

    private Color[][] toChatColorArray(final BufferedImage image, final int height) {
        final double ratio = (double) image.getHeight() / image.getWidth();
        int width = (int) (height / ratio);

        if (width > 10) width = 10;
        final BufferedImage resized = resizeImage(image, width, height);

        final Color[][] chatImg = new Color[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                final int rgb = resized.getRGB(x, y);
                chatImg[x][y] = new Color(rgb, true);
            }
        }
        return chatImg;
    }

    private BufferedImage resizeImage(final BufferedImage originalImage, final int width, final int height) {
        final AffineTransform af = new AffineTransform();
        af.scale(
                width / (double) originalImage.getWidth(),
                height / (double) originalImage.getHeight()
        );

        final AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private String[] toImgMessage(final Color[][] colors, final char imgChar) {
        lines = new String[colors[0].length];

        for (int y = 0; y < colors[0].length; y++) {
            final StringBuilder line = new StringBuilder();
            for (int x = 0; x < colors.length; x++) {
                final Color color = colors[x][y];

                if (color != null) {
                            line.append(colorToHex(colors[x][y]))
                            .append(imgChar);
                }
                else {
                    line.append(TRANSPARENT_CHAR);
                }
            }
            lines[y] = line.toString() + ChatColor.RESET;
        }

        return lines;
    }

    private String colorToHex(final Color color) {
        return String.format("#<%02x%02x%02x>", color.getRed(), color.getGreen(), color.getBlue());
    }

    ImageMessage appendText(final String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                lines[y] += " " + text[y];
            }
        }
        return this;
    }

    public ImageMessage appendCenteredText(final String... text) {
        for (int y = 0; y < lines.length; y++) {
            if (text.length > y) {
                int len = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH - lines[y].length();
                lines[y] = lines[y] + center(text[y], len);
            } else {
                return this;
            }
        }
        return this;
    }

    private String center(final String s, final int length) {
        if (s.length() > length) {
            return s.substring(0, length);
        } else if (s.length() == length) {
            return s;
        } else {
            int leftPadding = (length - s.length()) / 2;
            StringBuilder leftBuilder = new StringBuilder();
            for (int i = 0; i < leftPadding; i++) {
                leftBuilder.append(" ");
            }
            return leftBuilder.toString() + s;
        }
    }

    void sendToPlayer(final Player player) {
        for (final String line : lines) {
            player.sendMessage(com.github.frcsty.donationnotifications.util.Color.translate(line));
        }
    }
}
