package com.minebone.tnttag.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.AbstractTagCommands;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class join extends AbstractTagCommands {
	public join() {
		super("join", Messages.getMessage(Message.join), "<ArenaName>", new Permissions().join, true, "j");
	}

	public void onCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			MessageManager.getInstance().sendInsuficientArgs(sender, "join", "<ArenaName>");

			StringBuilder arenaNames = new StringBuilder(Messages.getMessage(Message.availableArenas));

			int x = 0;
			for (Arena arena : Arena.arenaObjects) {
				x++;
				arenaNames.append(arena.getName() + (x != Arena.arenaObjects.size() ? ", " : "."));
			}
			MessageManager.getInstance().sendMessage(sender, arenaNames.toString());

			return;
		}
		String arenaName = args[0];
		if (!ArenaManager.getManager().isInGame(player)) {
			ArenaManager.getManager().addPlayers(player, arenaName);
		} else {
			MessageManager.getInstance().sendErrorMessage(sender, Messages.getMessage(Message.leaveCurrentArena));
		}
	}
}
