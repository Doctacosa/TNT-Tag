package com.minebone.tnttag.util;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;

import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.MessageManager;

public abstract class AbstractTagSetupCommands {
	private String name;
	private String desc;
	private String args;
	private Permission perm;
	private boolean useperms;

	public AbstractTagSetupCommands(String name, String desc, String args,
			Permission perm, boolean useperms) {
		this.name = name;
		this.desc = desc;
		this.args = args;
		this.perm = perm;
		this.useperms = useperms;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.desc;
	}

	public String getArgs() {
		return this.args;
	}

	public Permission getPermission() {
		return this.perm;
	}

	public void sendMessage(CommandSender sender, String s) {
		MessageManager.getInstance().sendMessage(sender, s);
	}

	public FileConfiguration getPlayerData() {
		return FileManager.getInstance().getPlayerData();
	}

	public abstract void onCommand(CommandSender paramCommandSender,
			String[] paramArrayOfString);

	public boolean usePermissions() {
		return this.useperms;
	}
}
