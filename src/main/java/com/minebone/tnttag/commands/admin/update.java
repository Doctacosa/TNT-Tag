package com.minebone.tnttag.commands.admin;

import org.bukkit.command.CommandSender;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Permissions;

public class update extends AbstractTagAdminCommands {
	public update() {
		super("update", "Updates the plugin.", null, new Permissions().update, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		TNTTag.main.Update(sender);
	}
}
