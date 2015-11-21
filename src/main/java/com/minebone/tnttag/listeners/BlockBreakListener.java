package com.minebone.tnttag.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.minebone.tnttag.files.GameData;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.managers.SignManager;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class BlockBreakListener implements Listener {
	@EventHandler
	public void Break(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (ArenaManager.getManager().isInGame(player)) {
			event.setCancelled(true);
		} else if (SignManager.getManager().getSignAtLocation(
				event.getBlock().getLocation()) != null) {
			if (event.getPlayer().hasPermission(new Permissions().deleteSign)) {
				FileConfiguration fc = GameData.getGameData();
				fc.set("signs." + SignManager.getManager().getSignAtLocation(event.getBlock().getLocation()).getId(), null);
				TNTTagSign.signs.remove(SignManager.getManager().getSignAtLocation(event.getBlock().getLocation()));
				MessageManager.getInstance().sendMessage(event.getPlayer(), "Sign successfully removed.");
			} else {
				event.setCancelled(true);
				MessageManager.getInstance().sendErrorMessage(event.getPlayer(), "You do not have permission to remove a TNT Tag sign.");
			}
		}
	}
}
