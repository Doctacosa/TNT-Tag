package com.minebone.tnttag.commands.admin;

import org.bukkit.command.CommandSender;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Permissions;

public class resetStats extends AbstractTagAdminCommands {
	
	public resetStats(TNTTag plugin) {
		super(plugin, "resetstats", "Reset stats for a player.", "<player>", new Permissions().resetStats, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
	}
}
