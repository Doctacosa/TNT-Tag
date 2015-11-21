package com.minebone.tnttag.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.GameData;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class BlockBreakListener implements Listener {

	private TNTTag plugin;

	public BlockBreakListener(TNTTag plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void Break(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (plugin.getArenaManager().isInGame(player)) {
			event.setCancelled(true);
		} else if (plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()) != null) {
			if (event.getPlayer().hasPermission(new Permissions().deleteSign)) {
				FileConfiguration fc = GameData.getGameData();
				fc.set("signs." + plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()).getId(), null);
				TNTTagSign.signs.remove(plugin.getSignManager().getSignAtLocation(event.getBlock().getLocation()));
				plugin.getMessageManager().sendMessage(event.getPlayer(), "Sign successfully removed.");
			} else {
				event.setCancelled(true);
				plugin.getMessageManager().sendErrorMessage(event.getPlayer(), "You do not have permission to remove a TNT Tag sign.");
			}
		}
	}
}
