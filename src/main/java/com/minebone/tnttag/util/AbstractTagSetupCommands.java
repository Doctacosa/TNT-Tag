package com.minebone.tnttag.util;

import org.bukkit.permissions.Permission;

import com.minebone.tnttag.core.TNTTag;

public abstract class AbstractTagSetupCommands extends AbstractCommand {

	public AbstractTagSetupCommands(TNTTag plugin, String name, String desc, String args, Permission perm, boolean useperms) {
		super(plugin, name, desc, args, perm, useperms);
	}

}
