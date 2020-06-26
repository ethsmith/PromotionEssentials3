package me.tekkitcommando.pe;

import me.tekkitcommando.pe.command.CommandManager;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.economy.EconomyManager;
import me.tekkitcommando.pe.listener.ListenerManager;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.time.TimeManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PromotionEssentials extends JavaPlugin {

    private static final PromotionEssentials instance = (PromotionEssentials) Bukkit.getPluginManager().getPlugin("PromotionEssentials");
    private Logger logger;

    public static PromotionEssentials getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        logger.info("Disabled!");
    }

    @Override
    public void onEnable() {
        this.logger = getLogger();

        if (!(EconomyManager.isEconomyRegistered())) {
            logger.warning("You must have vault and an economy plugin installed for this to work!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (!(PermissionManager.isPermissionsRegistered())) {
            logger.warning("You must have vault and a permissions plugin installed for this to work!");
            getServer().getPluginManager().disablePlugin(this);
        }

        DataManager.setupConfigFiles();
        CommandManager.setupCommands();
        ListenerManager.setupListeners();

        if (PermissionManager.getPermissions() == null) {
            logger.warning("No permissions system found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            logger.info("Using " + PermissionManager.getPermissions().getName() + " for promotions.");
        }

        if (DataManager.getConfig().getBoolean("time.enabled")) {
            TimeManager.startTimePromote();
        }

        if (DataManager.getConfig().getBoolean("metrics.enabled")) {
            new Metrics(this);
        }

        logger.info("Enabled!");
    }
}
