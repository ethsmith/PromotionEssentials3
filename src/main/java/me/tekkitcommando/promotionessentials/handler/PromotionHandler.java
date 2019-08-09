package me.tekkitcommando.promotionessentials.handler;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PromotionHandler {

    private PromotionEssentials plugin;

    public PromotionHandler(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    public void promotePlayer(Player player, String rank) {
        plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
        plugin.getPermission().playerAddGroup(null, player, rank);

        performPromotionCommands(player, rank);
    }

    public void performPromotionCommands(Player player, String rank) {
        List<String> consoleCommands = plugin.getPluginConfig().getStringList("commands." + rank + ".console");
        List<String> playerCommands = plugin.getPluginConfig().getStringList("commands." + rank + ".player");


        if (consoleCommands != null) {
            ConsoleCommandSender console = plugin.getServer().getConsoleSender();

            for (String command : consoleCommands) {
                plugin.getServer().dispatchCommand(console, command.replace("%player%", player.getName()));
            }
        }

        if (playerCommands != null) {
            for (String command : playerCommands) {
                player.performCommand(command);
            }
        }
    }
}
