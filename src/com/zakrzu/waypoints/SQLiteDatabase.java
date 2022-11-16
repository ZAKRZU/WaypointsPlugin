package com.zakrzu.waypoints;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

public class SQLiteDatabase implements Database {

    private String dbPath = "";

    public SQLiteDatabase(WaypointsPlugin instance) {
        this.dbPath = "jdbc:sqlite:"+instance.getDataFolder().toString()+"/database.db";

        try (Connection conn = this.connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("[Waypoint] Using database driver " + meta.getDriverName());

            String sql = "CREATE TABLE IF NOT EXISTS waypoints (\n"
                    + "     id integer PRIMARY KEY,\n"
                    + "     name text not null,\n"
                    + "     creator text not null,\n"
                    + "     location text not null\n"
                    + ");";
            try {
                Statement stmt = conn.createStatement();

                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.loadWaypoints();
    }

    private Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(this.dbPath);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                return conn;
            }
        }
        return null;
    }

    public Location convertFromString(String location) {
        Map<String, Object> newLocation = new HashMap<>();
        location = location.replace("{", "");
        location = location.replace("}", "");
        for (String vars : location.split(", ")) {
            String[] var = vars.split("=");
            if (var[0].equals("world")) {
                newLocation.put(var[0], var[1]);
            } else if(var[0].equals("x") || var[0].equals("y") || var[0].equals("z")) {
                newLocation.put(var[0], Double.parseDouble(var[1]));
            } else if(var[0].equals("pitch") || var[0].equals("yaw")) {
                newLocation.put(var[0], Float.parseFloat(var[1]));
            }
        }

        return Location.deserialize(newLocation);
    }

    @Override
    public void loadWaypoints() {
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) FROM waypoints");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Waypoint.count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public ArrayList<Waypoint> getWaypoints() {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM waypoints");
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) { 
                waypoints.add(new Waypoint(rs.getInt("id"), rs.getString("name"), rs.getString("creator"), this.convertFromString(rs.getString("location"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return waypoints;
    }

    @Override
    public ArrayList<Waypoint> getWaypoints(int pageNumber, int pageSize) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM waypoints LIMIT ? OFFSET ?");
            
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, pageNumber);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) { 
                waypoints.add(new Waypoint(rs.getInt("id"), rs.getString("name"), rs.getString("creator"), this.convertFromString(rs.getString("location"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return waypoints;
    }

    @Override
    public Waypoint getById(int id) {
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM waypoints WHERE id = ?");
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                return new Waypoint(rs.getInt("id"), rs.getString("name"), rs.getString("creator"), this.convertFromString(rs.getString("location")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Waypoint> getByName(String name) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM waypoints WHERE name = ?");
            
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) { 
                waypoints.add(new Waypoint(rs.getInt("id"), rs.getString("name"), rs.getString("creator"), this.convertFromString(rs.getString("location"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return waypoints;
    }

    @Override
    public boolean addWaypoint(Waypoint location) {
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO waypoints (name, creator, location) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCreator());
            pstmt.setString(3, location.getLocation().serialize().toString());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            while(rs.next()) {
                location.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        Waypoint.count++;
        return true;
    }

    @Override
    public boolean updateWaypoint(Waypoint location) {
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE waypoints SET name = ?, creator = ?, location = ? WHERE id = ?");

            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCreator());
            pstmt.setString(3, location.getLocation().serialize().toString());
            pstmt.setInt(4, location.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean removeWaypoint(int id) {
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM waypoints WHERE id = ?");

            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() < 1)
                return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        Waypoint.count--;
        return true;
    }

    @Override
    public ArrayList<Waypoint> getFiltred(String word, Boolean caseSensitive) {
        ArrayList<Waypoint> waypoints = new ArrayList<>();
        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM waypoints WHERE name LIKE ?");
            
            pstmt.setString(1, "%"+word+"%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) { 
                Waypoint waypoint = new Waypoint(rs.getInt("id"), rs.getString("name"), rs.getString("creator"), this.convertFromString(rs.getString("location")));
                if (caseSensitive) {
                    if (waypoint.getName().contains(word)) {
                        waypoints.add(waypoint);
                    }
                } else
                    waypoints.add(waypoint);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return waypoints;
    }
    
}
