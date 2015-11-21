package com.minebone.tnttag.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Config;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class PlayerInteractListener implements Listener {
	
	private TNTTag plugin;

	public PlayerInteractListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void Place(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			if ((event.getClickedBlock().getType() == Material.WALL_SIGN) || (event.getClickedBlock().getType() == Material.SIGN_POST)) {
				TNTTagSign sign = plugin.getSignManager().getSignAtLocation(event.getClickedBlock().getLocation());
				if (sign != null) {
					if (plugin.getSignManager().tempSign.containsKey(player.getName())) {
						plugin.getMessageManager().sendMessage(event.getPlayer(), Messages.getMessage(Message.signAlreadyExists));
					} else if ((player.hasPermission(new Permissions().join)) || (!Config.getConfig().getBoolean("usepermissions"))) {
						if (!plugin.getArenaManager().isInGame(player)) {
							plugin.getArenaManager().addPlayers(player, sign.getArena());
							return;
						}
						plugin.getMessageManager().sendErrorMessage(player, Messages.getMessage(Message.leaveCurrentArena));
					}
				} else if (plugin.getSignManager().tempSign.containsKey(player.getName())) {
					event.setCancelled(true);
					Sign s = (Sign) event.getClickedBlock().getState();
					plugin.getSignManager().addSign(event.getClickedBlock().getLocation(), (String) plugin.getSignManager().tempSign.get(player.getName()), s);
					plugin.getMessageManager().sendMessage(event.getPlayer(), Messages.getMessage(Message.signCreated));
					plugin.getSignManager().tempSign.remove(player.getName());
				}
				return;
			}
			if (plugin.getArenaManager().isInGame(player)) {
				event.setCancelled(true);
			}
		}
	}
}
