package com.zakrzu.waypoints.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdEdit extends WaypointCmdBase {
    public WaypointCmdEdit() {
        super();
        this.setCmd("edit");
        this.setDescription("edits waypoint details");
        this.addArg("id");
        this.addArg("x | current");
        this.addOptionalArg("y");
        this.addOptionalArg("z");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < this.args.size()) {
            sender.sendMessage("No ID has been specified and one coordiante!");
            return false;
        }

        int id = -1;
        int x, y, z = 0;
        boolean xb, yb, zb;
        xb = yb = zb = false;

        // Select waypoint to modify
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Id must be a number!");
            return false;
        }
        Waypoint wp  = WaypointsPlugin.getDatabase().getById(id);

        if (wp != null) {
            // We are checking if X has been provided
            // in case of current, player current position will be used
            // Also if player provide String (other than current) or Char 
            // we will skip this coordinate
            // So we are able to change only y or z coordinate
            try {
                x = Integer.parseInt(args[1]);
                wp.getLocation().setX(x);
                xb = true;
            } catch (NumberFormatException e) {
                if (args[1].equals("current")) {
                    if (sender instanceof Player) {
                        Player pl = (Player) sender;
                        wp.setLocation(pl.getLocation());
                        xb = true;
                        yb = true;
                        zb = true;
                    } else {
                        sender.sendMessage("current parameter can be used ingame only!");
                    }
                }
            }
            // We are checking if Y coordinate has been provided
            // If not we just ignore that change
            // Also can be skippable
            try {
                y = Integer.parseInt(args[2]);
                wp.getLocation().setY(y);
                yb = true;
            } catch (NumberFormatException e) {
            } catch (ArrayIndexOutOfBoundsException e1) {
            }
            // We are checking if Z coordinate has been provided
            // If not we just ignore that change
            try {
                z = Integer.parseInt(args[3]);
                wp.getLocation().setZ(z);
                zb = true;
            } catch (NumberFormatException e) {
            }catch (ArrayIndexOutOfBoundsException e1) {
            }

            // If player made some changes we update Waypoint
            if (xb || yb || zb) {
                WaypointsPlugin.getDatabase().updateWaypoint(wp);
                sender.sendMessage("Waypoint " + wp.getId() + " has been modified");
                String edited = "";
                if (xb)
                    edited += "X:" + String.format("%.03f", wp.getLocation().getX())+" ";
                if (yb)
                    edited += "Y:" + String.format("%.03f", wp.getLocation().getY())+" ";
                if (zb)
                    edited += "Z:" + String.format("%.03f", wp.getLocation().getZ());
                sender.sendMessage(edited);
            } else {
                sender.sendMessage("No changes has been applied to waypoint " + wp.getId());
            }
        } else {
            sender.sendMessage("Waypoint " + id + " does not exist!");
        }
        return true;
    }
}
