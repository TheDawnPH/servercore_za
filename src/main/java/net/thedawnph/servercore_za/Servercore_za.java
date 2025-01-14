package net.thedawnph.servercore_za;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;
import com.ericdebouwer.zombieapocalypse.api.ApocalypseAPI;


public final class Servercore_za extends JavaPlugin {

    private static final Logger LOGGER=Logger.getLogger("servercore_za");
    FileConfiguration config = getConfig();

    @Override
    public void onEnable()
    {
        LOGGER.info("ZA ServerCore has been enabled!");

        registerEvents();
        // Create config folder if it doesn't exist
        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdir()) {
                getLogger().info("Data folder created: " + getDataFolder().getAbsolutePath());
            } else {
                getLogger().info("Failed to create data folder: " + getDataFolder().getAbsolutePath() + ". It might exists?.");
            }
        }

        // Save default configuration file
        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        LOGGER.info("ZA ServerCore disabled");
    }


    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ZombieKill(), this);
        getServer().getPluginManager().registerEvents(new DisableMonsters(), this);
        getServer().getPluginManager().registerEvents(new StartNighttimeTask(), this);
    }
}
