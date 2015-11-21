package com.minebone.tnttag.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.Message;

public class PlayerCommandPreprocessListener implements Listener {
	@EventHandler
	private void blockcommand(PlayerCommandPreprocessEvent event) {
		List<String> cmds = FileManager.getInstance().getConfig().getStringList("AllowedCommands");
		cmds.add("tag");
		cmds.add("tnttag");
		String cmdPerformed = event.getMessage().toLowerCase();
		if (ArenaManager.getManager().isInGame(event.getPlayer())) {
			for (String command : cmds) {
				if (cmdPerformed.startsWith("/" + command)) {
					return;
				}
			}
			event.setCancelled(true);
			MessageManager.getInstance().sendErrorMessage(event.getPlayer(), Messages.getMessage(Message.commandError).replace("{command}", cmdPerformed));
		}
	}
}
