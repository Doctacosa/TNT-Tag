package com.minebone.tnttag.commands.admin;

import org.bukkit.command.CommandSender;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class forceStart extends AbstractTagAdminCommands {
	public forceStart() {
		super("forcestart", Messages.getMessage(Message.forceStart), null, new Permissions().forceStart, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			MessageManager.getInstance().sendInsuficientArgs(sender, "forcestart", "<ArenaName>");

			StringBuilder arenaNames = new StringBuilder(
					Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}
			MessageManager.getInstance().sendMessage(sender, arenaNames.toString());

			return;
		}
		String arenaName = args[0];
		if (ArenaManager.getManager().getArena(arenaName) != null) {
			ArenaManager.getManager().forceStartArena(arenaName, sender);
		} else {
			MessageManager.getInstance().sendErrorMessage(sender, Messages.getMessage(Message.invalidArena));

			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}
			MessageManager.getInstance().sendMessage(sender, arenaNames.toString());
		}
	}
}
