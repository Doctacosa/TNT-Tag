package com.minebone.tnttag.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.managers.SignManager;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class reload extends AbstractTagAdminCommands {
	public reload() {
		super("reload", Messages.getMessage(Message.reload), null, new Permissions().reload, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		FileManager.getInstance().reloadConfig();
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage(Messages.getMessage(Message.thereWasReload));
			ArenaManager.getManager().endArena(arena);
		}
		Arena.arenaObjects.clear();
		TNTTagSign.signs.clear();
		ArenaManager.getManager().loadArenas();
		SignManager.getManager().loadSigns();
		MessageManager.getInstance().sendMessage(sender, ChatColor.GREEN + Messages.getMessage(Message.reloadComplete));
	}
}
