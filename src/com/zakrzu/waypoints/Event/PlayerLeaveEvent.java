package com.zakrzu.waypoints.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.zakrzu.waypoints.WaypointsPlugin;

public class PlayerLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        WaypointsPlugin.getTracker().removeTracker(event.getPlayer());
    }
}
