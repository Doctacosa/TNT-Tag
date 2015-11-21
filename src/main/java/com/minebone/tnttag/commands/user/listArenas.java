package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class listArenas extends AbstractTagCommands {
	public listArenas() {
		super("listArenas", Messages.getMessage(Message.listArenas), null, new Permissions().join, true, "arenas");
	}

	public void onCommand(CommandSender sender, String[] args) {
		StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

		int x = 0;
		for (Arena arena : Arena.arenaObjects) {
			x++;
			arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
		}
		MessageManager.getInstance().sendMessage(sender, arenaNames.toString());
	}
}
