package com.minebone.tnttag.managers;

import org.bukkit.configuration.file.FileConfiguration;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Config;
import com.minebone.tnttag.files.GameData;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.files.PlayerData;
import com.minebone.tnttag.files.Signs;

public class FileManager {

	public void setup(TNTTag p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
		Config.reload();
		Config.load();
		Config.save();
		Config.reload();

		PlayerData.reload();
		PlayerData.load();
		PlayerData.save();
		PlayerData.reload();

		GameData.reload();
		GameData.load();
		GameData.save();
		GameData.reload();

		Messages.reload();
		Messages.load();
		Messages.save();
		Messages.reload();

		Signs.reload();
		Signs.load();
		Signs.save();
		Signs.reload();

		p.getSignManager().loadSigns();
		p.getArenaManager().loadArenas();
	}

	public FileConfiguration getConfig() {
		return Config.getConfig();
	}

	public FileConfiguration getPlayerData() {
		return PlayerData.getPlayerData();
	}

	public FileConfiguration getGameData() {
		return GameData.getGameData();
	}

	public int getMaxPlayers() {
		return getConfig().getInt("maxplayers");
	}

	public int getMinPlayers() {
		return getConfig().getInt("minplayers");
	}

	public void saveConfig() {
		Config.save();
		PlayerData.save();
		GameData.save();
		Messages.save();
		Signs.reload();
	}

	public void reloadConfig() {
		Config.reload();
		PlayerData.reload();
		GameData.reload();
		Messages.reload();
		Signs.reload();
	}
}
