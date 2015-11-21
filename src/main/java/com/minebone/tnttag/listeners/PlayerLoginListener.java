package com.minebone.tnttag.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.util.Permissions;

public class PlayerLoginListener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void latePlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (((player.hasPermission(new Permissions().all)) || (player.hasPermission(new Permissions().update))) && (TNTTag.versionDiff)) {
			player.sendMessage(ChatColor.GREEN + "An update is available: " + TNTTag.name + " Version " + TNTTag.version + ".");
			player.sendMessage(ChatColor.GREEN + "Downlaod it at:");
			player.sendMessage(ChatColor.GREEN + TNTTag.link);
			player.sendMessage(ChatColor.GREEN + "Or do /tnttag admin update.");
		}
	}
}
