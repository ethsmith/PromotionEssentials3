package me.tekkitcommando.pe.promote;

import me.tekkitcommando.pe.permission.PermissionManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class Promotion {

    public Promotion(Player player, String rank) {

        Permission permissions = PermissionManager.getPermissions();
        permissions.playerRemoveGroup(player, permissions.getPrimaryGroup(player));
        permissions.playerAddGroup(null, player, rank);
    }
}
