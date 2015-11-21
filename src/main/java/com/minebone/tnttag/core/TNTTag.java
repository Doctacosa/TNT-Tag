package com.minebone.tnttag.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.CommandManager;
import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.ListenerManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Permissions;

public class TNTTag extends JavaPlugin {
	FileManager settings = FileManager.getInstance();
	Permissions perms = new Permissions();
	protected Logger log;
	public static TNTTag main;
	public static boolean versionDiff = false;
	public static boolean update = false;
	public static String name;
	public static String version;
	public static String link;

	public void onEnable() {
		this.log = getLogger();
		getServer().getScheduler().runTaskAsynchronously(this, new BukkitRunnable() {
			public void run() {
				checkUpdate();
			}
		});
		main = this;
		this.settings.setup(this);
		this.perms.loadPermissions(this);
		ListenerManager.registerEvents(this);
		getCommand("tnttag").setExecutor(new CommandManager());
		getCommand("tag").setExecutor(new CommandManager());
		this.log.info("Has Been Enabled!");
	}

	public void onDisable() {
		main = null;
		this.log = getLogger();
		this.settings.saveConfig();
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage("There was a reload");
			ArenaManager.getManager().endArena(arena);
		}
		this.perms.unloadPermissions(this);
		this.log.info("Has Been Disabled!");
	}

	private void checkUpdate() {
		Updater updater = new Updater(this, 73538, getFile(),
				Updater.UpdateType.NO_DOWNLOAD, false);
		Updater.UpdateResult result = updater.getResult();
		switch (result) {
		case FAIL_DBO:
			this.log.log(Level.INFO, "The updater could not contact dev.bukkit.org.");
			break;
		case NO_UPDATE:
			this.log.log(Level.INFO, "TNT Tag is up to date.");
			versionDiff = false;
			break;
		case UPDATE_AVAILABLE:
			name = updater.getLatestName();
			version = updater.getLatestGameVersion();
			link = updater.getLatestFileLink();
			this.log.log(Level.INFO, "============================================");
			this.log.log(Level.INFO, "An update is available:");
			this.log.log(Level.INFO, name + " is available for download at");
			this.log.log(Level.INFO, link);
			this.log.log(Level.INFO, "============================================");
			versionDiff = true;
			break;
		default:
			System.out.println(result);
		}
	}

	public void Update(CommandSender sender) {
		if (versionDiff) {
			new Updater(this, 73538, getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
			MessageManager.getInstance().sendMessage(sender, "Relaod when update is complete.");
		} else {
			MessageManager.getInstance().sendErrorMessage(sender, "No update is available!");
		}
	}
}
