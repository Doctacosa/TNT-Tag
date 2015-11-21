package com.minebone.tnttag.commands.setup;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.CreateArenaData;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class setLobby extends AbstractTagSetupCommands {
	public setLobby() {
		super("setlobby", Messages.getMessage(Message.setLobby), null, new Permissions().setLobby, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		CreateArenaData.setLobbyLocation(player);
	}
}
