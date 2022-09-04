package com.zakrzu.waypoints;

import org.bukkit.plugin.java.JavaPlugin;

public class WaypointsPlugin extends JavaPlugin {

    Database db;

    @Override
    public void onEnable() {
        db = new ConfigDatabase(this, this.getConfig());
        this.getCommand("waypoint").setExecutor(new WaypointCommand(this));
    }

    @Override
    public void onDisable() {
        
    }


}
