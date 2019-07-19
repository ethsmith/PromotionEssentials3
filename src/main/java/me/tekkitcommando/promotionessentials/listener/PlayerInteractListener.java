package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
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

    private PromotionEssentials plugin;

    public PlayerInteractListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (block.getType().equals(Material.WALL_SIGN) || block.getType().equals(Material.SIGN)) {
                Sign sign = (Sign) block.getState();

                if (sign.getLine(0).equals(ChatColor.GREEN + "[Promote]")) {
                    String group = sign.getLine(1);

                    if (player.hasPermission("pe.sign.use." + group) || player.hasPermission("pe.sign.use")) {
                        double price = Double.valueOf(sign.getLine(2));

                        if (plugin.getEconomy().has(player, price)) {
                            plugin.getEconomy().withdrawPlayer(player, price);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("UsedSign")).replace("%group%", group));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoMoney")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                    }
                }
            }
        }
    }
}
