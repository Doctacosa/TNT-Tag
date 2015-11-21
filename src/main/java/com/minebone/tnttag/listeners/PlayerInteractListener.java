package com.minebone.tnttag.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.minebone.tnttag.files.Config;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.managers.SignManager;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void Place(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if (action == Action.RIGHT_CLICK_BLOCK) {
			if ((event.getClickedBlock().getType() == Material.WALL_SIGN) || (event.getClickedBlock().getType() == Material.SIGN_POST)) {
				TNTTagSign sign = SignManager.getManager().getSignAtLocation(event.getClickedBlock().getLocation());
				if (sign != null) {
					if (SignManager.getManager().tempSign.containsKey(player.getName())) {
						MessageManager.getInstance().sendMessage(event.getPlayer(), Messages.getMessage(Message.signAlreadyExists));
					} else if ((player.hasPermission(new Permissions().join)) || (!Config.getConfig().getBoolean("usepermissions"))) {
						if (!ArenaManager.getManager().isInGame(player)) {
							ArenaManager.getManager().addPlayers(player, sign.getArena());
							return;
						}
						MessageManager.getInstance().sendErrorMessage(player, Messages.getMessage(Message.leaveCurrentArena));
					}
				} else if (SignManager.getManager().tempSign.containsKey(player.getName())) {
					event.setCancelled(true);
					Sign s = (Sign) event.getClickedBlock().getState();
					SignManager.getManager().addSign(event.getClickedBlock().getLocation(), (String) SignManager.getManager().tempSign.get(player.getName()), s);
					MessageManager.getInstance().sendMessage(event.getPlayer(), Messages.getMessage(Message.signCreated));
					SignManager.getManager().tempSign.remove(player.getName());
				}
				return;
			}
			if (ArenaManager.getManager().isInGame(player)) {
				event.setCancelled(true);
			}
		}
	}
}
