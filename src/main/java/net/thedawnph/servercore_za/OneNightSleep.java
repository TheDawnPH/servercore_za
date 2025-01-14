package net.thedawnph.servercore_za;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OneNightSleep implements Listener {
    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) {

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            event.getPlayer().getWorld().setTime(23000);
        }
    }
}
