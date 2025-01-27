package net.thedawnph.servercore_za.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AboutZA implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Server Core for Zombie Apocalypse \n Developed by princepines for TheDawnPH");
        return true;
    }
}
