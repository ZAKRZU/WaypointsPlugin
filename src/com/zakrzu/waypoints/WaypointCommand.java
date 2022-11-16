package com.zakrzu.waypoints;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.zakrzu.waypoints.Command.AddCommand;
import com.zakrzu.waypoints.Command.BaseCommand;
import com.zakrzu.waypoints.Command.ConvertCommand;
import com.zakrzu.waypoints.Command.DeleteCommand;
import com.zakrzu.waypoints.Command.EditCommand;
import com.zakrzu.waypoints.Command.FilterCommand;
import com.zakrzu.waypoints.Command.GetCommand;
import com.zakrzu.waypoints.Command.ListCommand;
import com.zakrzu.waypoints.Command.TrackCommand;
import com.zakrzu.waypoints.Command.VersionCommand;
import com.zakrzu.waypoints.Command.WaypointTabCompleter;

public class WaypointCommand implements CommandExecutor {

    private ArrayList<BaseCommand> cmds;
    private WaypointTabCompleter tabCompleter;
    private PluginDescriptionFile pdf;

    public WaypointCommand(WaypointsPlugin wpl) {
        this.cmds = new ArrayList<>();
        this.pdf = wpl.getDescription();
        this.tabCompleter = new WaypointTabCompleter(cmds);
        this.cmds.add(new AddCommand());
        this.cmds.add(new VersionCommand(this.pdf));
        this.cmds.add(new ListCommand());
        this.cmds.add(new FilterCommand());
        this.cmds.add(new GetCommand());
        this.cmds.add(new DeleteCommand());
        this.cmds.add(new EditCommand());
        this.cmds.add(new TrackCommand());
        this.cmds.add(new ConvertCommand(wpl));
    }

    public WaypointTabCompleter getTabCompleter() {
        return this.tabCompleter;
    }

    public void displayHelp(CommandSender sender, String typedCmd) {
        sender.sendMessage(ChatColor.YELLOW + "-----"
                        + ChatColor.RESET + " ["
                        + ChatColor.RED + "Waypoints " 
                        + ChatColor.RESET + this.pdf.getVersion() 
                        + " by "+ ChatColor.DARK_AQUA + ChatColor.BOLD +"ZAKRZU"
                        + ChatColor.RESET + "] "
                        + ChatColor.YELLOW + "-----");
        for (BaseCommand waypointCmdBase : cmds) {
            String params = "";
            String optionalParams = "";
            for (String param : waypointCmdBase.getArgs()) {
                params += "<" + param + "> ";
            }
            for (String param : waypointCmdBase.getOptionalArgs()) {
                optionalParams += "[" + param + "] ";
            }
            sender.sendMessage(ChatColor.GOLD + "/" + typedCmd + " " + waypointCmdBase.getCmd() + " " + ChatColor.GRAY + params + ChatColor.DARK_GRAY + optionalParams + ChatColor.RESET + "- "
                    + waypointCmdBase.getDescription());
        }
    }

    public void displayCmdHelp(CommandSender sender, String typedCmd, BaseCommand cmd) {
        String params = "";
        String optionalParams = "";
        for (String param : cmd.getArgs()) {
            params += "<" + param + "> ";
        }
        for (String param : cmd.getOptionalArgs()) {
            optionalParams += "[" + param + "] ";
        }
        sender.sendMessage("Usage: /" + typedCmd + " " + cmd.getCmd() + " " + params + optionalParams);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        for (BaseCommand wpCmd : this.cmds) {
            // Security checks
            if (args.length < 1)
                break;
            if (args[0].equals("help") || args[0].equals("?"))
                break;
            // Get matching command
            if (wpCmd.getCmd().equals(args[0])) {
                // Prepare new argument list
                String[] cmdArgs = new String[args.length - 1];
                for (int i = 0; i < args.length - 1; i++) {
                    cmdArgs[i] = args[i + 1];
                }

                if (!wpCmd.execute(sender, cmdArgs)) {
                    displayCmdHelp(sender, label, wpCmd);
                }
                return true;
            }
        }
        displayHelp(sender, label);
        return true;
    }

}
