package org.padrewin.spidersense.Commands;

import org.padrewin.spidersense.SpiderSense;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final SpiderSense plugin;

    public Commands(SpiderSense plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfigFiles();

            String reloadMessage = plugin.getPrefix() + plugin.getReloadMessage();
            String noPermissionMessage = plugin.getPrefix() + plugin.getNoPermissionMessage();

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("spidersense.admin")) {
                    player.sendMessage(reloadMessage);
                } else {
                    player.sendMessage(noPermissionMessage);
                }
            } else {
                sender.sendMessage(reloadMessage);
            }
            return true;
        }
        return false;
    }
}
