package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.event.Listener;

public class PlayerKillListener implements Listener {

    private final PromotionEssentials plugin;

    public PlayerKillListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }
}
