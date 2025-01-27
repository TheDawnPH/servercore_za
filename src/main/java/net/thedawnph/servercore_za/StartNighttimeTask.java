package net.thedawnph.servercore_za;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class StartNighttimeTask {

    private final JavaPlugin plugin;
    public boolean isApocalyptic = false;

    public static int randomizer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

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
                int random = randomizer(0, 2);
                if (!Bukkit.getOnlinePlayers().isEmpty() && !isApocalyptic) {
                    if (random == 1) {
                        // Start the apocalypse
                        isApocalyptic = true;

                        // Play raid horn sound for all players
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), "item.goat_horn.sound.5", 16.0f, 1.0f);
                        }

                        // spawn zombies around players in the world with a radius set on config
                        int radius = plugin.getConfig().getInt("zombie-radius");
                        int amount = plugin.getConfig().getInt("zombie-amount");
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            for (int mobs = 0; mobs < amount; mobs++) {
                                int x = randomizer(10, radius);
                                int z = randomizer(10, radius);
                                world.spawnEntity(player.getLocation().add(x, world.getHighestBlockYAt(x, z), z), org.bukkit.entity.EntityType.ZOMBIE);
                            }
                        }
                    } else {
                        // send message to all players
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage("The night is quiet...");
                        }
                    }

                    isApocalyptic = true;
                }
            } else {
                if (isApocalyptic) {
                    // kill all zombies
                    world.getEntities().stream()
                            .filter(entity -> entity.getType().name().equals("ZOMBIE"))
                            .forEach(Entity::remove);

                    // Play raid horn sound for all players
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), "item.goat_horn.sound.6", 16.0f, 1.0f);
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