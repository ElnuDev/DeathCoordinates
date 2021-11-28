package com.elnu;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class DeathListener implements Listener {
    HashMap<String, DeathInfo> playerDeathInfoMap;

    public DeathListener() {
        playerDeathInfoMap = new HashMap();
    }

    public HashMap<String, DeathInfo> getPlayerDeathInfoMap() {
        return playerDeathInfoMap;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        var location = event.getEntity().getLocation();
        var player = event.getEntity();
        player.sendMessage(ChatColor.RED + String.format(
                "Oof! You died at (%d, %d, %d) in %s.",
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld().getEnvironment()
        ));
        playerDeathInfoMap.put(player.getName(), new DeathInfo(location));
    }
}
