package me.tekkitcommando.promotionessentials.command;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class RankCommand implements CommandExecutor {

    private PromotionEssentials plugin;

    public RankCommand(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to list/buy ranks!");
            } else {
                Player player = (Player) sender;
                String subcommand = args[0];

                if (!(plugin.getPluginConfig().getBoolean("buy.enabled"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("FunctionDisabled")));
                    return true;
                }

                if (args.length == 1) {
                    if (subcommand.equalsIgnoreCase("list")) {
                        if (player.hasPermission("pe.rank.list")) {
                            Set<String> ranks = plugin.getPluginConfig().getMap("buy.groups").keySet();

                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getPluginConfig().getString("buy.rankListHeader")));

                            for (String rank : ranks) {
                                player.sendMessage(ChatColor.valueOf(plugin.getPluginConfig().getString("buy.rankListColor")) + rank);
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                    }
                } else if (args.length == 2) {
                    String rank = args[1];

                    if (subcommand.equalsIgnoreCase("buy")) {
                        if (plugin.getPluginConfig().contains("buy.groups." + rank)) {
                            if (player.hasPermission("pe.rank.buy." + rank) || player.hasPermission("pe.rank.buy")) {
                                if (plugin.getEconomy().has(player, plugin.getConfig().getDouble("buy.groups." + rank))) {
                                    plugin.getEconomy().withdrawPlayer(player, plugin.getConfig().getDouble("buy.groups." + rank));
                                    plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
                                    plugin.getPermission().playerAddGroup(player, rank);
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoMoney")));
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                }
            }
        }
        return true;
    }
}
