package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

        if (plugin.getPluginConfig().getBoolean("apply.enabled")) {
            if (!(groups.contains(plugin.getPluginConfig().getString("apply.promotion"))) && groups.contains(plugin.getPluginConfig().getString("apply.default"))) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("Join").replace("%player%", player.getName())));
            }
        }

//        if (plugin.getPluginConfig().getBoolean("time.enabled")) {
//            if (plugin.getTimes().contains(player.getUniqueId().toString())) {
//                int timeOnServer = plugin.getTimes().getInt(player.getUniqueId().toString() + ".timeOnServer");
//
//                // Check if offline counting is on
//                if (plugin.getPluginConfig().getBoolean("time.countOffline")) {
//                    // Check time since last login
//                    DateTime dateTimeNow = plugin.getDateTimeHandler().getDateTime(plugin.getDateTimeHandler().getFormatter());
//                    DateTime dateTimeLastLogoff = plugin.getDateTimeHandler().getFormatter().parseDateTime(plugin.getTimes().getString(player.getUniqueId().toString() + ".timeLastLogoff"));
//
//                    int ticksToAdd = Seconds.secondsBetween(dateTimeLastLogoff, dateTimeNow).getSeconds() * 20;
//                    timeOnServer += ticksToAdd;
//
//                    // Check if the player has earned anything
//                    Map<String, String> timedRanks = plugin.getPluginConfig().getMap("time.groups");
//                    String highestRankEarned = null;
//                    String nextHighestRank = null;
//                    int nextHighestTicksNeeded = 0;
//
//                    for (String rank : timedRanks.keySet()) {
//                        int hours;
//                        int minutes;
//                        int seconds;
//
//                        try {
//                            hours = Integer.parseInt(timedRanks.get(rank).substring(0, 2));
//                            minutes = Integer.parseInt(timedRanks.get(rank).substring(3, 5));
//                            seconds = Integer.parseInt(timedRanks.get(rank).substring(6, 8));
//                        } catch (NumberFormatException e) {
//                            plugin.getPluginLogger().warning("Invalid time format! Disabling plugin to prevent corruption of timed ranks!");
//                            plugin.getServer().getPluginManager().disablePlugin(plugin);
//                            return;
//                        }
//
//                        int hoursToSeconds = hours * 3600;
//                        int minutesToSeconds = minutes * 60;
//                        int ticksNeededForRank = (hoursToSeconds + minutesToSeconds + seconds) * 20;
//
//                        if (ticksNeededForRank <= timeOnServer) {
//                            highestRankEarned = rank;
//                        } else {
//                            nextHighestRank = rank;
//                            nextHighestTicksNeeded = ticksNeededForRank;
//                            break;
//                        }
//                    }
//
//                    if (highestRankEarned != null) {
//                        if (!(plugin.getPermission().getPrimaryGroup(player).equalsIgnoreCase(highestRankEarned))) {
//                            plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
//                            plugin.getPermission().playerAddGroup(player, highestRankEarned);
//                        }
//                    }
//
//                    // Restart timer
//                    if (nextHighestRank != null) {
//                        int ticksLeft = nextHighestTicksNeeded - timeOnServer;
//                        String finalNextHighestRank = nextHighestRank;
//
//                        int timedRankup = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
//                            plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
//                            plugin.getPermission().playerAddGroup(player, finalNextHighestRank);
//                        }, ticksLeft);
//
//                        plugin.getTaskHandler().getTimedRankups().add(timedRankup);
//                    }
//                } else {
//                    // Continue time on server from last place left off
//                }
//            } else {
//                // Start counting time
//            }
//        }
    }
}

// String dateTimeLastLoginStr = dateTimeNow.minus(Seconds.seconds((timeOnServer / 20))).toString(plugin.getDateTimeHandler().getFormatter());
// DateTime dateTimeLastLogin = plugin.getDateTimeHandler().getFormatter().parseDateTime(dateTimeLastLoginStr);