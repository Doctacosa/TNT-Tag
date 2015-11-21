package com.minebone.tnttag.files;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.minebone.tnttag.util.Message;
import com.minebone.tnttag.util.MessageHandler;

public class Messages {
	private static FileConfiguration messages = null;
	private static File messagesFile = null;

	public static void load() {
		messages = getMessages();
		messages.options()
				.header("############################################################\n# +------------------------------------------------------+ #\n# |                   TNT Tag Messages                   | #\n# +------------------------------------------------------+ #\n############################################################");

		getMessages().options().copyDefaults(true);
		messages.addDefault("ADD_DESC", "Add coins, wins, tags, or taggeds to a player.");
		messages.addDefault("FORCE_END_DESC", "Force end a game.");
		messages.addDefault("FORCE_START_DESC", "Force start a game.");
		messages.addDefault("RELOAD_DESC", "Reloads the config.");
		messages.addDefault("REMOVE_DESC", "Remove coins, wins, tags, or taggeds to a player.");
		messages.addDefault("RESET_STATS_DESC", "Reset stats for a player.");
		messages.addDefault("UPDATE_DESC", "Updates the plugin.");
		messages.addDefault("CREATE_ARENA_DESC", "Creates an arena.");
		messages.addDefault("DELETE_ARENA_DESC", "Delete an arena.");
		messages.addDefault("SET_ARENA_POINT_DESC", "Sets the Arena point.");
		messages.addDefault("SET_LOBBY_POINT_DESC", "Sets the TNT Tag lobby point.");
		messages.addDefault("SET_SPECTATORS_POINT_DESC", "Set the spectators point.");
		messages.addDefault("CHECK_STATS_DESC", "Check your stats.");
		messages.addDefault("CHECK_COINS_DESC", "Check your coins.");
		messages.addDefault("JOIN_DESC", "Joins TNT Tag.");
		messages.addDefault("LEAVE_DESC", "Leaves TNT Tag.");
		messages.addDefault("LIST_ARENAS_DESC", "List all the arenas.");
		messages.addDefault("TRANSFER_DESC", "Transfer coins to another player.");
		messages.addDefault("NOW_HAS_COINS", "{player} now has {amount} coins.");
		messages.addDefault("NOW_HAS_WINS", "{player} now has {amount} wins.");
		messages.addDefault("NOW_HAS_TAGS", "{player} now has {amount} tags.");
		messages.addDefault("NOW_HAS_TAGGEDS", "{player} now has {amount} taggeds.");
		messages.addDefault("AVAILABLE_ARENAS", "Available Arenas: ");
		messages.addDefault("INVALID_ARENA", "That is not a valid arena!");
		messages.addDefault("RELOAD_COMPLETE", "Reloaded plugin.");
		messages.addDefault("THERE_WAS_A_RELOAD", "There was a reload");
		messages.addDefault("ARENA_CREATED", "Arena {arena} has been created!");
		messages.addDefault("ARENA_DELETED", "Arena {arena} has been deleted!");
		messages.addDefault("UNSPECIFIED_ARENA", "You must specify which arena to delete.");
		messages.addDefault("CONFIRMATION_MESSAGE", "Type /tnttag setup deletearena confirm to confirm this action.%newlineThis cannot be undone.%newlineThe confirmation will expire in 10 seconds.");
		messages.addDefault("STATS", "Coins: {money}%newlinePlayers tagged: {tags}%newlineTimes tagged: {taggeds}%newlineWins: {wins}");
		messages.addDefault("COINS_CHECK", "Coins: {amount}%newlineUse /tag transfer coins <player> <amount> to transfer coins to another player.");
		messages.addDefault("NOT_IN_ARENA", "You must be in an arena in order to leave TNT Tag!");
		messages.addDefault("LEAVE_CURRENT_ARENA", "You must first leave the arena you are currently in.");
		messages.addDefault("COMMAND_ERROR", "You cannot perform {command} while playing TNT Tag");
		messages.addDefault("JOINED_FROM_BED", "Don't join from a bed.");
		messages.addDefault("ARENA_ALREADY_STARTED", "The game in the arena you are looking for has already started!");
		messages.addDefault("FULL_ARENA", "The arena you are looking for is currently full!");
		messages.addDefault("JOINED_GAME", "{player} &ejoined the game (&d{size}&e/&d{max_players}&e)");
		messages.addDefault("ARENA_NOT_FOUND", "The arena you are looking for could not be found!");
		messages.addDefault("FORCE_STARTING", "Force starting the game...");
		messages.addDefault("ARENA_STARTED_WHEN_FORCE_START", "The arena has already started!");
		messages.addDefault("MINIMUM_REQUIRED_NOT_REACHED", "Minimum players must be 2!");
		messages.addDefault("FORCE_ENDING", "Force ending the game...");
		messages.addDefault("FORCE_END_KICKED", "The arena has been force to an end by an admin.");
		messages.addDefault("FORCE_START_ERROR", "The arena hasn't started!");
		messages.addDefault("SECONDS_UNTIL_GAME_STARTS", "&e{time} seconds until the game starts!");
		messages.addDefault("SECOND_UNTIL_GAME_STARTS", "&e{time} second until the game starts!");
		messages.addDefault("TNT_RELEASED", "&eThe TNT has been released!");
		messages.addDefault("EARNED_50_COINS_BONUS", "&6You earned a total of 50 Coins!");
		messages.addDefault("LINE_BREAK", "&6#&7------------------&6#");
		messages.addDefault("PLAYER_BLEW_UP", "{player} &eblew up!");
		messages.addDefault("ROUND_ENDED", "&eRound Ended");
		messages.addDefault("PLAYER_IS_IT", "{player} &eis 'it'");
		messages.addDefault("ARENA_TEMP_SAVED", "Arena location temporarily saved.");
		messages.addDefault("LOBBY_TEMP_SAVED", "Lobby location temporarily saved.");
		messages.addDefault("SPECTATOR_TEMP_SAVED", "Spectator location temporarily saved.");
		messages.addDefault("MISSING_LOBBY", "Lobby Location is missing.");
		messages.addDefault("MISSING_ARENA", "Arena Location is missing.");
		messages.addDefault("MISSING_SPECTATOR", "Spectators Location is missing.");
		messages.addDefault("WIN_MESSAGE", "{player} won at TNT Tag!");
		messages.addDefault("INVALID_NUMBER", "Invalid number.");
		messages.addDefault("CREATE_SIGN_DESC", "Create a sign for a specific arena.");
		messages.addDefault("CLICK_TO_CREATE_SIGN", "Punch any sign to create a join sign for the Arena {arena}.%newlineThis will expire in 60 seconds.");
		messages.addDefault("SIGN_ALREADY_EXISTS", "The sign you punched, is already registered.");
		messages.addDefault("SIGN_CREATED", "Sign successfully created.");
		save();
	}

	public static void reload() {
		if (messagesFile == null) {
			messagesFile = new File("plugins/TNTTag/messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
	}

	public static FileConfiguration getMessages() {
		if (messages == null) {
			reload();
		}
		return messages;
	}

	public static void save() {
		if ((messages == null) || (messagesFile == null)) {
			return;
		}
		try {
			messages.save(messagesFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save configFile to " + messagesFile, ex);
		}
	}

	public static String getMessage(Message message) {
		return MessageHandler.getMessage(message);
	}
}
