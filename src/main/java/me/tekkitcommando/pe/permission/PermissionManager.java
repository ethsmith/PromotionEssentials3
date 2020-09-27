package me.tekkitcommando.pe.permission;

import lombok.Getter;
import me.tekkitcommando.pe.PromotionEssentials;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermissionManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    private static Permission permissions = null;

    public static boolean isPermissionsRegistered() {
        RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);

        if (permissionProvider != null) {
            permissions = permissionProvider.getProvider();
        }

        return permissions != null;
    }
}
