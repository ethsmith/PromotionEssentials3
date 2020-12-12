package me.tekkitcommando.pe;

import dev.jorel.commandapi.CommandAPI;
import me.tekkitcommando.pe.command.ApplyCmd;
import me.tekkitcommando.pe.command.RankCmd;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.economy.EconomyManager;
import me.tekkitcommando.pe.listener.PlayerInteractListener;
import me.tekkitcommando.pe.listener.PlayerJoinListener;
import me.tekkitcommando.pe.listener.SignChangeListener;
import me.tekkitcommando.pe.permission.PermissionManager;
import me.tekkitcommando.pe.promote.PromotionManager;
import me.tekkitcommando.pe.time.TimeManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class PromotionEssentials extends JavaPlugin {

    private static PromotionEssentials instance;
    private Logger logger;
    private Metrics metrics;

    public static PromotionEssentials getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(TimeManager.getTimerId());

        for (UUID uuid : TimeManager.getPlayTimes().keySet()) {
            DataManager.getTimes().set(uuid.toString() + ".playTime", TimeManager.getPlayTimes().get(uuid));
        }

        logger.info("Disabled!");
    }

    @Override
    public void onEnable() {
        instance = this;
        this.logger = getLogger();

        DataManager.setupConfigFiles();

        if (!(EconomyManager.isEconomyRegistered())) {
            logger.warning("You must have vault and an economy plugin installed for this to work!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (!(PermissionManager.isPermissionsRegistered())) {
            logger.warning("You must have vault and a permissions plugin installed for this to work!");
            getServer().getPluginManager().disablePlugin(this);
        }

        if (PermissionManager.getPermissions() == null) {
            logger.warning("No permissions system found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            logger.info("Using " + PermissionManager.getPermissions().getName() + " for promotions.");
        }

        if (DataManager.getConfig().getBoolean("time.enabled")) {
            TimeManager.setCountOffline(DataManager.getConfig().getBoolean("time.countOffline"));
            PromotionManager.setBlacklistedRanks(DataManager.getConfig().getStringList("time.blacklistRanks"));

            Map<String, String> times = (Map<String, String>) DataManager.getConfig().getMap("time.groups");
            if (times != null) {
                for (String rank : times.keySet()) {
                    String time = times.get(rank);
                    PromotionManager.getTimedRanks().put(rank, Long.parseLong(time));
                }
            }

            for (String uuid : DataManager.getTimes().singleLayerKeySet()) {
                TimeManager.getPlayTimes().put(UUID.fromString(uuid), DataManager.getTimes().getLong(uuid + ".playTime"));
            }

            TimeManager.startTimePromote();
        }

        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        if (DataManager.getConfig().getBoolean("metrics.enabled")) {
            metrics = new Metrics(this, 1184);
        }

        logger.info("Enabled!");
    }

    @Override
    public void onLoad() {
        CommandAPI.registerCommand(ApplyCmd.class);
        CommandAPI.registerCommand(RankCmd.class);
    }
}
