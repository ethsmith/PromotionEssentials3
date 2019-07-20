package me.tekkitcommando.promotionessentials.listener;

import me.tekkitcommando.promotionessentials.PromotionEssentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerKillListener implements Listener {

    private PromotionEssentials plugin;

    public PlayerKillListener(PromotionEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            Entity entity = event.getEntity();

            if (plugin.getPluginConfig().getBoolean("kill.enabled")) {
                if (player.hasPermission("pe.kill.promote")) {
                    if (entity instanceof Animals) {
                        if (!plugin.getPluginConfig().getBoolean("kill.countFriendlyMobs")) {
                            return;
                        }
                    }

                    if (plugin.getKills().contains(player.getUniqueId().toString())) {
                        if (entity instanceof Player) {
                            int playerKills = plugin.getKills().getInt(player.getUniqueId().toString() + ".playerKills");
                            plugin.getKills().set(player.getUniqueId().toString() + ".players", playerKills + 1);
                        } else if (entity instanceof Monster) {
                            int mobKills = plugin.getKills().getInt(player.getUniqueId().toString() + ".mobKills");
                            plugin.getKills().set(player.getUniqueId().toString() + ".mobs", mobKills + 1);
                        }
                    } else {
                        if (entity instanceof Player) {
                            plugin.getKills().set(player.getUniqueId().toString() + ".players", 1);
                        } else if (entity instanceof Monster) {
                            plugin.getKills().set(player.getUniqueId().toString() + ".mobs", 1);
                        }
                    }

                    checkForKillRankup(player);
                }
            }
        }
    }

    private void checkForKillRankup(Player player) {
        String highestRankEarned = null;

        for (String group : plugin.getPermission().getGroups()) {
            if (plugin.getPluginConfig().contains("kill." + group)) {
                int playerKillsNeeded = plugin.getPluginConfig().getInt("kill." + group + ".players");
                int mobKillsNeeded = plugin.getPluginConfig().getInt("kill." + group + ".mobs");
                int playerKills = plugin.getKills().getInt(player.getUniqueId().toString() + ".players");
                int mobKills = plugin.getKills().getInt(player.getUniqueId().toString() + ".mobs");

                if (playerKills >= playerKillsNeeded && mobKills >= mobKillsNeeded) {
                    highestRankEarned = group;
                }
            }
        }

        if (highestRankEarned != null) {
            if (!plugin.getPermission().getPrimaryGroup(player).equalsIgnoreCase(highestRankEarned)) {
                plugin.getPermission().playerRemoveGroup(player, plugin.getPermission().getPrimaryGroup(player));
                plugin.getPermission().playerAddGroup(player, highestRankEarned);

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("PromotedAfterKills").replace("%group%", highestRankEarned)));
            }
        }
    }
}
