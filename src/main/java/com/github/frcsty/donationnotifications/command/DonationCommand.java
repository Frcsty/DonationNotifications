package com.github.frcsty.donationnotifications.command;

import com.github.frcsty.donationnotifications.DonationsPlugin;
import com.github.frcsty.donationnotifications.util.Color;
import com.github.frcsty.donationnotifications.util.image.PictureWrapper;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("donation")
public final class DonationCommand extends CommandBase {

    private final DonationsPlugin plugin;

    public DonationCommand(final DonationsPlugin plugin) {
        this.plugin = plugin;
    }

    @SubCommand("send")
    public void onCommand(final CommandSender sender, final String target, final String[] placeholders) {
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);

        if (!offlinePlayer.isOnline()) {
            return;
        }

        final Player targetPlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
        if (targetPlayer == null) {
            return;
        }

        final PictureWrapper wrapper = new PictureWrapper(plugin, targetPlayer, placeholders);
        wrapper.runTaskAsynchronously(plugin);
    }

}
