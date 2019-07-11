package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerChatListener implements Listener {

    private PromotionEssentials plugin;

    public PlayerChatListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        List<String> groups = Arrays.asList(plugin.getPermission().getPlayerGroups(player));

        if (plugin.getPluginConfig().getBoolean("apply.mute")) {
            if (!(groups.contains(plugin.getPluginConfig().getString("apply.promotion"))) && groups.contains("apply.default")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Join")));
                event.setCancelled(true);
            }
        }

        if (plugin.getPluginConfig().getBoolean("apply.blockViewChat")) {
            for (Player chatPlayer : plugin.getServer().getOnlinePlayers()) {
                List<String> chatPlayerGroups = Arrays.asList(plugin.getPermission().getPlayerGroups(chatPlayer));

                if (!(chatPlayerGroups.contains(plugin.getPluginConfig().getString("apply.promotion"))) && chatPlayerGroups.contains("apply.default")) {
                    event.getRecipients().remove(chatPlayer);
                }
            }
        }
    }
}
