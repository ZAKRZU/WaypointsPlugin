package com.zakrzu.waypoints.Command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zakrzu.waypoints.Waypoint;
import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointCmdAdd extends WaypointCmdBase {

    public WaypointCmdAdd() {
        super();
        this.setCmd("add");
        this.setDescription("adds position to the list");
        this.addArg("name");
        this.addOptionalArg("x");
        this.addOptionalArg("y");
        this.addOptionalArg("z");
    }

    @Override
    public boolean execute(Player sender, String[] args) {
        String name = args[0];
        Location loc = sender.getLocation();
        // Creating location from your own coordinates
        if (args.length > 1 && args.length <= this.argsOptional.size() + this.args.size()) {
            if (args.length < this.argsOptional.size() + this.args.size()) {
                sender.sendMessage("You need to specify <x> <y> and <z> coordinates!");
                return false;
            }
            double x, y, z = 0.0f;
            try {
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Coordinate must be a number");
                return false;
            }
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
        }

        Waypoint newWaypoint = new Waypoint(name, sender, loc);
        WaypointsPlugin.getDatabase().addWaypoint(newWaypoint);
        sender.sendMessage("Waypoint \"" + name + "\" added with id: " + newWaypoint.getId());
        sender.sendMessage(newWaypoint.getCoordinates());
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            return this.execute(player, args);
        } else {
            sender.sendMessage("This command is for use ingame only!");
        }
        return true;
    }

}
