package com.minebone.tnttag.util;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;

public class CreateArenaData {
	private static HashMap<String, Location> lobbyLocation = new HashMap<String, Location>();
	private static HashMap<String, Location> arenaLocation = new HashMap<String, Location>();
	private static HashMap<String, Location> spectatorsLocation = new HashMap<String, Location>();

	public static void setArenaLocation(Player player) {
		arenaLocation.put(player.getName(), player.getLocation());
		MessageManager.getInstance().sendMessage(player, Messages.getMessage(Message.arenaTempSaved));
	}

	public static void setLobbyLocation(Player player) {
		lobbyLocation.put(player.getName(), player.getLocation());
		MessageManager.getInstance().sendMessage(player, Messages.getMessage(Message.lobbyTempSaved));
	}

	public static void setSpectatorsLocation(Player player) {
		spectatorsLocation.put(player.getName(), player.getLocation());
		MessageManager.getInstance().sendMessage(player, Messages.getMessage(Message.spectatorsTempSaved));
	}

	public static boolean check(Player player) {
		boolean b = false;
		if (lobbyLocation.get(player.getName()) == null) {
			if (!b) {
				b = true;
			}
			MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.lobbyMissing));
		}
		if (arenaLocation.get(player.getName()) == null) {
			if (!b) {
				b = true;
			}
			MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.arenaMissing));
		}
		if (spectatorsLocation.get(player.getName()) == null) {
			if (!b) {
				b = true;
			}
			MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.spectatorsMissing));
		}
		return b;
	}

	public static void createArena(String player, String arenaName) {
		ArenaManager.getManager().createArena(arenaName, (Location) lobbyLocation.get(player), (Location) arenaLocation.get(player), (Location) spectatorsLocation.get(player));
		spectatorsLocation.remove(player);
		arenaLocation.remove(player);
		lobbyLocation.remove(player);
	}
}
