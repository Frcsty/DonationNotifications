package com.github.frcsty.donationnotifications;

import com.github.frcsty.donationnotifications.command.DonationCommand;
import com.github.frcsty.donationnotifications.util.image.ImageMessage;
import com.github.frcsty.donationnotifications.util.image.PictureUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public final class DonationsPlugin extends JavaPlugin {

    // Name: Frcsty - UUID: a1b72b20-97e0-476e-8a78-db9c9dcf93a4

    @Override
    public void onEnable() {
        saveDefaultConfig();

        final CommandManager commandManager = new CommandManager(this);
        commandManager.register(new DonationCommand(this));
    }

    @Override
    public void onDisable() {
        reloadConfig();
    }

}
