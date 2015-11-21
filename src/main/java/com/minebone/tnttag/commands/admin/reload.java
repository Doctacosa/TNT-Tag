package com.minebone.tnttag.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;
import com.minebone.tnttag.util.TNTTagSign;

public class reload extends AbstractTagAdminCommands {
	
	public reload(TNTTag plugin ) {
		super(plugin, "reload", Messages.getMessage(Message.reload), null, new Permissions().reload, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		getFileManager().reloadConfig();
		
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage(Messages.getMessage(Message.thereWasReload));
			getArenaManager().endArena(arena);
		}
		
		Arena.arenaObjects.clear();
		TNTTagSign.signs.clear();
		getArenaManager().loadArenas();
		getPlugin().getSignManager().loadSigns();
		getMessageManager().sendMessage(sender, ChatColor.GREEN + Messages.getMessage(Message.reloadComplete));
	}
}
