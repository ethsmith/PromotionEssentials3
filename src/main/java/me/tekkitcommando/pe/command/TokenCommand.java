package me.tekkitcommando.pe.command;

import me.tekkitcommando.pe.PromotionEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TokenCommand implements CommandExecutor {

    private final PromotionEssentials plugin;

    public TokenCommand(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
