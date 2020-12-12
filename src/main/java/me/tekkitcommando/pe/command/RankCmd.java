package me.tekkitcommando.pe.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.economy.EconomyManager;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.promote.Promotion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;

@Command("rank")
public class RankCmd {

    @Default
    public static void rank(Player player) {
        player.sendMessage(ChatColor.RED + "Subcommands are buy, list");
    }

    @Subcommand("list")
    public static void list(Player player) {
        if (player.hasPermission("pe.rank.list")) {
            Set<String> ranks = (Set<String>) DataManager.getConfig().getMap("buy.groups").keySet();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getConfig().getString("buy.rankListHeader")));

            for (String rank : ranks) {
                player.sendMessage(ChatColor.valueOf(DataManager.getConfig().getString("buy.rankListColor")) + rank);
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
        }
    }

    @Subcommand("buy")
    public static void buy(Player player, @AStringArgument String rank) {
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
    }
}
