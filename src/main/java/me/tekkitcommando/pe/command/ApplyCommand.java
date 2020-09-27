package me.tekkitcommando.pe.command;

import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.promote.Promotion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ApplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("apply")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You must be a player to use that command!");
            } else {
                Player player = (Player) sender;

                if (!(DataManager.getConfig().getBoolean("apply.enabled"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("FunctionDisabled")));
                    return true;
                }

                if (player.hasPermission("pe.apply")) {
                    if (args.length < 1) {
                        player.sendMessage(ChatColor.RED + "[PromotionEssentials] You need to enter a password!");
                    } else {
                        String password = args[0];

                        if (DataManager.getConfig().getString("apply.password").equals(password)) {
                            // Promote player
                            new Promotion(player, DataManager.getConfig().getString("apply.promotion"));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("UsedPW").replace("%group%", DataManager.getConfig().getString("apply.promotion"))));
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("WrongPW")));

                            if (DataManager.getConfig().getBoolean("apply.kickWrongPW")) {
                                player.kickPlayer(DataManager.getMessages().getString("WrongPW"));
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
                }
            }
        }
        return true;
    }
}
