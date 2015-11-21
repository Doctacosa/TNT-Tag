package com.minebone.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.CreateArenaData;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class setArenaPoint extends AbstractTagSetupCommands {
	public setArenaPoint() {
		super("setarena", Messages.getMessage(Message.setArenaPoint), null, new Permissions().setArena, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setArenaLocation(player);
	}
}
