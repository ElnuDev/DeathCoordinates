package com.elnu;

import org.bukkit.Location;

import java.time.LocalDateTime;

public class DeathInfo {
    Location location;
    LocalDateTime time;

    public DeathInfo(Location location) {
        this.location = location;
        time = LocalDateTime.now();
    }

    public Location getLocation() {
        return location;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
