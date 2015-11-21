package com.minebone.tnttag.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.CommandManager;
import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.ListenerManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.managers.SignManager;
import com.minebone.tnttag.managers.TempArenaDataManager;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Permissions;

public class TNTTag extends JavaPlugin {

	private FileManager fileManager;
	private Permissions perms;
	private MessageManager messageManager;
	private ArenaManager arenaManager;
	private CommandManager commandManager;
	private SignManager signManager;
	private TempArenaDataManager dataManager;
	private Logger log;
	private boolean versionDiff = false;
	private String name;
	private String version;
	private String link;

	public void onEnable() {
		this.log = getLogger();
		this.fileManager = new FileManager();
		this.perms = new Permissions();
		this.perms.loadPermissions(this);
		this.messageManager = new MessageManager();
		this.arenaManager = new ArenaManager(this);
		this.commandManager = new CommandManager(this);
		this.signManager = new SignManager(this);
		this.dataManager = new TempArenaDataManager(this);
		this.fileManager.setup(this);
		
		ListenerManager.registerEvents(this);
		getCommand("tnttag").setExecutor(commandManager);
		getCommand("tag").setExecutor(commandManager);
		getServer().getScheduler().runTaskAsynchronously(this, new BukkitRunnable() {
			public void run() {
				checkUpdate();
			}
		});
		
		this.log.info("Has Been Enabled!");
	}

	public void onDisable() {
		this.log = getLogger();
		this.fileManager.saveConfig();
		
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage("There was a reload");
			arenaManager.endArena(arena);
		}
		
		this.perms.unloadPermissions(this);
		this.log.info("Has Been Disabled!");
	}

	private void checkUpdate() {
		Updater updater = new Updater(this, 73538, getFile(), UpdateType.NO_DOWNLOAD, false);
		UpdateResult result = updater.getResult();
		
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

	public void update(CommandSender sender) {
		if (versionDiff) {
			new Updater(this, 73538, getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
			messageManager.sendMessage(sender, "Relaod when update is complete.");
			return;
		}
		messageManager.sendErrorMessage(sender, "No update is available!");
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
	public MessageManager getMessageManager() {
		return messageManager;
	}

	public ArenaManager getArenaManager() {
		return arenaManager;
	}
	
	public SignManager getSignManager() {
		return signManager;
	}
	
	public TempArenaDataManager getDataManager() {
		return dataManager;
	}
	
	public String getTNTName() {
		return name;
	}
	
	public boolean isUpdate() {
		return versionDiff;
	}
	
	public String getVersionString() {
		return version;
	}
	
	public String getLink() {
		return link;
	}
}
