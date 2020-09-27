package me.tekkitcommando.pe.promote;

import me.tekkitcommando.pe.PromotionEssentials;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.permission.PermissionManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Promotion {

    public Promotion(Player player, String rank) {

        Permission permissions = PermissionManager.getPermissions();
        permissions.playerRemoveGroup(player, permissions.getPrimaryGroup(player));
        permissions.playerAddGroup(null, player, rank);

        List<String> consoleCommands = DataManager.getConfig().getStringList("commands." + rank + ".console");
        List<String> playerCommands = DataManager.getConfig().getStringList("commands." + rank + ".player");

        if (consoleCommands != null) {
            PromotionEssentials plugin = PromotionEssentials.getInstance();
            ConsoleCommandSender console = plugin.getServer().getConsoleSender();
            for (String command : consoleCommands)
                plugin.getServer().dispatchCommand(console, command.replace("%player%", player.getName()));
        }

        if (playerCommands != null) {
            for (String command : playerCommands)
                player.performCommand(command);
        }
    }
}
