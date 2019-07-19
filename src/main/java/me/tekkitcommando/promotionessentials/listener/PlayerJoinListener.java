package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

public class PlayerJoinListener implements Listener {

    private PromotionEssentials plugin;

    public PlayerJoinListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<String> groups = Arrays.asList(plugin.getPermission().getPlayerGroups(player));

        if (!(groups.contains(plugin.getPluginConfig().getString("apply.promotion"))) && groups.contains(plugin.getPluginConfig().getString("apply.default"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Join").replace("%player%", player.getName())));
        }

        if (plugin.getPluginConfig().getBoolean("time.enabled")) {
            if (plugin.getTimes().contains(player.getUniqueId().toString())) {
                int timeOnServer = plugin.getTimes().getInt(player.getUniqueId().toString() + ".timeOnServer");

                // Check if offline counting is on
                if (plugin.getPluginConfig().getBoolean("time.countOffline")) {
                    // Check time since last login
                    DateTime dateTimeNow = plugin.getDateTimeHandler().getDateTime(plugin.getDateTimeHandler().getFormatter());
                    DateTime dateTimeLastLogoff = plugin.getDateTimeHandler().getFormatter().parseDateTime(plugin.getTimes().getString(player.getUniqueId().toString() + ".timeLastLogoff"));

                    int ticksToAdd = Seconds.secondsBetween(dateTimeLastLogoff, dateTimeNow).getSeconds() * 20;
                    timeOnServer += ticksToAdd;

                    // Check if the player has earned anything

                    // Restart timer

//                    String dateTimeLastLoginStr = dateTimeNow.minus(Seconds.seconds((timeOnServer / 20))).toString(plugin.getDateTimeHandler().getFormatter());
//                    DateTime dateTimeLastLogin = plugin.getDateTimeHandler().getFormatter().parseDateTime(dateTimeLastLoginStr);
//


                } else {
                    // Continue time on server from last place left off
                }
            } else {
                // Start counting time
            }
        }
    }
}
