package me.tekkitcommando.pe.promote;

import lombok.Getter;
import me.tekkitcommando.pe.PromotionEssentials;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.time.TimeManager;
import org.bukkit.entity.Player;

import java.util.*;

public class PromotionManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    private static final List<String> blacklistedRanks = new ArrayList<>();
    @Getter
    private static final Map<String, Long> timedRanks = new HashMap<>();

    public static String calculatePromotion(UUID uuid, long playTimeSec) {
        Player player = plugin.getServer().getPlayer(uuid);

        if (blacklistedRanks.contains(PermissionManager.getPermissions().getPrimaryGroup(player))) {
            return null;
        }

        String rankEarned = null;

        long totalTime;

        if (TimeManager.isCountOffline())
            totalTime = TimeManager.getTotalTime(uuid);
        else
            totalTime = TimeManager.getTotalTimePlayed(uuid);

        for (String rank : timedRanks.keySet()) {
            if (timedRanks.get(rank) <= totalTime) {
                // eligable
                rankEarned = rank;
            }
        }

        if (!PermissionManager.getPermissions().getPrimaryGroup(player).equalsIgnoreCase(rankEarned)) {
            return rankEarned;
        }

        return null;
    }
}
