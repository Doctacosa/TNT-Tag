package com.minebone.tnttag.util;

import org.bukkit.permissions.Permission;

import com.minebone.tnttag.core.TNTTag;

public abstract class AbstractTagCommands extends AbstractCommand {

	public AbstractTagCommands(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms, String alias) {
		super(plugin, name, desc, args, perm, useperms, alias);
	}

}
