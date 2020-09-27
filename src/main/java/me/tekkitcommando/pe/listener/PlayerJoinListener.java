package me.tekkitcommando.pe.listener;

import me.tekkitcommando.pe.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (DataManager.getConfig().getBoolean("time.enabled")) {
            if (!DataManager.getTimes().contains(player.getUniqueId().toString() + ".firstJoin"))
                DataManager.getTimes().set(player.getUniqueId().toString() + ".firstJoin", LocalDateTime.now().toString());
        }
    }
}
