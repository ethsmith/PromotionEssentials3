package me.tekkitcommando.pe.promote;

import lombok.Getter;
import lombok.Setter;
import me.tekkitcommando.pe.PromotionEssentials;
import me.tekkitcommando.pe.permission.PermissionManager;
import org.bukkit.entity.Player;

import java.util.*;

public class PromotionManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    @Setter
    private static List<String> blacklistedRanks = new ArrayList<>();
    @Getter
    @Setter
    private static Map<String, Long> timedRanks = new HashMap<>();

    public static String calculatePromotion(UUID uuid, long playTimeSec) {
        Player player = plugin.getServer().getPlayer(uuid);
        String rankEarned = null;

        if (blacklistedRanks.contains(PermissionManager.getPermissions().getPrimaryGroup(player))) {
            return null;
        }

        for (String rank : timedRanks.keySet()) {
            if (timedRanks.get(rank) <= playTimeSec) {
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
