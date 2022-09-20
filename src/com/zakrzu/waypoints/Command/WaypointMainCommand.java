package com.zakrzu.waypoints.Command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.zakrzu.waypoints.WaypointsPlugin;

public class WaypointMainCommand implements CommandExecutor {

    private ArrayList<WaypointCmdBase> cmds;
    private WaypointTabCompleter tabCompleter;
    private PluginDescriptionFile pdf;

    public WaypointMainCommand(WaypointsPlugin wpl) {
        this.cmds = new ArrayList<>();
        this.pdf = wpl.getDescription();
        this.tabCompleter = new WaypointTabCompleter(cmds);
        this.cmds.add(new WaypointCmdAdd());
        this.cmds.add(new WaypointCmdVersion(this.pdf));
        this.cmds.add(new WaypointCmdList());
        this.cmds.add(new WaypointCmdFilter());
        this.cmds.add(new WaypointCmdGet());
        this.cmds.add(new WaypointCmdDelete());
        this.cmds.add(new WaypointCmdEdit());
        this.cmds.add(new WaypointCmdTrack());
    }

    public WaypointTabCompleter getTabCompleter() {
        return this.tabCompleter;
    }

    public void displayHelp(CommandSender sender, String typedCmd) {
        sender.sendMessage("[Waypoints " + this.pdf.getVersion() + " by ZAKRZU] -- Showing help");
        for (WaypointCmdBase waypointCmdBase : cmds) {
            String params = "";
            String optionalParams = "";
            for (String param : waypointCmdBase.getArgs()) {
                params += "<" + param + "> ";
            }
            for (String param : waypointCmdBase.getOptionalArgs()) {
                optionalParams += "[" + param + "] ";
            }
            sender.sendMessage("/" + typedCmd + " " + waypointCmdBase.getCmd() + " " + params + optionalParams + "- "
                    + waypointCmdBase.getDescription());
        }
    }

    public void displayCmdHelp(CommandSender sender, String typedCmd, WaypointCmdBase cmd) {
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
        sender.sendMessage("");
        for (WaypointCmdBase wpCmd : this.cmds) {
            // Security checks
            if (args.length < 1)
                break;
            if (args[0].equals("help") || args[0].equals("?"))
                break;
            // Get matching command
            if (wpCmd.getCmd().equals(args[0])) {
                if (wpCmd.getArgs().size() > args.length - 1) {
                    // Display help information about command
                    displayCmdHelp(sender, label, wpCmd);
                    if (wpCmd.getCmd().equals("filter")) {
                        // Should be fine by now
                        sender.sendMessage(wpCmd.getOptionalArgs() + " can be true or false");
                    }
                    return true;
                }

                // Prepare new arguemnt list
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
