# TNT Tag

**Original content from https://dev.bukkit.org/projects/tnt_tag**

Ever wanted to play hot potatoe in minecraft? This plugins allows you to play! You set up the arena and join. Your inventory, gamemode, everything will be saved when you join. You will be teleported to the lobby. After the minimum players required to start join, the game will automatically start. Every player will be teleported to the arena, random players will be the "Hot Potatoes". The "Hot Potatoes" will be represented by having TNTs on their hand and on their head. You hit a player in order to pass the TNT. After the configurable time has passed, all the TNTs will explode eliminating players. There will be multiple rounds until the final player wins. When you leave TNT Tag, everything will be restored. Check down for the things that will be saved. This plugin will require Java 8!


## Features

* Lobby, arena, spectator area
* Economy (Tag coins)
* Permission support
* Multiple Arenas
* Force start
* Speed
* Whitelisted commands
* Delete arenas
* List arenas
* Join signs (fixed)
* Updater
* messages.yml file


## Things that are going to be saved and restored

* Inventory
* Potion effects
* GameMode
* Health
* FoodBar
* XP level
* Location
* Flying status


## Commands and permissions

### User Commands
|Command | Function | Permission|
|--- | --- | --- |
|/tnttag join | Joins TNT Tag | tnttag.join|
|/tnttag leave | Leaves TNT Tag | tnttag.leave|
|/tnttag spectate | Spectate TNT Tag | tnttag.spec|
|/tnttag stats | Check your stats | tnttag.stats|
|/tnttag coins transfer <player> <amount> | Transfer coins to a player | tnttag.transfer|

### Admin Commands
|Command | Function | Permission|
|--- | --- | --- |
|/tnttag admin reload | Reloads The Config | tnttag.admin.reload|
|/tnttag admin add <player> <item> <amount> | Adds a value to a player (coins, wins, tags, taggeds) | tnttag.admin.add|
|/tnttag admin remove <player> <item> <amount> | Removes a value to a player (coins, wins, tags, taggeds) | tnttag.admin.remove|
|/tnttag admin resetstats <player> | Reset stats for a player | tnttag.admin.resetstats|

### Setup Commands
|Command | Function | Permission|
|--- | --- | --- |
|/tnttag setup setlobby | Save the lobby point temporarily | tnttag.setup.setlobby|
|/tnttag setup setarena | Save the arena point temporarily | tnttag.setup.setarena|
|/tnttag setup setspec | Save the spectators point temporarily | tnttag.setup.setspec|
|/tnttag setup createarena <arenaname> | Create the arena with the temporarily objects | tnttag.setup.createarena|

### Misc
|Command | Function | Permission|
|--- | --- | --- |
|*No Command* | All permissions | tnttag.*|