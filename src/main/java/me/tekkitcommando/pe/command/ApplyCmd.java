package me.tekkitcommando.pe.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import me.tekkitcommando.pe.data.DataManager;
import me.tekkitcommando.pe.promote.Promotion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command("apply")
public class ApplyCmd {

    @Default
    public static void apply(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("WrongPW")
                .concat("\nIt seems that the password argument is empty.")));
    }

    @Default
    public static void apply(Player player, @AStringArgument String password) {
        if (!(DataManager.getConfig().getBoolean("apply.enabled"))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("FunctionDisabled")));
            return;
        }

        if (player.hasPermission("pe.apply")) {
            if (DataManager.getConfig().getString("apply.password").equals(password)) {
                // Promote player
                new Promotion(player, DataManager.getConfig().getString("apply.promotion"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("UsedPW").replace("%group%", DataManager.getConfig().getString("apply.promotion"))));
            } else {
                if (DataManager.getConfig().getBoolean("apply.kickWrongPW")) {
                    player.kickPlayer(DataManager.getMessages().getString("WrongPW"));
                    return;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("WrongPW")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("NoPermissions")));
        }
    }
}
