# About

A plugin for the Minecraft Spigot server that allows you to save *waypoints*.

# Tested with
- Java 17
- Minecraft Spigot 19.2-R0.1-SNAPSHOT

# Changelog
## 1.2.1
- Replaced system logger with plugin logger
- Added better error handling
## 1.2.0
- Code base rewrite
- Fixed memory leak
- Added color support
- Added SQLite support
- SQLite is now default database engine (ConfigDatabase is deprecated and will be removed in the next version)
- Added convert command that allows you to convert from old ConfigDatabase to new SQLite
## 1.1.0
- Added simple tab completion system
- Added tracking command (changes compass target)
    - wp track `[id or restore]` - compass wil show the waypoint (wp track `restore` to restore compass target to spawn point)
- Added edit command
    - wp edit `<id> <x or current> [y] [z]` - edits waypoint details (wp edit `<id> current` to use your current position)
- Improved command system
- Fixed waypoint update function 
- Version command no longer shows help string
## 1.0.1
- Command system rewrite
- Few minor improvements
## 1.0.0
- Init version
- Added commands 
    - wp add `<name> [x] [y] [z]` - creates waypoint
    - wp get `<id or name>` - displays details about waypoint
    - wp delete `<id>` - removes waypoint from list
    - wp filter `<word>` - filtered search
    - wp list - lists waypoints
    - wp version - plugin details