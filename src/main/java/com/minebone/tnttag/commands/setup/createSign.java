package com.minebone.tnttag.commands.setup;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagSetupCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class createSign extends AbstractTagSetupCommands {

	public createSign(TNTTag plugin) {
		super(plugin, "createsign", Messages.getMessage(Message.createSign), "<arena>", new Permissions().createSign, true);
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "createsign", "<arena>");
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}

			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		String arenaName = args[0];
		if (getArenaManager().getArena(arenaName) == null) {
			getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.arenaNotFound));
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}

			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		getPlugin().getSignManager().tempSign.put(player.getName(), arenaName);
		getMessageManager().sendMessage(sender, Messages.getMessage(Message.clickToCreateSign).replace("{arena}", arenaName));
		addToTempList(player);
	}

	private void addToTempList(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable() {
			public void run() {
				if (getPlugin().getSignManager().tempSign.containsKey(player.getName())) {
					getPlugin().getSignManager().tempSign.remove(player.getName());
				}
			}
		}, 1200L);
	}
}
