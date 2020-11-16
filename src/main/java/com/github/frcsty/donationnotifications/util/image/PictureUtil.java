package com.github.frcsty.donationnotifications.util.image;

import com.github.frcsty.donationnotifications.DonationsPlugin;
import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public final class PictureUtil {

    private static URL newURL(final String playerName) {
        final String url = String.format("https://minepic.org/avatar/8/%s", playerName);

        try {
            return new URL(url);
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static BufferedImage getImage(final String playerName) {
        final URL headImage = newURL(playerName);

        // URL Formatted correctly.
        if (headImage != null) {
            try {
                //User-Agent is needed for HTTP requests
                final HttpURLConnection connection = (HttpURLConnection) headImage.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                return ImageIO.read(connection.getInputStream());
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    static ImageMessage getMessage(final List<String> message, final BufferedImage image) {
        int imageDimensions = 8, count = 0;
        final ImageMessage imageMessage = new ImageMessage(image, imageDimensions, ImageChar.BLOCK.getCharacter());
        String[] msg = new String[imageDimensions];

        for (final String line : message) {
            if (count > msg.length) break;
            msg[count++] = line;
        }

        while (count < imageDimensions) {
            msg[count--] = "";
        }

        return imageMessage.appendText(msg);
    }

    static void sendOutPictureMessage(final DonationsPlugin plugin, final ImageMessage pictureMessage) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
            Bukkit.getServer().getOnlinePlayers().forEach(pictureMessage::sendToPlayer)
        );
    }

}