package com.zakrzu.waypoints;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDatabase implements Database {

    private WaypointsPlugin pluginInstance;
    private ArrayList<Waypoint> waypoints;
    private FileConfiguration config;

    public ConfigDatabase(WaypointsPlugin instance, FileConfiguration config) {
        this.waypoints = new ArrayList<>();
        this.pluginInstance = instance;
        this.config = config;
        this.loadWaypoints();
        instance.saveConfig();
    }

    @Override
    public void loadWaypoints() {
        Set<String> locations = null;
        try {
            locations = this.config.getConfigurationSection("WAYPOINTS").getKeys(false);
        } catch (NullPointerException e) {
            return;
        }
        for (String str_id : locations) {
            int id = Integer.parseInt(str_id);
            String name = this.config.getString("WAYPOINTS."+id+".NAME");
            String creator = this.config.getString("WAYPOINTS."+id+".CREATOR");
            Location location = this.config.getLocation("WAYPOINTS."+id+".LOCATION");
            Waypoint waypoint = new Waypoint(id, name, creator, location);
            waypoints.add(waypoint);
        }

        Waypoint.ids = this.config.getInt("counter");
    }

    @Override
    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    @Override
    public boolean addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
        this.config.set("counter", waypoint.getId()+1);
        this.config.set("WAYPOINTS."+waypoint.getId(), "");
        this.config.set("WAYPOINTS."+waypoint.getId()+".NAME", waypoint.getName());
        this.config.set("WAYPOINTS."+waypoint.getId()+".CREATOR", waypoint.getCreator());
        this.config.set("WAYPOINTS."+waypoint.getId()+".LOCATION", waypoint.getLocation());
        pluginInstance.saveConfig();
        return true;
    }

    @Override
    public boolean removeWaypoint(int id) {
        Waypoint waypoint = this.getById(id);
        if(waypoints.remove(waypoint)) {
            this.config.set("WAYPOINTS."+waypoint.getId(), null);
            pluginInstance.saveConfig();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Waypoint getById(int id) {
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getId() == id)
                return waypoint;
        }
        return null;
        // if (this.locations.size() < id+1 || id < 0)
        //     return null;
        // return this.locations.get(id);
    }

    @Override
    public ArrayList<Waypoint> getByName(String name) {
        ArrayList<Waypoint> filteredWaypoints = new ArrayList<>();
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getName().equals(name))
                filteredWaypoints.add(waypoint);
        }
        return filteredWaypoints;
    }

    public ArrayList<Waypoint> getFiltred(String name) {
        ArrayList<Waypoint> filteredWaypoints = new ArrayList<>();
        for (Waypoint waypoint : waypoints) {
            if (waypoint.getName().contains(name))
                filteredWaypoints.add(waypoint);
        }
        return filteredWaypoints;
    }
    
}
