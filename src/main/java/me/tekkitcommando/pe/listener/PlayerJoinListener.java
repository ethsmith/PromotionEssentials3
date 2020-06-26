package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {

    private final PromotionEssentials plugin;

    public PlayerJoinListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }
}
