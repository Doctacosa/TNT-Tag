package com.minebone.tnttag.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.minebone.tnttag.core.TNTTag;

public class PlayerQuitListener implements Listener {
	
	private TNTTag plugin;

	public PlayerQuitListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Quit(PlayerQuitEvent event) {
		if (plugin.getArenaManager().isInGame(event.getPlayer())) {
			plugin.getArenaManager().removePlayer(event.getPlayer());
		}
	}
}
