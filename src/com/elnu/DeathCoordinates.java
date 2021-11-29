package com.elnu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DeathCoordinates extends JavaPlugin {
    DeathListener deathListener;

    @Override
    public void onEnable() {
        deathListener = new DeathListener();
        Bukkit.getPluginManager().registerEvents(deathListener, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("announcedeath")) {
            var senderName = sender.getName();
            try {
                var playerDeathInfoMap = deathListener.getPlayerDeathInfoMap();
                var deathInfo = playerDeathInfoMap.get(senderName);
                var location = deathInfo.getLocation();
                var time = deathInfo.getTime();
                var now = LocalDateTime.now();
                var minutesSinceDeath = time.until(now, ChronoUnit.MINUTES);
                var secondsSinceDeath = time.until(now, ChronoUnit.SECONDS);
                playerDeathInfoMap.remove(senderName);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + String.format(
                        "%s is announcing that they died at (%d, %d, %d) in %s, %d minutes and %d seconds ago.",
                        senderName,
                        location.getBlockX(),
                        location.getBlockY(),
                        location.getBlockZ(),
                        location.getWorld().getEnvironment(),
                        minutesSinceDeath,
                        secondsSinceDeath - minutesSinceDeath * 60
                ));
            } catch(NullPointerException e) {
                sender.sendMessage(ChatColor.YELLOW + "No death coordinates to announce.");
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("announcelocation")) {
            var player = (Player) sender;
            var location = player.getLocation();
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + String.format(
                    "%s is announcing that they are at (%d, %d, %d) in %s.",
                    sender.getName(),
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ(),
                    location.getWorld().getEnvironment()
            ));
            return true;
        }
        return false;
    }
}
