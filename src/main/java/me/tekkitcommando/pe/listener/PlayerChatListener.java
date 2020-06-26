package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final PromotionEssentials plugin;

    public PlayerChatListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }
}
