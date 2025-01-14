package net.thedawnph.servercore_za;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;


public class ZombieKill implements Listener {
    // randomizer function
    public static int randomizer(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private FileConfiguration getConfig() {
        return JavaPlugin.getPlugin(Servercore_za.class).getConfig();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType().equals(EntityType.ZOMBIE)) {
            event.getDrops().clear();
            List<String> items = getConfig().getStringList("zombie-drops");
            event.getDrops().add(new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(items.get(randomizer(0, items.size())))))));
        }
    }
}
