//package me.tekkitcommando.pe.command;
//
//import me.tekkitcommando.pe.data.DataManager;
//import org.bukkit.ChatColor;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
//public class TokenCommand implements CommandExecutor {
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
//        if (cmd.getName().equalsIgnoreCase("token")) {
//            if (!(sender instanceof Player)) {
//                sender.sendMessage("You must be a player to redeem/create a token!");
//            } else {
//                Player player = (Player) sender;
//
//                if (!(DataManager.getConfig().getBoolean("token.enabled"))) {
//                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', DataManager.getMessages().getString("FunctionDisabled")));
//                    return true;
//                }
//
//                if (args.length == 1) {
//                    // Redeem token
//                    String token = args[0];
//
//                    if (player.hasPermission("pe.token.use") || player.hasPermission("pe.token.*")) {
//                        if (DataManager.getTokens().contains(token)) {
//
//                        }
//                    }
//                }
//            }
//        }
//        return true;
//    }
//}
