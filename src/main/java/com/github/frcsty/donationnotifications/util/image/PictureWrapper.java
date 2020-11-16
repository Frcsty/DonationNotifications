package com.github.frcsty.donationnotifications.util.image;

import com.github.frcsty.donationnotifications.DonationsPlugin;
import com.github.frcsty.donationnotifications.util.Replace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.image.BufferedImage;
import java.util.List;

public final class PictureWrapper extends BukkitRunnable {

    private final DonationsPlugin plugin;
    private final Player player;
    private final String[] placeholders;

    public PictureWrapper(final DonationsPlugin plugin, final Player player, final String[] placeholders) {
        this.plugin = plugin;
        this.player = player;
        this.placeholders = placeholders;
    }

    @Override
    public void run() {
        sendImage();
    }

    private ImageMessage getMessage() {
        final BufferedImage image = PictureUtil.getImage(player.getName());

        return PictureUtil.getMessage(
                translatePlaceholders(),
                image
        );
    }

    private List<String> translatePlaceholders() {
        List<String> message = plugin.getConfig().getStringList("donation-message");

        for (int placeholderIndex = 0; placeholderIndex < placeholders.length; placeholderIndex++) {
            final String placeholder = "{arg_" + (placeholderIndex + 1) + "}";
            final String value = placeholders[placeholderIndex];

            message = Replace.replaceList(message,
                    placeholder, value
            );
        }

        return message;
    }

    private void sendImage() {
        final ImageMessage pictureMessage = getMessage();

        if (pictureMessage == null) return;
        PictureUtil.sendOutPictureMessage(plugin, pictureMessage);
    }

}
