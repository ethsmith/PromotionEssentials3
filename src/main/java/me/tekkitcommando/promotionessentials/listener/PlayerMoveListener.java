package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerMoveListener implements Listener {

    private PromotionEssentials plugin;

    public PlayerMoveListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<String> groups = Arrays.asList(plugin.getPermission().getPlayerGroups(player));

        if (plugin.getPluginConfig().getBoolean("apply.enabled")) {
            if (plugin.getPluginConfig().getBoolean("apply.freeze")) {
                if (!(groups.contains(plugin.getPluginConfig().getString("apply.promotion"))) && groups.contains(plugin.getPluginConfig().getString("apply.default"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Join").replace("%player%", player.getName())));
                    event.setCancelled(true);
                }
            }
        }
    }
}
