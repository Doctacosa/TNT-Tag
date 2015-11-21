package com.minebone.tnttag.util;

import org.bukkit.permissions.Permission;

import com.minebone.tnttag.core.TNTTag;

public class Permissions {
	
	public Permission all = new Permission("tnttag.*");
	public Permission join = new Permission("tnttag.join");
	public Permission leave = new Permission("tnttag.leave");
	public Permission spectate = new Permission("tnttag.spectate");
	public Permission add = new Permission("tnttag.add");
	public Permission remove = new Permission("tnttag.remove");
	public Permission resetStats = new Permission("tnttag.resetstats");
	public Permission checkCoins = new Permission("tnttag.checkcoins");
	public Permission checkStats = new Permission("tnttag.checkstats");
	public Permission transferCoins = new Permission("tnttag.transfercoins");
	public Permission setLobby = new Permission("tnttag.setlobby");
	public Permission setArena = new Permission("tnttag.setarena");
	public Permission setSpec = new Permission("tnttag.setspec");
	public Permission forceStart = new Permission("tnttag.forcestart");
	public Permission reload = new Permission("tnttag.reload");
	public Permission createArena = new Permission("tnttag.create");
	public Permission listArenas = new Permission("tnttag.listarenas");
	public Permission deleteArena = new Permission("tnttag.deleteArena");
	public Permission createSign = new Permission("tnttag.createsign");
	public Permission deleteSign = new Permission("tnttag.deletesign");
	public Permission update = new Permission("tnttag.update");

	public void loadPermissions(TNTTag plugin) {
		plugin.getServer().getPluginManager().addPermission(all);
		plugin.getServer().getPluginManager().addPermission(leave);
		plugin.getServer().getPluginManager().addPermission(join);
		plugin.getServer().getPluginManager().addPermission(spectate);
		plugin.getServer().getPluginManager().addPermission(checkCoins);
		plugin.getServer().getPluginManager().addPermission(forceStart);
		plugin.getServer().getPluginManager().addPermission(remove);
		plugin.getServer().getPluginManager().addPermission(resetStats);
		plugin.getServer().getPluginManager().addPermission(checkStats);
		plugin.getServer().getPluginManager().addPermission(setArena);
		plugin.getServer().getPluginManager().addPermission(setLobby);
		plugin.getServer().getPluginManager().addPermission(setSpec);
		plugin.getServer().getPluginManager().addPermission(transferCoins);
		plugin.getServer().getPluginManager().addPermission(reload);
		plugin.getServer().getPluginManager().addPermission(createArena);
		plugin.getServer().getPluginManager().addPermission(listArenas);
		plugin.getServer().getPluginManager().addPermission(deleteArena);
		plugin.getServer().getPluginManager().addPermission(createSign);
		plugin.getServer().getPluginManager().addPermission(update);
		plugin.getServer().getPluginManager().addPermission(deleteSign);
	}

	public void unloadPermissions(TNTTag plugin) {
		plugin.getServer().getPluginManager().removePermission(all);
		plugin.getServer().getPluginManager().removePermission(join);
		plugin.getServer().getPluginManager().removePermission(leave);
		plugin.getServer().getPluginManager().removePermission(spectate);
		plugin.getServer().getPluginManager().removePermission(checkCoins);
		plugin.getServer().getPluginManager().removePermission(forceStart);
		plugin.getServer().getPluginManager().removePermission(remove);
		plugin.getServer().getPluginManager().removePermission(checkStats);
		plugin.getServer().getPluginManager().removePermission(resetStats);
		plugin.getServer().getPluginManager().removePermission(setArena);
		plugin.getServer().getPluginManager().removePermission(setLobby);
		plugin.getServer().getPluginManager().removePermission(setSpec);
		plugin.getServer().getPluginManager().removePermission(transferCoins);
		plugin.getServer().getPluginManager().removePermission(reload);
		plugin.getServer().getPluginManager().removePermission(createArena);
		plugin.getServer().getPluginManager().removePermission(listArenas);
		plugin.getServer().getPluginManager().removePermission(deleteArena);
		plugin.getServer().getPluginManager().removePermission(createSign);
		plugin.getServer().getPluginManager().removePermission(update);
		plugin.getServer().getPluginManager().removePermission(deleteSign);
	}
}
