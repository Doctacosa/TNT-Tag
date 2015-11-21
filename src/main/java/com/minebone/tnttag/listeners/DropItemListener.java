package com.minebone.tnttag.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.minebone.tnttag.managers.ArenaManager;

public class DropItemListener implements Listener {
	
	@EventHandler
	public void Drop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (ArenaManager.getManager().isInGame(player)) {
			event.setCancelled(true);
		}
	}
}
