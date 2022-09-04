package com.zakrzu.waypoints;

import java.util.ArrayList;

public interface Database {
    public void loadWaypoints();
    public ArrayList<Waypoint> getWaypoints();
    public Waypoint getById(int id);
    public ArrayList<Waypoint> getByName(String name);
    public boolean addWaypoint(Waypoint location);
    public boolean removeWaypoint(int id);
    public ArrayList<Waypoint> getFiltred(String word);
}
