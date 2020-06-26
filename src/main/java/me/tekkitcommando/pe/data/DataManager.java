package me.tekkitcommando.pe.data;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import lombok.Getter;
import me.tekkitcommando.pe.PromotionEssentials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    @Getter
    private static final Yaml config = new Yaml("config", plugin.getDataFolder().getAbsolutePath());
    @Getter
    private static final Yaml messages = new Yaml("messages", plugin.getDataFolder().getAbsolutePath());
    @Getter
    private static final Json tokens = new Json("token", plugin.getDataFolder().getAbsolutePath());
    @Getter
    private static final Json times = new Json("times", plugin.getDataFolder().getAbsolutePath());
    @Getter
    private static final Json kills = new Json("kills", plugin.getDataFolder().getAbsolutePath());

    public static void setupConfigFiles() {
        Map<String, String> timedRanks = new HashMap<>();
        Map<String, Double> purchasedRanks = new HashMap<>();
        List<String> noPromote = new ArrayList<>();
        List<String> consoleCommands = new ArrayList<>();
        List<String> playerCommands = new ArrayList<>();

        timedRanks.put("member", "00h15m30s");
        timedRanks.put("elite", "24h00m00s");
        timedRanks.put("legend", "48h30m15s");

        purchasedRanks.put("member", 1000.00);
        purchasedRanks.put("elite", 10000.00);
        purchasedRanks.put("legend", 100000.00);

        noPromote.add("Admin");
        noPromote.add("Mod");
        noPromote.add("Helper");
        noPromote.add("Owner");

        consoleCommands.add("eco give %player% 1000");
        playerCommands.add("me got promoted to %group%");

        config.setDefault("metrics.enabled", true);

        config.setDefault("token.enabled", true);

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
        config.setDefault("time.noPromote", noPromote);
        config.setDefault("time.countOffline", false);

        config.setDefault("buy.enabled", true);
        config.setDefault("buy.rankListHeader", "&aPurchasable Ranks");
        config.setDefault("buy.rankListColor", "RED");
        config.setDefault("buy.groups", purchasedRanks);

        config.setDefault("kill.enabled", false);
        config.setDefault("kill.countFriendlyMobs", false);
        config.setDefault("kill.member.players", 10);
        config.setDefault("kill.member.mobs", 10);
        config.setDefault("kill.elite.players", 100);
        config.setDefault("kill.elite.mobs", 100);
        config.setDefault("kill.legend.players", 1000);
        config.setDefault("kill.legend.mobs", 1000);

        config.setDefault("commands.member.console", consoleCommands);
        config.setDefault("commands.member.player", playerCommands);

        messages.setDefault("NoPermissions", "&c[PromotionEssentials] You do not have permission to do this!");
        messages.setDefault("CreatedSign", "&a[PromotionEssentials] Successfully created a promotion sign!");
        messages.setDefault("UsedSign", "&a[PromotionEssentials] Successfully promoted to %group%!");
        messages.setDefault("UsedPW", "&a[PromotionEssentials] You have been successfully promoted to %group%!");
        messages.setDefault("WrongPW", "&c[PromotionEssentials] Wrong PW!");
        messages.setDefault("TokenUse", "&a[PromotionEssentials] You have been successfully promoted to %group%!");
        messages.setDefault("CreateToken", "&a[PromotionEssentials] Created token %token% for %group%!");
        messages.setDefault("TokenExpired", "&c[PromotionEssentials] That token has expired!");
        messages.setDefault("TokenDoesntExist", "&c[PromotionEssentials] That token doesn't exist!");
        messages.setDefault("Join", "&a[PromotionEssentials] %player%, &aplease write /apply [Password] to get Permissions to build!");
        messages.setDefault("Mute", "&c[PromotionEssentials] You are not allowed to chat!");
        messages.setDefault("FunctionDisabled", "&c[PromotionEssentials] This function has been disabled by the server administrator!");
        messages.setDefault("CantBuyRank", "&c[PromotionEssentials] You can not buy this rank! Either it doesn't exist or you already own it.");
        messages.setDefault("NoMoney", "&c[PromotionEssentials] You do not have enough money to buy this rank!");
        messages.setDefault("BoughtRank", "&a[PromotionEssentials] Bought rank %group%!");
        messages.setDefault("PromotedAfterTime", "&a[PromotionEssentials] You have been promoted to %group%!");
        messages.setDefault("InvalidArgs", "&c[PromotionEssentials] Invalid arguments!");
        messages.setDefault("PromotedAfterKills", "&a[PromotionEssentials] You have been promoted to %group% for your kills!");
    }
}
