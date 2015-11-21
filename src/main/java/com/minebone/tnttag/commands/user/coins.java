package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class coins extends AbstractTagCommands {

	public coins(TNTTag plugin) {
		super(plugin, "coins", Messages.getMessage(Message.coins), "", new Permissions().checkCoins, true, "money");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		sendMessage(sender, Messages.getMessage(Message.checkCoins).replace("{amount}", getPlayerData().getString(player.getName())));
	}
}
