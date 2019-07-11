package me.tekkitcommando.promotionessentials.handler;

import me.tekkitcommando.promotionessentials.PromotionEssentials;

public class PermissionsHandler {

    private PromotionEssentials plugin;
    private String permissionSystem;

    public PermissionsHandler(PromotionEssentials plugin) {
        this.plugin = plugin;
        this.permissionSystem = plugin.getPermission().getName();
    }

    public String getPermissionSystem() {
        return permissionSystem;
    }
}
