package com.zakrzu.waypoints.Command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class EditCommand extends BaseCommand {
    public EditCommand() {
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
        int id = -1;
        int x, y, z = 0;
        Waypoint wp = null;
        Waypoint modifiedWP = null; // we need copy so we can compare changes easily
        if (args.length < this.args.size()) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "You must provide waypoint ID and new position");
            sender.sendMessage(ChatColor.RED + "(type current to use your position)!");
            return false;
        }

        try {
          id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Id must be a number!");
            return false;
        }

        wp = WaypointsPlugin.getDatabase().getById(id);

        if (wp == null) {
            sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.RED + "Waypoint with id " + id + " does not exist!");
            return true;
        }

        // the fastest way to create object copy
        modifiedWP = new Waypoint(wp.getId(),
                                wp.getName(),
                                wp.getCreator(),
                                new Location(wp.getLocation().getWorld(),
                                    wp.getLocation().getX(), wp.getLocation().getY(), wp.getLocation().getZ()));

        // We are checking if player provided X coordinate
        try {
            x = Integer.parseInt(args[1]);
            modifiedWP.getLocation().setX(x);
        } catch (NumberFormatException e) { // if we catch this exeption it means player want to skip this coordinate
            if (args[1].contains("current")) { // or want to use his current position
                if (!(sender instanceof Player)) { // no for console users ;p
                    sender.sendMessage("current parameter can be used ingame only!");
                    return true;
                }
                modifiedWP.setLocation(((Player)sender).getLocation());
            }
        }

        if (args.length > this.getArgs().size()) { // checking for Y coordinate
            try {
                y = Integer.parseInt(args[2]);
                modifiedWP.getLocation().setY(y);
            } catch (NumberFormatException e) { // if catched it means player want to skip this coordinate
            }
        }

        if (args.length > this.getOptionalArgs().size() - 1) { // checking for Z coordinate
            try {
                z = Integer.parseInt(args[3]);
                modifiedWP.getLocation().setZ(z);
            } catch (NumberFormatException e) {
            }
        }

        sender.sendMessage(WaypointsPlugin.PREFIX + ChatColor.DARK_GREEN + "Waypoint " + modifiedWP.getId() + " has been modified");
        // Comparing changes
        String edited = "";
        if (modifiedWP.getLocation().getX() != wp.getLocation().getX())
            edited += "X:" + String.format("%.03f", modifiedWP.getLocation().getX())+" ";
        if (modifiedWP.getLocation().getY() != wp.getLocation().getY())
            edited += "Y:" + String.format("%.03f", modifiedWP.getLocation().getY())+" ";
        if (modifiedWP.getLocation().getZ() != wp.getLocation().getZ())
            edited += "Z:" + String.format("%.03f", modifiedWP.getLocation().getZ());
        sender.sendMessage(edited);

        // Put modified Waypoint in the database
        WaypointsPlugin.getDatabase().updateWaypoint(modifiedWP);
        
        return true;
    }
}
