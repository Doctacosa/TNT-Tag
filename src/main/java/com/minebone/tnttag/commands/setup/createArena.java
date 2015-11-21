package com.minebone.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class createArena extends AbstractTagSetupCommands {
	
	public createArena(TNTTag plugin) {
		super(plugin, "createarena", Messages.getMessage(Message.createArena), "<ArenaName>", new Permissions().createArena, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "createarena", "<ArenaName>");
			return;
		}
		String arenaName = args[0];
		if (!getPlugin().getDataManager().getData(player).check()) {
			getPlugin().getDataManager().getData(player).createArena(arenaName);
			getMessageManager().sendMessage(sender, Messages.getMessage(Message.arenaCreated).replace("{arena}", arenaName));
		}
	}
}
