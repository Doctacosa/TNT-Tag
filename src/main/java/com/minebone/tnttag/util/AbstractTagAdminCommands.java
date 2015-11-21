package com.minebone.tnttag.util;

import org.bukkit.permissions.Permission;

import com.minebone.tnttag.core.TNTTag;

public abstract class AbstractTagAdminCommands extends AbstractCommand {

	public AbstractTagAdminCommands(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms) {
		super(plugin, name, desc, args, perm, useperms);
	}
}
