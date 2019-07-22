package me.tekkitcommando.promotionessentials.handler;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.HashMap;
import java.util.Map;

public class TimePromoteHandler {

    private PromotionEssentials plugin;
    private Map<String, String> timedRanks;

    public TimePromoteHandler(PromotionEssentials plugin) {
        this.plugin = plugin;
        timedRanks = plugin.getPluginConfig().getMap("time.groups");
    }

    public Map<String, String> getTimedRanks() {
        return timedRanks;
    }

    public int startTimePromote() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Map<Player, Integer> totalTimes = new HashMap<>();

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                totalTimes.put(player, getTotalTime(player));
                String rankEarned = calculatePromotion(totalTimes.get(player));

                if (rankEarned != null) {
                    if (!(plugin.getPermission().getPrimaryGroup(player).equalsIgnoreCase(rankEarned))) {
                        plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
                        plugin.getPermission().playerAddGroup(player, rankEarned);

                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("PromotedAfterTime")).replace("%group%", rankEarned));
                    }
                }
            }
        }, 20L, 20L);
    }

    private int getTotalTime(Player player) {
        DateTime latestLogin = plugin.getDateTimeHandler().getFormatter().parseDateTime(plugin.getTimes().getString(player.getUniqueId().toString() + ".latestLogin"));
        int prevTotalTime = plugin.getTimes().getInt(player.getUniqueId().toString() + ".totalTime");
        int ticksToAdd = 0;

        if (plugin.getPluginConfig().getBoolean("time.countOffine")) {
            DateTime lastLogoff = plugin.getDateTimeHandler().getFormatter().parseDateTime(plugin.getTimes().getString(player.getUniqueId().toString() + ".lastLogoff"));

            if (lastLogoff == null) {
                lastLogoff = latestLogin;
            }

            ticksToAdd += Seconds.secondsBetween(lastLogoff, latestLogin).getSeconds() * 20;
        }

        DateTime dateTimeNow = plugin.getDateTimeHandler().getDateTime();
        ticksToAdd += Seconds.secondsBetween(latestLogin, dateTimeNow).getSeconds() * 20;

        return prevTotalTime + ticksToAdd;
    }

    private String calculatePromotion(int totalTime) {
        String highestRankEarned = null;

        for (String rank : timedRanks.keySet()) {
            int hours;
            int minutes;
            int seconds;

            try {
                hours = Integer.parseInt(timedRanks.get(rank).substring(0, 2));
                minutes = Integer.parseInt(timedRanks.get(rank).substring(3, 5));
                seconds = Integer.parseInt(timedRanks.get(rank).substring(6, 8));
            } catch (NumberFormatException e) {
                plugin.getPluginLogger().warning("Invalid time format! Disabling plugin to prevent corruption of timed ranks!");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return null;
            }

            int hoursToSeconds = hours * 3600;
            int minutesToSeconds = minutes * 60;
            int ticksNeededForRank = (hoursToSeconds + minutesToSeconds + seconds) * 20;

            if (ticksNeededForRank <= totalTime) {
                highestRankEarned = rank;
            }
        }

        return highestRankEarned;
    }
}
