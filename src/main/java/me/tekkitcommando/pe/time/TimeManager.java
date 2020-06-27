package me.tekkitcommando.pe.time;

import lombok.Getter;
import me.tekkitcommando.pe.PromotionEssentials;
import me.tekkitcommando.pe.promote.Promotion;
import me.tekkitcommando.pe.promote.PromotionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimeManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();
    @Getter
    private static final Map<UUID, Long> playTime = new HashMap<>();
    @Getter
    private static int timerId;

    public static int startTimePromote() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    UUID playerId = player.getUniqueId();
                    // add one second to their total time
                    playTime.replace(playerId, getTotalTimePlayed(playerId) + 1L);
                    // get there total time
                    long playTimeSec = getTotalTimePlayed(playerId);
                    // see what rank they earned
                    String eligibleRank = PromotionManager.calculatePromotion(playerId, playTimeSec);
                    // promote them if they don't already have that rank adn they aren't an admin rank
                    if (eligibleRank != null) {
                        new Promotion(player, eligibleRank);
                    }
                }
            }
        }, 20L, 20L);

        timerId = id;
        return id;
    }

    public static long getTotalTimePlayed(UUID uuid) {
        return playTime.get(uuid);
    }

    public static long getTotalTime(UUID uuid) {
        // DateTime of the first time they ever joined

        return 0L;
    }

}
