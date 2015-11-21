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
		plugin.getServer().getPluginManager().addPermission(new Permissions().all);
		plugin.getServer().getPluginManager().addPermission(new Permissions().leave);
		plugin.getServer().getPluginManager().addPermission(new Permissions().join);
		plugin.getServer().getPluginManager().addPermission(new Permissions().spectate);
		plugin.getServer().getPluginManager().addPermission(new Permissions().checkCoins);
		plugin.getServer().getPluginManager().addPermission(new Permissions().forceStart);
		plugin.getServer().getPluginManager().addPermission(new Permissions().remove);
		plugin.getServer().getPluginManager().addPermission(new Permissions().resetStats);
		plugin.getServer().getPluginManager().addPermission(new Permissions().checkStats);
		plugin.getServer().getPluginManager().addPermission(new Permissions().setArena);
		plugin.getServer().getPluginManager().addPermission(new Permissions().setLobby);
		plugin.getServer().getPluginManager().addPermission(new Permissions().setSpec);
		plugin.getServer().getPluginManager().addPermission(new Permissions().transferCoins);
		plugin.getServer().getPluginManager().addPermission(new Permissions().reload);
		plugin.getServer().getPluginManager().addPermission(new Permissions().createArena);
		plugin.getServer().getPluginManager().addPermission(new Permissions().listArenas);
		plugin.getServer().getPluginManager().addPermission(new Permissions().deleteArena);
		plugin.getServer().getPluginManager().addPermission(new Permissions().createSign);
		plugin.getServer().getPluginManager().addPermission(new Permissions().update);
		plugin.getServer().getPluginManager().addPermission(new Permissions().deleteSign);
	}

	public void unloadPermissions(TNTTag plugin) {
		plugin.getServer().getPluginManager().removePermission(new Permissions().all);
		plugin.getServer().getPluginManager().removePermission(new Permissions().join);
		plugin.getServer().getPluginManager().removePermission(new Permissions().leave);
		plugin.getServer().getPluginManager().removePermission(new Permissions().spectate);
		plugin.getServer().getPluginManager().removePermission(new Permissions().checkCoins);
		plugin.getServer().getPluginManager().removePermission(new Permissions().forceStart);
		plugin.getServer().getPluginManager().removePermission(new Permissions().remove);
		plugin.getServer().getPluginManager().removePermission(new Permissions().checkStats);
		plugin.getServer().getPluginManager().removePermission(new Permissions().resetStats);
		plugin.getServer().getPluginManager().removePermission(new Permissions().setArena);
		plugin.getServer().getPluginManager().removePermission(new Permissions().setLobby);
		plugin.getServer().getPluginManager().removePermission(new Permissions().setSpec);
		plugin.getServer().getPluginManager().removePermission(new Permissions().transferCoins);
		plugin.getServer().getPluginManager().removePermission(new Permissions().reload);
		plugin.getServer().getPluginManager().removePermission(new Permissions().createArena);
		plugin.getServer().getPluginManager().removePermission(new Permissions().listArenas);
		plugin.getServer().getPluginManager().removePermission(new Permissions().deleteArena);
		plugin.getServer().getPluginManager().removePermission(new Permissions().createSign);
		plugin.getServer().getPluginManager().removePermission(new Permissions().update);
		plugin.getServer().getPluginManager().removePermission(new Permissions().deleteSign);
	}
}
