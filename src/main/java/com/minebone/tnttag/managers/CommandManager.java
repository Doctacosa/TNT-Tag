package com.minebone.tnttag.managers;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.commands.admin.add;
import com.minebone.tnttag.commands.admin.forceEnd;
import com.minebone.tnttag.commands.admin.forceStart;
import com.minebone.tnttag.commands.admin.reload;
import com.minebone.tnttag.commands.admin.remove;
import com.minebone.tnttag.commands.admin.resetStats;
import com.minebone.tnttag.commands.admin.update;
import com.minebone.tnttag.commands.setup.createArena;
import com.minebone.tnttag.commands.setup.createSign;
import com.minebone.tnttag.commands.setup.deleteArena;
import com.minebone.tnttag.commands.setup.setArenaPoint;
import com.minebone.tnttag.commands.setup.setLobby;
import com.minebone.tnttag.commands.setup.setSpectatorsPoint;
import com.minebone.tnttag.commands.user.checkStats;
import com.minebone.tnttag.commands.user.coins;
import com.minebone.tnttag.commands.user.join;
import com.minebone.tnttag.commands.user.leave;
import com.minebone.tnttag.commands.user.listArenas;
import com.minebone.tnttag.commands.user.transfer;
import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.Permissions;

public class CommandManager implements CommandExecutor {

	private TNTTag plugin;
	private ArrayList<AbstractTagCommands> cmds = new ArrayList<AbstractTagCommands>();
	private ArrayList<AbstractTagAdminCommands> adminCmds = new ArrayList<AbstractTagAdminCommands>();
	private ArrayList<AbstractTagSetupCommands> setupCmds = new ArrayList<AbstractTagSetupCommands>();

	public CommandManager(TNTTag plugin) {
		this.plugin = plugin;
		this.cmds.add(new join(plugin));
		this.cmds.add(new leave(plugin));
		this.cmds.add(new coins(plugin));
		this.cmds.add(new checkStats(plugin));
		this.cmds.add(new transfer(plugin));
		this.cmds.add(new listArenas(plugin));

		this.adminCmds.add(new add(plugin));
		this.adminCmds.add(new remove(plugin));
		this.adminCmds.add(new resetStats(plugin));
		this.adminCmds.add(new forceEnd(plugin));
		this.adminCmds.add(new forceStart(plugin));
		this.adminCmds.add(new reload(plugin));
		this.adminCmds.add(new update(plugin));

		this.setupCmds.add(new setLobby(plugin));
		this.setupCmds.add(new setSpectatorsPoint(plugin));
		this.setupCmds.add(new setArenaPoint(plugin));
		this.setupCmds.add(new createArena(plugin));
		this.setupCmds.add(new deleteArena(plugin));
		this.setupCmds.add(new createSign(plugin));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if ((cmd.getName().equalsIgnoreCase("tnttag")) || (cmd.getName().equalsIgnoreCase("tag"))) {
			if (args.length == 0) {
				showHelp(sender);
				return true;
			}
			ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
			a.remove(0);
			if (args[0].equalsIgnoreCase("admin")) {
				if (args.length != 1) {
					for (AbstractTagAdminCommands c : this.adminCmds) {
						if (c.getName().equalsIgnoreCase(args[1])) {
							if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
								a.remove(0);
								if (args.length != 1) {
									try {
										c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "An error has occurred.");
										e.printStackTrace();
									}
									return true;
								}
								plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
							} else {
								sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
								return true;
							}
						}
					}
					plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
					return true;
				}
				showAdminHelp(sender);
			} else if (args[0].equalsIgnoreCase("setup")) {
				if (!(sender instanceof Player)) {
					plugin.getMessageManager().isConsole(sender);
					return false;
				}
				if (args.length != 1) {
					for (AbstractTagSetupCommands c : this.setupCmds) {
						if (c.getName().equalsIgnoreCase(args[1])) {
							if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
								a.remove(0);
								if (args.length != 1) {
									try {
										c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
									} catch (Exception e) {
										sender.sendMessage(ChatColor.RED + "An error has occurred.");
										e.printStackTrace();
									}
									return true;
								}
								plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
							} else {
								sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
								return true;
							}
						}
					}
					plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
					return true;
				}
				showCreateHelp(sender);
			} else {
				/*
				if (!(sender instanceof Player)) {
					plugin.getMessageManager().isConsole(sender);
					return false;
				}
				*/
				for (AbstractTagCommands c : this.cmds) {
					if ((c.getName().equalsIgnoreCase(args[0])) || (c.getAlias().equalsIgnoreCase(args[0]))) {
						if (c.usePermissions() ? (sender.hasPermission(c.getPermission())) || (sender.hasPermission(new Permissions().all)) : !c.usePermissions()) {
							if (args.length != 0) {
								try {
									c.onCommand(sender, (String[]) a.toArray(new String[a.size()]));
								} catch (Exception e) {
									sender.sendMessage(ChatColor.RED + "An error has occurred.");
									e.printStackTrace();
								}
								return true;
							}
							plugin.getMessageManager().sendInsuficientArgs(sender, c.getName(), c.getArgs());
						} else {
							sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
							return true;
						}
					}
				}
				plugin.getMessageManager().sendErrorMessage(sender, "Invalid Command!");
				return true;
			}
		} else {
			System.out.println("Unknown Error - TNT Tag. Please report.");
		}
		return true;
	}

	public void showHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "=================== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag Help " + ChatColor.AQUA + "===================");
		for (AbstractTagCommands c : this.cmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag " + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()) + ChatColor.DARK_AQUA + c.getDescription());
		}
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}

	public void showAdminHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "================ " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag  Admin Help " + ChatColor.AQUA + "================");
		for (AbstractTagAdminCommands c : this.adminCmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag admin " + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()) + ChatColor.DARK_AQUA + c.getDescription());
		}
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}

	public void showCreateHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.AQUA + "=============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "TNT Tag Setup Help " + ChatColor.AQUA + "==================");
		for (AbstractTagSetupCommands c : this.setupCmds) {
			sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/tnttag setup " + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()) + ChatColor.DARK_AQUA + c.getDescription());
		}
		sender.sendMessage(ChatColor.AQUA + "=====================================================");
	}
}
