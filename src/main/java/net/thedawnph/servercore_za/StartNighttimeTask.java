package net.thedawnph.servercore_za;

import com.ericdebouwer.zombieapocalypse.api.ApocalypseAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Objects;

public class StartNighttimeTask implements Listener {
    ApocalypseAPI apocalypseAPI = ApocalypseAPI.getInstance();
    public StartNighttimeTask() {
        Bukkit.getScheduler().runTaskTimer(Servercore_za.getPlugin(Servercore_za.class), () -> {
            long time = Objects.requireNonNull(Bukkit.getWorld("world")).getTime();
            if (time >= 13000 && time <= 23000) { // Check if it is nighttime
                boolean isApocalyptic = apocalypseAPI.isApocalypse("world");
                if (isApocalyptic) {
                    apocalypseAPI.startApocalypse("world", 300, 1, true);
                    Bukkit.getWorlds().getFirst().playSound(Bukkit.getWorlds().getFirst().getSpawnLocation(), "block.bell.use", 1, 1);
                }
            }
        }, 0, 1200L); // Run every minute
    }
}
