package net.thedawnph.servercore_za;

import com.ericdebouwer.zombieapocalypse.api.ApocalypseAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class StartNighttimeTask {

    private final ApocalypseAPI apocalypseAPI = ApocalypseAPI.getInstance();
    private final JavaPlugin plugin;
    private boolean isApocalyptic = false;

    public StartNighttimeTask(JavaPlugin plugin) {
        this.plugin = plugin;
        startScheduler();
    }

    private void startScheduler() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            String worldName = plugin.getConfig().getString("worldname");
            if (worldName == null) {
                Bukkit.getLogger().severe("No worldname specified in the configuration!");
                return;
            }

            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                Bukkit.getLogger().severe("World not found: " + worldName);
                return;
            }

            long time = world.getTime();

            // Check if it is nighttime
            if (time >= 12541 && time <= 23458) {
                if (!Bukkit.getOnlinePlayers().isEmpty() && !isApocalyptic) {
                    // Play raid horn sound for all players
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), "item.goat_horn.sound.5", 16.0f, 1.0f);
                    }

                    long duration = 0;
                    if (plugin.getConfig().getInt("ZA-duration") == 0) {
                        // Start the apocalypse
                        apocalypseAPI.startApocalypse(
                                worldName, true
                        );
                    } else {
                        // Start the apocalypse
                        apocalypseAPI.startApocalypse(
                                worldName,
                                plugin.getConfig().getInt("ZA-duration"),
                                plugin.getConfig().getInt("ZA-mobs"),
                                true
                        );
                    }


                    isApocalyptic = true;
                }
            } else {
                if (isApocalyptic) {
                    // End the apocalypse
                    apocalypseAPI.endApocalypse(worldName, true);

                    // Play raid horn sound for all players
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), "item.goat_horn.sound.3", 16.0f, 1.0f);
                    }

                    // Remove all dropped items from the world
                    world.getEntities().stream()
                            .filter(entity -> entity instanceof Item)
                            .forEach(Entity::remove);

                    isApocalyptic = false;
                }
            }
        }, 0L, 40L); // Run every 2 seconds
    }
}