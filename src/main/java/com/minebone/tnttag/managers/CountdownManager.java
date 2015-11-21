package com.minebone.tnttag.managers;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.files.Config;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.util.Arena;
import com.minebone.tnttag.util.Message;

public class CountdownManager {

	public static void startGame(int seconds1, final Arena arena) {
		if (!arena.runningCountdown() && arena.getPlayers().size() >= arena.getMinPlayers()) {
			arena.setSeconds(seconds1);
			arena.setRunningCountdown(Boolean.valueOf(true));
			arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TNTTag.main, new Runnable() {
						@SuppressWarnings("deprecation")
						public void run() {
							if (CountdownManager.timesToBroadcast.contains(Integer.valueOf(arena.getSeconds())))
								if (arena.getSeconds() == 1){
									MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.secondCountdown).replace("{time}",(new StringBuilder(String.valueOf(arena.getSeconds()))).toString()), arena);
								}else{
									MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.secondsCountdown).replace("{time}",(new StringBuilder(String.valueOf(arena.getSeconds()))).toString()), arena);
								}
							Player player;
							for (String s : arena.getPlayers()) {
								player = Bukkit.getPlayer(s);
								arena.setBoard(player, arena.getSeconds());
								player.setLevel(arena.getSeconds());
							}

							if (arena.getSeconds() == 0) {
								Bukkit.getScheduler().cancelTask(arena.getTaskID());
								arena.setInGame(true);
								Location loc = arena.getArenaLocation();
								for (String s :  arena.getPlayers()) {
									player = Bukkit.getPlayer(s);
									player.teleport(loc);
								}

								MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.TNTReleased), arena);
								CountdownManager.pickRandomTNT(arena);
								CountdownManager.startRound(arena);
							}
							int seconds = arena.getSeconds();
							arena.setSeconds(seconds - 1);
						}
					}, 20L, 20L));
		}
	}

	protected static void startRound(final Arena arena) {
		arena.setSeconds(arena.getPlayers().size() <= 6 ? 30 : 50);
		arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TNTTag.main, new Runnable() {

					@SuppressWarnings("deprecation")
					public void run() {
						Player player;
						for (String p : arena.getPlayers()) {
							player = Bukkit.getPlayer(p);
							arena.setBoard(player, arena.getSeconds());
							player.setLevel(arena.getSeconds());
						}

						if (arena.getSeconds() == 0) {
							Bukkit.getScheduler().cancelTask(arena.getTaskID());
					          if (arena.getPlayers().size() == 2) {
					            Player player1 = null;
					            for (String s : arena.getAlivePlayers()) {
					              player1 = Bukkit.getPlayer(s);
					            }
					            Player player2 = null;
					            for (String s : arena.getTNTPlayers()) {
					              player2 = Bukkit.getPlayer(s);
					            }
					            blowUpTNTs(arena);
					            
					            MessageManager.getInstance().sendWinMessage(player2, player1.getName(), arena);
					            
					            arena.setRunningCountdown(false);
					            for (String s : arena.getPlayers()) {
					              Player player11 = Bukkit.getPlayer(s);
					              
					              int wins = FileManager.getInstance().getPlayerData().getInt(s + ".wins");
					              FileManager.getInstance().getPlayerData().set(s + ".wins", Integer.valueOf(wins + 1));
					              
					              int money = FileManager.getInstance().getPlayerData().getInt(s + ".money");
					              FileManager.getInstance().getPlayerData().set(s + ".money", Integer.valueOf(money + 50));
					              
					              MessageManager.getInstance().sendNoPrefixMessage(player11, Messages.getMessage(Message.coinsBonus));
					              MessageManager.getInstance().sendNoPrefixMessage(player11, Messages.getMessage(Message.lineBreak));
					              Config.executeGameWinCommand(player11);
					              arena.getAlivePlayers().remove(player11.getName());
					              
					              ArenaManager.getManager().removePlayer(player11);
					              if (arena.getPlayers().size() == 0) {
					                arena.setInGame(false);
					                
					                return;
					              }
					            }
					          
							} else if (arena.getPlayers().size() <= 6) {
								CountdownManager.blowUpTNTs(arena);
								CountdownManager.startDelayedRound(arena);
							} else {
								CountdownManager.blowUpTNTs(arena);
								CountdownManager.startDelayedRound(arena);
							}
						}
						int seconds = arena.getSeconds();
						arena.setSeconds(seconds - 1);
					}
				}, 20L, 20L));
	}

	protected static void startDelayedRound(final Arena arena) {
		arena.setSeconds(5);
		arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(TNTTag.main, new Runnable() {

					@SuppressWarnings("deprecation")
					public void run() {
						if (arena.getSeconds() == 0) {
							Bukkit.getScheduler().cancelTask(arena.getTaskID());
							CountdownManager.startRound(arena);
					          Location loc = arena.getArenaLocation();
					          for (String p : arena.getPlayers()) {
					            Player player = Bukkit.getPlayer(p);
					            player.teleport(loc);
					          }
							MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.TNTReleased), arena);
							CountdownManager.pickRandomTNT(arena);
						}
						int seconds = arena.getSeconds();
						arena.setSeconds(seconds - 1);
					}
				}, 20L, 20L));
	}

	@SuppressWarnings("deprecation")
	public static void blowUpTNTs(Arena arena) {
		for (String s : arena.getTNTPlayers()){
	      Player TNTplayer = Bukkit.getPlayer(s);
	      
	      World world = TNTplayer.getWorld();
	      
	      world.createExplosion(TNTplayer.getLocation(), 0.0F);
	      
	      MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.playerBlewUp).replace("{player}", TNTplayer.getName()), arena);
	      
	      TNTplayer.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
	      TNTplayer.getInventory().setItem(1, new ItemStack(Material.AIR, 1));
	      
	      arena.getTNTPlayers().remove(s);
	      arena.getPlayers().remove(s);
	      arena.removeBoard(TNTplayer);
	      InventoryManager.restoreInventory(Bukkit.getPlayer(s));
	      if (arena.getTNTPlayers().size() == 0){
	        finishBlowingUp(arena);
	        return;
	      }
	    }

	}

	private static void finishBlowingUp(Arena arena) {
	    MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.roundEnded), arena);
	    for (String p : arena.getPlayers()){
	      @SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(p);
	      Config.executeRoundWinCommand(player);
	      player.sendMessage(ChatColor.GOLD + "+2 coins!");
	      int money = FileManager.getInstance().getPlayerData().getInt(p + ".money");
	      FileManager.getInstance().getPlayerData().set(p + ".money", Integer.valueOf(money + 2));
	      for (PotionEffect effect : player.getActivePotionEffects()) {
	        player.removePotionEffect(effect.getType());
	      }
	      player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, Config.getSpeed(Config.PlayerType.Player).intValue()));
	      FileManager.getInstance().saveConfig();
	    }

	}

	public static void pickRandomTNT(Arena arena) {
	    int playerThatWillBePicked = arena.getPlayers().size() >= 6 ? arena.getPlayers().size() / 2 : 1;
	    while (playerThatWillBePicked != 0) {
	      pickPlayers(arena);
	      playerThatWillBePicked--;
	    }
	    for (String s : arena.getTNTPlayers()) {
	      MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.playerIsIt).replace("{player}", s), arena);
	    }
	}

	@SuppressWarnings("deprecation")
	private static void pickPlayers(Arena arena) {
	    Random random = new Random();
	    
	    String[] players = new String[arena.getAlivePlayers().size()];
	    int i = 0;
	    for (String s : arena.getAlivePlayers()) {
	      players[i] = s;
	      i++;
	    }
	    int randomInt = 0;
	    randomInt = random.nextInt(players.length);
	    arena.getTNTPlayers().add(players[randomInt]);
	    arena.getAlivePlayers().remove(players[randomInt]);
	    if (players[randomInt] != null) {
	      Player player = Bukkit.getPlayer(players[randomInt]);
	      player.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
	      player.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
	      for (PotionEffect effect : player.getActivePotionEffects()) {
	        player.removePotionEffect(effect.getType());
	      }
	      player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, Config.getSpeed(Config.PlayerType.TNT).intValue()));
	    }
	    players = null;
	    if (arena.getTNTPlayers().contains(null)) {
	      arena.getTNTPlayers().remove(null);
	    }
	}

	public static void cancelTask(Arena arena) {
		Bukkit.getScheduler().cancelTask(arena.getTaskID());
		arena.setRunningCountdown(Boolean.valueOf(false));
		arena.setInGame(false);
	}

	static final List<Integer> timesToBroadcast = FileManager.getInstance().getConfig().getIntegerList("BroadcastTimes");

}
