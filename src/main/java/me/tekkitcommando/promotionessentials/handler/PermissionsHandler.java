package me.tekkitcommando.promotionessentials.handler;

import me.tekkitcommando.promotionessentials.PromotionEssentials;

public class PermissionsHandler {

    private PromotionEssentials plugin;

    public PermissionsHandler(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    public String getPermissionSystem() {
        return plugin.getPermission().getName();
    }
}
