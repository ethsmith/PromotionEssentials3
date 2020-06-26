package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.event.Listener;

public class PlayerLeaveListener implements Listener {

    private final PromotionEssentials plugin;

    public PlayerLeaveListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }
}
