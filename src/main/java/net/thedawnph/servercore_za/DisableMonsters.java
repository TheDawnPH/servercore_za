package net.thedawnph.servercore_za;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class DisableMonsters implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Server server = Bukkit.getServer();
        Objects.requireNonNull(server.getWorld("world")).setSpawnFlags(false, true);
    }
}
