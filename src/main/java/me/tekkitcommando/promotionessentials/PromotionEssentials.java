package me.tekkitcommando.promotionessentials;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import me.tekkitcommando.promotionessentials.command.ApplyCommand;
import me.tekkitcommando.promotionessentials.handler.PermissionsHandler;
import me.tekkitcommando.promotionessentials.listener.PlayerChatListener;
import me.tekkitcommando.promotionessentials.listener.PlayerJoinListener;
import me.tekkitcommando.promotionessentials.listener.PlayerMoveListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PromotionEssentials extends JavaPlugin {

    private Logger logger;
    private Yaml config = new Yaml("config", getDataFolder().getAbsolutePath());
    private Yaml messages = new Yaml("messages", getDataFolder().getAbsolutePath());
    private Json tokens = new Json("token", getDataFolder().getAbsolutePath());
    private Json times = new Json("times", getDataFolder().getAbsolutePath());
    private Map<Player, String> confirmations = new HashMap<>();
    private PermissionsHandler permissionsHandler = new PermissionsHandler(this);

    // Vault
    private Economy economy = null;
    private Permission permission = null;

    @Override
    public void onDisable() {
        logger.info("[PromotionEssentials] Disabled!");
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();

        if (!(registerEconomy())) {
            logger.warning("[PromotionEssentials] You must have vault installed for this plugin!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (!(registerPermission())) {
            logger.warning("[PromotionEssentials] You must have vault installed for this plugin!");
            getServer().getPluginManager().disablePlugin(this);
        }

        setupConfigFiles();
        setupCommands();
        setupListeners();

        if (permissionsHandler.getPermissionSystem() == null) {
            logger.warning("[PromotionEssentials] No permissions system found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            logger.info("[PromotionEssentials] Using " + permissionsHandler.getPermissionSystem() + " for promotions.");
        }

        logger.info("[PromotionEssentials] Enabled!");
    }

    public Yaml getPluginConfig() {
        return config;
    }

    public Yaml getMessages() {
        return messages;
    }

    public Json getTokens() {
        return tokens;
    }

    public Json getTimes() {
        return times;
    }

    public Map<Player, String> getConfirmations() {
        return confirmations;
    }

    public Permission getPermission() {
        return permission;
    }

    public Economy getEconomy() {
        return economy;
    }

    private boolean registerEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return economy != null;
    }

    private boolean registerPermission() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);

        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }

        return permission != null;
    }

    private void setupCommands() {
        getCommand("apply").setExecutor(new ApplyCommand(this));
    }

    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
    }

    private void setupConfigFiles() {
        Map<String, String> timedRanks = new HashMap<>();
        Map<String, Double> purchasedRanks = new HashMap<>();

        timedRanks.put("member", "15m30s");
        timedRanks.put("elite", "24h");
        timedRanks.put("legend", "48h30m15s");

        purchasedRanks.put("member", 1000.00);
        purchasedRanks.put("elite", 10000.00);
        purchasedRanks.put("legend", 100000.00);

        config.setDefault("apply.enabled", false);
        config.setDefault("apply.password", "changeme");
        config.setDefault("apply.default", "default");
        config.setDefault("apply.promotion", "member");
        config.setDefault("apply.freeze", false);
        config.setDefault("apply.mute", false);
        config.setDefault("apply.kickWrongPW", false);
        config.setDefault("apply.blockViewChat", false);

        config.setDefault("time.enabled", false);
        config.setDefault("time.groups", timedRanks);
        config.setDefault("time.countOffline", false);

        config.setDefault("buy.enabled", true);
        config.setDefault("buy.groups", purchasedRanks);
        config.setDefault("buy.hierarchy", false);

        messages.setDefault("NoPermissions", "&cYou do not have permission to do this!");
        messages.setDefault("CreatedSign", "&a[PromotionEssentials] Successfully created a promotion sign!");
        messages.setDefault("UsedSign", "&a[PromotionEssentials] Successfully promoted to %group%!");
        messages.setDefault("UsedPW", "&a[PromotionEssentials] You have been successfully promoted to %group%!");
        messages.setDefault("WrongPW", "&cWrong PW!");
        messages.setDefault("TokenUse", "&a[PromotionEssentials] You have been successfully promoted to %group%!");
        messages.setDefault("CreateToken", "&a[PromotionEssentials] Created token %token% for %group%!");
        messages.setDefault("Join", "&5<player>, &aplease write /apply [Password] to get Permissions to build!");
        messages.setDefault("Mute", "&cYou are not allowed to chat!");
        messages.setDefault("FunctionDisabled", "&cThis function has been disabled by the server administrator!");
        messages.setDefault("BuyRank", "&5Do you really want to buy %group% for %price%?");
        messages.setDefault("CantBuyRank", "&cYou can not buy this rank!");
        messages.setDefault("NoMoney", "&cYou do not have enough money to buy this rank!");
        messages.setDefault("BoughtRank", "&aBought rank %group%!");
        messages.setDefault("Confirm", "&5Type /peconfirm to continue");
        messages.setDefault("PromotedAfterTime", "&aYou have been promoted to %group%!");
    }
}
