package me.tekkitcommando.promotionessentials.command;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ApplyCommand implements CommandExecutor {

    private PromotionEssentials plugin;

    public ApplyCommand(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("apply")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player for that command!");
            } else {
                Player player = (Player) sender;

                if (!(plugin.getPluginConfig().getBoolean("apply.enabled"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("FunctionDisabled")));
                    return true;
                }

                if (player.hasPermission("pe.apply")) {
                    if (args.length < 1) {
                        player.sendMessage(ChatColor.RED + "[PromotionEssentials] You need to enter a password!");
                    } else {
                        String password = args[0];

                        if (plugin.getPluginConfig().getString("apply.password").equals(password)) {
                            // Promote player
                            plugin.getPermission().playerRemoveGroup(player, plugin.getPluginConfig().getString("apply.default"));
                            plugin.getPermission().playerAddGroup(player, plugin.getPluginConfig().getString("apply.promotion"));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("WrongPW")));

                            if (plugin.getPluginConfig().getBoolean("apply.kickWrongPW")) {
                                player.kickPlayer(plugin.getMessages().getString("WrongPW"));
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                }
            }
        }
        return true;
    }
}
