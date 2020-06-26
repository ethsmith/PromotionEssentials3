package me.tekkitcommando.pe.economy;

import lombok.Getter;
import me.tekkitcommando.pe.PromotionEssentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    private static Economy economy = null;

    public static boolean isEconomyRegistered() {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return economy != null;
    }

}
