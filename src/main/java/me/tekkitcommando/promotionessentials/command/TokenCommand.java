package me.tekkitcommando.promotionessentials.command;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
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

                if (!(plugin.getPluginConfig().getBoolean("token.enabled"))) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("FunctionDisabled")));
                    return true;
                }

                if (args.length == 1) {
                    // Redeem token
                    String token = args[0];

                    if (player.hasPermission("pe.token.use") || player.hasPermission("pe.token.*")) {
                        if (plugin.getTokens().contains(token)) {
                            if (plugin.getTokens().contains(token + ".expire")) {
                                // Check if expired
                                DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
                                DateTime dateTimeExpired = formatter.parseDateTime(plugin.getTokens().getString(token + ".expire"));

                                if (dateTimeExpired.isBeforeNow()) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("TokenExpired")));
                                    plugin.getTokens().removeKey(token);
                                } else {
                                    plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
                                    plugin.getPermission().playerAddGroup(player, plugin.getTokens().getString(token + ".group"));
                                    plugin.getTokens().removeKey(token);
                                }
                            } else {
                                plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
                                plugin.getPermission().playerAddGroup(player, plugin.getTokens().getString(token + ".group"));
                                plugin.getTokens().removeKey(token);
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("TokenDoesntExist")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                    }
                } else if (args.length > 1) {
                    String subcommand = args[0];

                    if (subcommand.equalsIgnoreCase("create")) {
                        if (player.hasPermission("pe.token.create") || player.hasPermission("pe.token.*")) {
                            String group = args[1];
                            String expiration = null;

                            if (args.length > 2) {
                                expiration = args[2].toLowerCase();
                            }

                            // Create token
                            UUID token = UUID.randomUUID();
                            String tokenFormatted = token.toString().replace("-", "");

                            plugin.getTokens().set(tokenFormatted + ".group", group);

                            if (!(expiration == null)) {
                                DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
                                DateTime dateTimeExpired = getDateTimeExpired(formatter, expiration);

                                if (dateTimeExpired != null) {
                                    plugin.getTokens().set(tokenFormatted + ".expire", dateTimeExpired.toString());
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("NoPermissions")));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("InvalidArgs")));
                }
            }
        }
        return true;
    }

    private DateTime getDateTime(DateTimeFormatter formatter) {
        DateTime dateTimeNow = DateTime.now();
        String dateTimeString = dateTimeNow.toString(formatter);

        return formatter.parseDateTime(dateTimeString);
    }

    private DateTime getDateTimeExpired(DateTimeFormatter formatter, String expiration) {
        int hours;
        int minutes;
        int seconds;

        try {
            hours = Integer.parseInt(expiration.substring(0, 2));
            minutes = Integer.parseInt(expiration.substring(3, 5));
            seconds = Integer.parseInt(expiration.substring(6, 8));
        } catch (NumberFormatException e) {
            return null;
        }

        DateTime dateTimeNow = getDateTime(formatter);
        DateTime dateTimeExpired = dateTimeNow.plus(Hours.hours(hours)).plus(Minutes.minutes(minutes)).plus(Seconds.seconds(seconds));
        String dateTimeExpiredStr = dateTimeExpired.toString(formatter);

        return formatter.parseDateTime(dateTimeExpiredStr);
    }
}
