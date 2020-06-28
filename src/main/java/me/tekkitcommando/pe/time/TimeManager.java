package me.tekkitcommando.pe.time;

import lombok.Getter;
import lombok.Setter;
import me.tekkitcommando.pe.PromotionEssentials;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.promote.Promotion;
import me.tekkitcommando.pe.promote.PromotionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimeManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    private static final Map<UUID, Long> playTimes = new HashMap<>();
    @Getter
    private static int timerId;

    @Getter
    @Setter
    private static boolean countOffline;

    public static int startTimePromote() {
        timerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    UUID playerId = player.getUniqueId();
                    long playTimeSec;

                    // add one second to their total time
                    if (playTimes.containsKey(playerId))
                        playTimes.replace(playerId, getTotalTimePlayed(playerId) + 1L);
                    else
                        playTimes.put(playerId, 1L);

                    // get there total time played or as a whole
                    if (countOffline)
                        playTimeSec = getTotalTime(playerId);
                    else
                        playTimeSec = getTotalTimePlayed(playerId);

                    // see what rank they earned
                    String eligibleRank = PromotionManager.calculatePromotion(playerId, playTimeSec);
                    // promote them if they don't already have that rank adn they aren't an admin rank
                    if (eligibleRank != null) {
                        new Promotion(player, eligibleRank);
                        player.sendMessage(ChatColor.GREEN + DataManager.getMessages().getString("PromotedAfterTime").replace("%group%", eligibleRank));
                    }
                }
            }
        }, 20L, 20L);

        return timerId;
    }

    public static long getTotalTimePlayed(UUID uuid) {
        return playTimes.get(uuid);
    }

    public static long getTotalTime(UUID uuid) {
        // DateTime of the first time they ever joined
        LocalDateTime firstJoin = LocalDateTime.parse(DataManager.getTimes().getString(uuid.toString() + ".firstJoin"));
        // DateTime of now
        LocalDateTime now = LocalDateTime.now();

        // Calculate the seconds of that
        return ChronoUnit.SECONDS.between(firstJoin, now);
    }

}
