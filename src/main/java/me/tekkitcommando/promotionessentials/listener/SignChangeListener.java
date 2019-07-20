package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private PromotionEssentials plugin;

    public SignChangeListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String lineOne = event.getLine(0);
        String lineTwo = event.getLine(1);
        String lineThree = event.getLine(2);

        if (lineOne.equals("[Promote]") || lineOne.equals(ChatColor.GREEN + "[Promote]")) {
            if (player.hasPermission("pe.sign.create") || player.hasPermission("pe.sign.*") || player.hasPermission("pe.*")) {
                if (!lineTwo.equals("") && !lineThree.equals("")) {
                    try {
                        Double.valueOf(lineThree);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "This sign requires the 3rd line to be a number!");
                        event.setCancelled(true);
                        return;
                    }
                    event.setLine(0, ChatColor.GREEN + "[Promote]");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("CreatedSign")));
                } else {
                    player.sendMessage(ChatColor.RED + "This type of sign requres the following format: [Promote]\n<rank>\n<price>");
                    event.setCancelled(true);
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
            }
        }
    }
}
