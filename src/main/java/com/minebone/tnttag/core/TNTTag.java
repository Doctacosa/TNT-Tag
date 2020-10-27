package com.minebone.tnttag.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

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
	private Economy economy;
	private Logger logger;
	private boolean versionDiff = false;
	private boolean useEconomy = false;
	private String name;
	private String version;
	private String link;

	public void onEnable() {
		final long currentTime = System.currentTimeMillis();
		Bukkit.getConsoleSender().sendMessage("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3] §e");
		Bukkit.getConsoleSender().sendMessage("§e=== ENABLE START ===");
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}
		
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			log("Found Vault!");
			log(ChatColor.YELLOW + "Hooking in...");
			RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
			if (rsp != null) {
				if (rsp.getProvider() != null) {
					economy = rsp.getProvider();
					useEconomy = true;
					log("Hooked into Vault's economy!");
				}
			}
			if (!useEconomy) {
				log(ChatColor.RED + "Could not hook into vault.");
				log(ChatColor.RED + "Economy options for this plugin will not work.");
			}
		}
		
		this.logger = getLogger();
		this.fileManager = new FileManager();
		
		log(ChatColor.YELLOW + "Registering permissions");
		this.perms = new Permissions();
		this.perms.loadPermissions(this);
		log("Registered all permissions");
		
		log(ChatColor.YELLOW + "Loading the managers");
		this.messageManager = new MessageManager();
		log("Message Manager loaded");
		
		this.arenaManager = new ArenaManager(this);
		log("Arena Manager loaded");
		
		this.commandManager = new CommandManager(this);
		log("Command Manager loaded");
		
		this.signManager = new SignManager(this);
		log("Sign Manager loaded");
		
		this.dataManager = new TempArenaDataManager(this);
		log("Data Manager loaded");
		
		log(ChatColor.YELLOW + "Loading files");
		this.fileManager.setup(this);
		log("Successfully loaded all the files");
		ListenerManager.registerEvents(this);
		getCommand("tnttag").setExecutor(commandManager);
		getCommand("tag").setExecutor(commandManager);
		getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
			public void run() {
				checkUpdate();
			}
		});
		
		Bukkit.getConsoleSender().sendMessage("§e=== ENABLE §aCOMPLETE §e(Took §d" + (System.currentTimeMillis() - currentTime) + "ms§e) ===");
	}

	public void onDisable() {
		final long currentTime = System.currentTimeMillis();
		Bukkit.getConsoleSender().sendMessage("§3[§b" + this.getDescription().getName() + " " + this.getDescription().getVersion() + "§3] §e");
		Bukkit.getConsoleSender().sendMessage("§e=== DISABLING ===");
		log(ChatColor.YELLOW + "Saving files");
		this.fileManager.saveConfig();
		log("All files saved!");
		
		log(ChatColor.YELLOW + "Ending arenas");
		for (Arena arena : Arena.arenaObjects) {
			arena.sendMessage("There was a reload");
			arenaManager.endArena(arena);
		}
		log("Arenas ended!");
		
		log(ChatColor.YELLOW + "Unloading permissions");
		this.perms.unloadPermissions(this);
		log("Unloaded permissions!");
		Bukkit.getConsoleSender().sendMessage("§e=== DISABLE §aCOMPLETE §e(Took §d" + (System.currentTimeMillis() - currentTime) + "ms§e) ===");
	}

	private void checkUpdate() {
		Updater updater = new Updater(this, 73538, getFile(), UpdateType.NO_DOWNLOAD, false);
		UpdateResult result = updater.getResult();
		
		switch (result) {
			case FAIL_DBO:
				this.logger.log(Level.INFO, "The updater could not contact dev.bukkit.org.");
				break;
			case NO_UPDATE:
				this.logger.log(Level.INFO, "TNT Tag is up to date.");
				versionDiff = false;
				break;
			case UPDATE_AVAILABLE:
				name = updater.getLatestName();
				version = updater.getLatestGameVersion();
				link = updater.getLatestFileLink();
				this.logger.log(Level.INFO, "============================================");
				this.logger.log(Level.INFO, "An update is available:");
				this.logger.log(Level.INFO, name + " is available for download at");
				this.logger.log(Level.INFO, link);
				this.logger.log(Level.INFO, "============================================");
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
	
	public void log(String string) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + string);
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
	
	public Economy getEconomy() {
		return economy;
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
