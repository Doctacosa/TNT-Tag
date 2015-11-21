package com.minebone.tnttag.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.Message;

public class PlayerCommandPreprocessListener implements Listener {
	
	private TNTTag plugin;

	public PlayerCommandPreprocessListener(TNTTag plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	private void blockcommand(PlayerCommandPreprocessEvent event) {
		List<String> cmds = plugin.getFileManager().getConfig().getStringList("AllowedCommands");
		cmds.add("tag");
		cmds.add("tnttag");
		String cmdPerformed = event.getMessage().toLowerCase();
		if (plugin.getArenaManager().isInGame(event.getPlayer())) {
			for (String command : cmds) {
				if (cmdPerformed.startsWith("/" + command)) {
					return;
				}
			}
			event.setCancelled(true);
			plugin.getMessageManager().sendErrorMessage(event.getPlayer(), Messages.getMessage(Message.commandError).replace("{command}", cmdPerformed));
		}
	}
}
