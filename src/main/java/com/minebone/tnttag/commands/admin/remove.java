package com.minebone.tnttag.commands.admin;

import org.bukkit.command.CommandSender;

import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.AbstractTagAdminCommands;
import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.Permissions;

public class remove extends AbstractTagAdminCommands {
	int amount;

	public remove() {
		super("remove", Messages.getMessage(Message.remove), "<coins|wins|tags|taggeds> <player>", new Permissions().remove, true);
	}

	public void onCommand(CommandSender sender, String[] args){
    if ((args.length != 0) && (args.length != 1)){
      if (getPlayerData().getString(args[0]) != null){
        String player = args[0];
	        try {
	          this.amount = Integer.parseInt(args[1]);
	        }
	        catch (NumberFormatException e){
	          MessageManager.getInstance().sendErrorMessage(sender, Messages.getMessage(Message.invalidNumber));
	          return;
	        }
	        switch(args[1]){
	        case "coins":
	        	int coins = getPlayerData().getInt(player + ".money");
	            getPlayerData().set(player + ".money", Integer.valueOf(coins - this.amount));
	            MessageManager.getInstance().sendMessage(sender, Messages.getMessage(Message.nowHasCoins).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".money").toString()) + "").replace("{player}", player));
	        	break;
	        case "wins":
	        	int wins = getPlayerData().getInt(player + ".wins");
	            getPlayerData().set(player + ".wins", Integer.valueOf(wins - this.amount));
	            MessageManager.getInstance().sendMessage(sender, Messages.getMessage(Message.nowHasWins).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".wins").toString()) + "").replace("{player}", player));
	        	break;
	        case "tags":
	        	int tags = getPlayerData().getInt(player + ".tags");
	            getPlayerData().set(player + ".tags", Integer.valueOf(tags - this.amount));
	            MessageManager.getInstance().sendMessage(sender, Messages.getMessage(Message.nowHasTags).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".tags").toString()) + "").replace("{player}", player));
	        	break;
	        case "taggeds":
	        	int taggeds = getPlayerData().getInt(player + ".taggeds");
	            getPlayerData().set(player + ".taggeds", Integer.valueOf(taggeds - this.amount));
	            MessageManager.getInstance().sendMessage(sender, Messages.getMessage(Message.nowHasTaggeds).replace("{amount}", getPlayerData().getInt(new StringBuilder(String.valueOf(player)).append(".taggeds").toString()) + "").replace("{player}", player));
	        	break;
	        default:
	            MessageManager.getInstance().sendInvalidArgs(sender, "add", "<coins|wins|tags|taggeds> <player>");
	            break;
	        }
	        } else {
	        MessageManager.getInstance().sendErrorMessage(sender, "Player " + args[0] + " could not be find.");
	      }
	    }else {
	      MessageManager.getInstance().sendInsuficientArgs(sender, "add", "<coins|wins|tags|taggeds> <player>");
	    }
	}
	
}
