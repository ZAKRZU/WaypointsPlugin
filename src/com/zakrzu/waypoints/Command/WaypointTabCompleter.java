package com.zakrzu.waypoints.Command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class WaypointTabCompleter implements TabCompleter {

    ArrayList<BaseCommand> cmds;

    public WaypointTabCompleter(ArrayList<BaseCommand> cmds) {
        this.cmds = cmds;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (BaseCommand wpCmd : cmds) {
                if (wpCmd.getCmd().startsWith(args[args.length - 1])) {
                    list.add(wpCmd.getCmd());
                }
                if (args[args.length - 1].isBlank()) {
                    list.add(wpCmd.getCmd());
                }
            }
        } else if (args.length > 1) {
            for (BaseCommand wpCmd : cmds) {
                if (args[0].equals(wpCmd.getCmd())) {
                    int argsPos = args.length - 2; // -1 becasue we count from 0, and -2 because first argument is cmd
                    if (wpCmd.getArgs().size() > 0 && wpCmd.getArgs().size() >= args.length - 1) {
                        if (args[args.length - 1].isBlank())
                            list.add(wpCmd.getArgs().get(argsPos));
                    }

                    int optionalPos = argsPos - wpCmd.getArgs().size();
                    if (optionalPos >= 0) {
                        if (wpCmd.getOptionalArgs().size() > 0
                                && (wpCmd.getOptionalArgs().size() + wpCmd.getArgs().size()) >= args.length - 1) {
                            if (args[args.length - 1].isBlank())
                                list.add(wpCmd.getOptionalArgs().get(optionalPos));
                        }
                    }
                }
            }
        }
        return list;
    }

}
