package me.tekkitcommando.promotionessentials.command;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.UUID;

public class TokenCommand implements CommandExecutor {

    private PromotionEssentials plugin;

    public TokenCommand(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("token")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to redeem/create a token!");
            } else {
                Player player = (Player) sender;

                if (args.length == 1) {
                    // Redeem token

                } else if (args.length > 1) {
                    String subcommand = args[0];

                    if (subcommand.equalsIgnoreCase("create")) {
                        String group = args[1];
                        String expiration = null;

                        if (args.length > 2) {
                            expiration = args[2];
                        }

                        // Create token
                        UUID token = UUID.randomUUID();
                        String tokenFormatted = token.toString().replace("-", "");

                        plugin.getTokens().set(tokenFormatted + ".group", group);

                        if (!(expiration == null)) {
                            DateTime dateTimeNow = getDateTime();

                            plugin.getTokens().set(tokenFormatted + ".creation", dateTimeNow.toString());
                            plugin.getTokens().set(tokenFormatted + ".expire", expiration);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "[PromotionEssentials] Invalid arguments!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[PromotionEssentials] Invalid arguments!");
                }
            }
        }
        return true;
    }

    private DateTime getDateTime() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        DateTime dateTimeNow = DateTime.now();
        String dateTimeString = dateTimeNow.toString(formatter);

        return formatter.parseDateTime(dateTimeString);
    }
}
