package com.minebone.tnttag.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.minebone.tnttag.api.PlayerTagEvent;
import com.minebone.tnttag.files.Config;
import com.minebone.tnttag.files.Messages;
import com.minebone.tnttag.managers.ArenaManager;
import com.minebone.tnttag.managers.FileManager;
import com.minebone.tnttag.managers.MessageManager;
import com.minebone.tnttag.util.FireworkEffectPlayer;
import com.minebone.tnttag.util.Message;

public class EntityDamageByEntityListener implements Listener {
	FireworkEffectPlayer fplayer = new FireworkEffectPlayer();

	@EventHandler(priority = EventPriority.NORMAL)
	public void dmg(EntityDamageByEntityEvent event) throws Exception {
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player damager = (Player) event.getDamager();
			Player victim = (Player) event.getEntity();
			if (ArenaManager.getManager().isInGame(victim)) {
				if (ArenaManager.getManager().isTNT(damager)) {
					ArenaManager.getManager().addTNTPlayer(victim);
					ArenaManager.getManager().removeTNTPlayer(damager);

					MessageManager.getInstance().sendInGamePlayersMessage(Messages.getMessage(Message.playerIsIt).replace( "{player}", victim.getName()), ArenaManager.get(victim));

					Bukkit.getServer().getPluginManager()
							.callEvent(new PlayerTagEvent(damager, victim));

					int tags = FileManager.getInstance().getPlayerData().getInt(damager.getName() + ".tags");
					int taggeds = FileManager.getInstance().getPlayerData().getInt(victim.getName() + ".taggeds");

					FileManager.getInstance().getPlayerData().set(damager.getName() + ".tags", Integer.valueOf(tags + 1));
					FileManager.getInstance().getPlayerData().set(victim.getName() + ".taggeds", Integer.valueOf(taggeds + 1));
					
					for (PotionEffect effect : damager.getActivePotionEffects()) {
						damager.removePotionEffect(effect.getType());
					}
					
					for (PotionEffect effect : victim.getActivePotionEffects()) {
						victim.removePotionEffect(effect.getType());
					}
					
					victim.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, Config.getSpeed(Config.PlayerType.TNT).intValue()));
					damager.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, Config.getSpeed(Config.PlayerType.Player).intValue()));

					victim.getInventory().setHelmet(new ItemStack(Material.TNT, 1));
					damager.getInventory().setHelmet(new ItemStack(Material.AIR, 1));

					damager.getInventory().setItem(0, new ItemStack(Material.AIR, 1));
					victim.getInventory().setItem(0, new ItemStack(Material.TNT, 1));

					FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.CREEPER).build();
					this.fplayer.playFirework(victim.getWorld(), victim.getLocation(), effect);

					victim.setHealth(20.0D);
				} else {
					victim.setHealth(20.0D);
				}
			}
		}
	}
}
