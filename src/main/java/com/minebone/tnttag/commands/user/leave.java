package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class leave extends AbstractTagCommands {
	public leave() {
		super("leave", Messages.getMessage(Message.leave), null, new Permissions().leave, true, "l");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (ArenaManager.getManager().isInGame(player)) {
			ArenaManager.getManager().removePlayer(player);
		} else {
			MessageManager.getInstance().sendErrorMessage(sender, Messages.getMessage(Message.notInArena));
		}
	}
}
