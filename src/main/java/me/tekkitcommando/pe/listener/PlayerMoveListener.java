package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.event.Listener;

public class PlayerMoveListener implements Listener {

    private final PromotionEssentials plugin;

    public PlayerMoveListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }
}
