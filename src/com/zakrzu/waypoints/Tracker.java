package com.zakrzu.waypoints;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Tracker {
    private HashMap<Player, Waypoint> trackers;

    public Tracker() {
        this.trackers = new HashMap<>();
    }

    public int size() {
        return this.trackers.size();
    }

    public void addTracker(Player player, Waypoint wp) {
        this.trackers.put(player, wp);
    }

    public Waypoint getTracker(Player player) {
        return this.trackers.get(player);
    }

    public void removeTracker(Player player) {
        this.trackers.remove(player);
    }
}
