package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class checkStats extends AbstractTagCommands {
	public checkStats() {
		super("checkstats", Messages.getMessage(Message.checkStats), null,
				new Permissions().checkStats, true, "stats");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;

		int money = getPlayerData().getInt(player.getName() + ".money");
		int tags = getPlayerData().getInt(player.getName() + ".tags");
		int taggeds = getPlayerData().getInt(player.getName() + ".taggeds");
		int wins = getPlayerData().getInt(player.getName() + ".wins");

		sendMessage(sender, Messages.getMessage(Message.stats).replace("{money}", money + "").replace("{tags}", tags + "").replace("{taggeds}", taggeds + "").replace("{wins}", wins + ""));
	}
}
