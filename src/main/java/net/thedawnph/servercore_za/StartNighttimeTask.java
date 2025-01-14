package net.thedawnph.servercore_za;

import com.ericdebouwer.zombieapocalypse.api.ApocalypseAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class StartNighttimeTask implements Listener {
    private FileConfiguration getConfig() {
        return JavaPlugin.getPlugin(Servercore_za.class).getConfig();
    }
    ApocalypseAPI apocalypseAPI = ApocalypseAPI.getInstance();
    boolean isApocalyptic = apocalypseAPI.isApocalypse(Objects.requireNonNull(getConfig().getString("worldname")));

    public StartNighttimeTask() {
        Bukkit.getScheduler().runTaskTimer(Servercore_za.getPlugin(Servercore_za.class), () -> {
            World world = Bukkit.getWorld(Objects.requireNonNull(getConfig().getString("worldname")));
            if (world == null) {
                Bukkit.getLogger().severe("World not found!");
                return;
            }

            long time = world.getTime();

            // Check if it is nighttime
            if (time >= 13000) {
                if (!isApocalyptic) {
                    // Play bell sound for all players
                    Bukkit.getOnlinePlayers().forEach(player ->
                            player.playSound(player.getLocation(), "event.raid.horn", 1.0f, 1.0f)
                    );
                    apocalypseAPI.startApocalypse(Objects.requireNonNull(getConfig().getString("worldname")), getConfig().getInt("ZA-duration"), getConfig().getInt("ZA-mobs"), true);
                }
            }
        }, 0, 13000L);
    }
}

