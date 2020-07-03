package me.tekkitcommando.pe.command;

import me.tekkitcommando.pe.PromotionEssentials;

public class CommandManager {

    private static final PromotionEssentials plugin = PromotionEssentials.getInstance();

    public static void setupCommands() {
        plugin.getCommand("apply").setExecutor(new ApplyCommand());
        plugin.getCommand("token").setExecutor(new TokenCommand());
        plugin.getCommand("rank").setExecutor(new RankCommand());
    }
}
