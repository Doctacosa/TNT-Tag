package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class leave extends AbstractTagCommands {

	public leave(TNTTag plugin) {
		super(plugin, "leave", Messages.getMessage(Message.leave), null, new Permissions().leave, true, "l");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (getArenaManager().isInGame(player)) {
			getArenaManager().removePlayer(player);
			return;
		}

		getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.notInArena));
	}
}
