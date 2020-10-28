package com.minebone.tnttag.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class join extends AbstractTagCommands {

	public join(TNTTag plugin) {
		super(plugin, "join", Messages.getMessage(Message.join), "<ArenaName>", new Permissions().join, true, "j");
	}

	public void onCommand(CommandSender sender, String[] args) {
		
		if (args.length == 0) {
			getMessageManager().sendInsuficientArgs(sender, "join", "<ArenaName>");
			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}
			getMessageManager().sendMessage(sender, arenaNames.toString());
			return;
		}

		Player target = null;
		if (args.length >= 2) {
			target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				getMessageManager().sendErrorMessage(sender, "Player not found!");
				return;
			}
		} else if (sender instanceof Player) {
			target = (Player)sender;
		} else {
			getMessageManager().sendErrorMessage(sender, "Player not found!");
			return;
		}

		String arenaName = args[0];
		if (!getArenaManager().isInGame(target)) {
			getArenaManager().addPlayers(target, arenaName);
			return;
		}

		getMessageManager().sendErrorMessage(sender, Messages.getMessage(Message.leaveCurrentArena));
	}
}
