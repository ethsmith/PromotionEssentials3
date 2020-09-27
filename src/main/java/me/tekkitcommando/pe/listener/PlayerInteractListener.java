package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.economy.EconomyManager;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.promote.Promotion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (block.getType().equals(Material.WALL_SIGN) || block.getType().equals(Material.SIGN)) {
                Sign sign = (Sign) block.getState();

                if (sign.getLine(0).equals(ChatColor.GREEN + "[Promote]")) {
                    String group = sign.getLine(1);

                    if (player.hasPermission("pe.sign.use." + group) || player.hasPermission("pe.sign.use.*") || player.hasPermission("pe.*")) {
                        double price = Double.parseDouble(sign.getLine(2));
                        boolean rankExists = false;

                        for (String rank : PermissionManager.getPermissions().getGroups()) {
                            if (rank.equalsIgnoreCase(group)) {
                                rankExists = true;
                                break;
                            }
                        }

                        if (rankExists) {
                            if (!PermissionManager.getPermissions().getPrimaryGroup(player).equalsIgnoreCase(group)) {
                                if (EconomyManager.getEconomy().has(player, price)) {
                                    EconomyManager.getEconomy().withdrawPlayer(player, price);
                                    new Promotion(player, group);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("UsedSign")).replace("%group%", group));
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoMoney")));
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("CantBuyRank")));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("CantBuyRank")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
                    }
                }
            }
        }
    }
}
