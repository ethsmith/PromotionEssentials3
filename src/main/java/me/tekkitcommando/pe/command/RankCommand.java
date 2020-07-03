package me.tekkitcommando.pe.command;

import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.economy.EconomyManager;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.promote.Promotion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class RankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rank")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to list/buy ranks!");
            } else {
                Player player = (Player) sender;

                if (!(DataManager.getConfig().getBoolean("buy.enabled"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("FunctionDisabled")));
                    return true;
                }

                if (args.length > 0) {
                    String subcommand = args[0];

                    if (args.length == 1) {
                        if (subcommand.equalsIgnoreCase("list")) {
                            if (player.hasPermission("pe.rank.list")) {
                                Set<String> ranks = (Set<String>) DataManager.getConfig().getMap("buy.groups").keySet();

                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getConfig().getString("buy.rankListHeader")));

                                for (String rank : ranks) {
                                    player.sendMessage(ChatColor.valueOf(DataManager.getConfig().getString("buy.rankListColor")) + rank);
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("InvalidArgs")));
                        }
                    } else if (args.length == 2) {
                        String rank = args[1];

                        if (subcommand.equalsIgnoreCase("buy")) {
                            if (DataManager.getConfig().contains("buy.groups." + rank)) {
                                if (player.hasPermission("pe.rank.buy." + rank) || player.hasPermission("pe.rank.buy.*")) {
                                    if (!PermissionManager.getPermissions().getPrimaryGroup(player).equalsIgnoreCase(rank)) {
                                        if (EconomyManager.getEconomy().has(player, DataManager.getConfig().getDouble("buy.groups." + rank))) {
                                            EconomyManager.getEconomy().withdrawPlayer(player, DataManager.getConfig().getDouble("buy.groups." + rank));
                                            new Promotion(player, rank);
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("BoughtRank")).replace("%group%", rank));
                                        } else {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoMoney")));
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("CantBuyRank")));
                                    }
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("CantBuyRank")));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("InvalidArgs")));
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("InvalidArgs")));
                }
            }
        }
        return true;
    }
}
