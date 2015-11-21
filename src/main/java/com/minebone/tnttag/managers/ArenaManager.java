package com.minebone.tnttag.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.GameData;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;

public class ArenaManager {
	private static ArenaManager am = new ArenaManager();

	public static ArenaManager getManager() {
		return am;
	}

	public Arena getArena(String name) {
		for (Arena a : Arena.arenaObjects) {
			if (a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}

	public void addPlayers(Player player, String arenaName) {
		if (player.isInsideVehicle()) {
			player.leaveVehicle();
		}
		if (player.isSleeping()) {
			player.kickPlayer(Messages.getMessage(Message.joinedFromBed));
			return;
		}
		if (getArena(arenaName) != null) {
			Arena arena = getArena(arenaName);
			if (!arena.isFull()) {
				if (!arena.isInGame()) {
					InventoryManager.saveInventory(player);
					if (arena.getLobbyLocation() != null) {
						player.teleport(arena.getLobbyLocation());

						arena.getPlayers().add(player.getName());

						arena.getAlivePlayers().add(player.getName());
						int playersLeft = arena.getMinPlayers() - arena.getPlayers().size();

						arena.sendMessage(Messages.getMessage(Message.joinedGame).replace("{player}", player.getName()).replace("{size}", arena.getPlayers().size() + "").replace("{max_players}", arena.getMaxPlayers() + ""));
						if ((playersLeft == 0) && (!arena.runningCountdown())) {
							startArena(arenaName);
						}
						SignManager.getManager().updateSigns(arenaName);
					}
				} else {
					player.sendMessage(ChatColor.RED + Messages.getMessage(Message.arenaAlreadyStarted));
				}
			} else {
				player.sendMessage(ChatColor.RED + Messages.getMessage(Message.fullArena));
			}
		} else {
			player.sendMessage(ChatColor.RED + Messages.getMessage(Message.arenaNotFound));
		}
	}

	public boolean isInGame(Player player) {
		for (Arena arena : Arena.arenaObjects) {
			for(String s : arena.getPlayers()){
				if(player.getName().equalsIgnoreCase(s)){
					return true;
				}
			}
		}
		return false;
	}

	public static Arena get(Player player) {
		for (Arena arena : Arena.arenaObjects) {
			for(String s : arena.getPlayers()){
				if(player.getName().equalsIgnoreCase(s)){
					return arena;
				}
			}
		}
		return null;
	}

	public boolean isTNT(Player player) {
		for (Arena arena : Arena.arenaObjects) {
			for(String s : arena.getTNTPlayers()){
				if(player.getName().equalsIgnoreCase(s)){
					return true;
				}
			}
		}
		return false;
	}

	public void removePlayer(Player player) {
		Arena arena = get(player);

		player.getInventory().clear();
		InventoryManager.restoreInventory(player);

		arena.getPlayers().remove(player.getName());
		if (arena.getTNTPlayers().contains(player.getName())) {
			arena.getTNTPlayers().remove(player.getName());
		}
		if (arena.getAlivePlayers().contains(player.getName())) {
			arena.getAlivePlayers().remove(player.getName());
		}
		SignManager.getManager().updateSigns(arena.getName());

		arena.removeBoard(player);
		if (arena.getPlayers().size() == 1) {
			if (arena.isInGame()) {
				arena.sendMessage("The last player left!");
				endArena(arena);
			} else {
				CountdownManager.cancelTask(arena);
			}
		}
	}

	public void removeTNTPlayer(Player player) {
		Arena arena = get(player);

		arena.getTNTPlayers().remove(player.getName());
		arena.getAlivePlayers().add(player.getName());
	}

	public void addTNTPlayer(Player player) {
		Arena arena = get(player);

		arena.getTNTPlayers().add(player.getName());
		arena.getAlivePlayers().remove(player.getName());
	}

	public void startArena(String arenaName) {
		if (getArena(arenaName) != null) {
			Arena arena = getArena(arenaName);
			if (arena.getPlayers().size() >= 2) {
				CountdownManager.startGame(50, arena);
			}
		}
	}

	public void forceStartArena(String arenaName, CommandSender player) {
		Arena arena = getArena(arenaName);
		if (arena.getPlayers().size() >= 2) {
			if (!arena.isInGame()) {
				MessageManager.getInstance().sendMessage(player, Messages.getMessage(Message.forceStarting));
				CountdownManager.cancelTask(arena);
				CountdownManager.startGame(10, arena);
			} else {
				MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.forceStartAlreadyStarted));
			}
		} else {
			MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.minTwoPlayers));
		}
	}

	public void forceEndArena(String arenaName, Player player) {
		Arena arena = getArena(arenaName);
		if (arena.isInGame()) {
			MessageManager.getInstance().sendMessage(player, Messages.getMessage(Message.forceEnding));
			arena.sendMessage(Messages.getMessage(Message.forceEndKicked));
			endArena(arena);
		} else {
			MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.arenaHasAlreadyStarted));
		}
	}

	public void endArena(Arena arena) {
		if (arena.runningCountdown()) {
			CountdownManager.cancelTask(arena);
		}
		arena.endArena();
	}

	public void loadArenas() {
		FileConfiguration fc = GameData.getGameData();
		if (fc.getString("arenas") != null) {
			for (String keys : fc.getConfigurationSection("arenas").getKeys(false)) {
				World joinWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".lobby.world"));
				int joinX = fc.getInt("arenas." + keys + ".lobby.x");
				int joinY = fc.getInt("arenas." + keys + ".lobby.y");
				int joinZ = fc.getInt("arenas." + keys + ".lobby.z");
				float joinpitch = fc.getInt("arenas." + keys + ".lobby.pitch");
				float joinyaw = fc.getInt("arenas." + keys + ".lobby.yaw");
				Location joinLocation = new Location(joinWorld, joinX, joinY, joinZ, joinyaw, joinpitch);

				World startWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".arena.world"));
				int startX = fc.getInt("arenas." + keys + ".arena.x");
				int startY = fc.getInt("arenas." + keys + ".arena.y");
				int startZ = fc.getInt("arenas." + keys + ".arena.z");
				float startpitch = fc.getInt("arenas." + keys + ".arena.pitch");
				float startyaw = fc.getInt("arenas." + keys + ".arena.yaw");
				Location startLocation = new Location(startWorld, startX, startY, startZ, startyaw, startpitch);

				World endWorld = Bukkit.getWorld(fc.getString("arenas." + keys + ".spec.world"));
				int endX = fc.getInt("arenas." + keys + ".spec.x");
				int endY = fc.getInt("arenas." + keys + ".spec.y");
				int endZ = fc.getInt("arenas." + keys + ".spec.z");
				float endpitch = fc.getInt("arenas." + keys + ".spec.pitch");
				float endyaw = fc.getInt("arenas." + keys + ".spec.yaw");
				Location endLocation = new Location(endWorld, endX, endY, endZ, endyaw, endpitch);

				int maxPlayers = FileManager.getInstance().getMaxPlayers();
				int minPlayers = FileManager.getInstance().getMinPlayers();

				new Arena(keys, joinLocation, startLocation, endLocation, maxPlayers, minPlayers);
			}
		}
	}

	public void createArena(String arenaName, Location joinLocation, Location startLocation, Location endLocation) {
		int maxPlayers = FileManager.getInstance().getMaxPlayers();
		int minPlayers = FileManager.getInstance().getMinPlayers();

		new Arena(arenaName, joinLocation, startLocation, endLocation, maxPlayers, minPlayers);

		FileConfiguration fc = GameData.getGameData();

		fc.set("arenas." + arenaName, null);

		String path = "arenas." + arenaName + ".";

		int joinx = joinLocation.getBlockX();
		int joiny = joinLocation.getBlockY();
		int joinz = joinLocation.getBlockZ();
		float joinpitch = joinLocation.getPitch();
		float joinyaw = joinLocation.getYaw();
		String joinworld = joinLocation.getWorld().getName();

		fc.set(path + "lobby.world", joinworld);
		fc.set(path + "lobby.x", Integer.valueOf(joinx));
		fc.set(path + "lobby.y", Integer.valueOf(joiny));
		fc.set(path + "lobby.z", Integer.valueOf(joinz));
		fc.set(path + "lobby.pitch", Float.valueOf(joinpitch));
		fc.set(path + "lobby.yaw", Float.valueOf(joinyaw));

		int startx = startLocation.getBlockX();
		int starty = startLocation.getBlockY();
		int startz = startLocation.getBlockZ();
		float startpitch = startLocation.getPitch();
		float startyaw = startLocation.getYaw();
		String startworld = startLocation.getWorld().getName();

		fc.set(path + "arena.world", startworld);
		fc.set(path + "arena.x", Integer.valueOf(startx));
		fc.set(path + "arena.y", Integer.valueOf(starty));
		fc.set(path + "arena.z", Integer.valueOf(startz));
		fc.set(path + "arena.pitch", Float.valueOf(startpitch));
		fc.set(path + "arena.yaw", Float.valueOf(startyaw));

		int endx = endLocation.getBlockX();
		int endy = endLocation.getBlockY();
		int endz = endLocation.getBlockZ();
		float endpitch = endLocation.getPitch();
		float endyaw = endLocation.getYaw();
		String endworld = endLocation.getWorld().getName();

		fc.set(path + "spec.world", endworld);
		fc.set(path + "spec.x", Integer.valueOf(endx));
		fc.set(path + "spec.y", Integer.valueOf(endy));
		fc.set(path + "spec.z", Integer.valueOf(endz));
		fc.set(path + "spec.pitch", Float.valueOf(endpitch));
		fc.set(path + "spec.yaw", Float.valueOf(endyaw));

		fc.set(path + "maxPlayers", Integer.valueOf(maxPlayers));
		fc.set(path + "minPlayers", Integer.valueOf(minPlayers));

		FileManager.getInstance().saveConfig();
	}
}
