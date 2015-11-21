package com.minebone.tnttag.managers;

import org.bukkit.plugin.PluginManager;

import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.listeners.BlockBreakListener;
import com.minebone.tnttag.listeners.DropItemListener;
import com.minebone.tnttag.listeners.EntityDamageByEntityListener;
import com.minebone.tnttag.listeners.EntityDamageListener;
import com.minebone.tnttag.listeners.FoodLevelChangeListener;
import com.minebone.tnttag.listeners.InventoryClickListener;
import com.minebone.tnttag.listeners.PlayerCommandPreprocessListener;
import com.minebone.tnttag.listeners.PlayerInteractListener;
import com.minebone.tnttag.listeners.PlayerKickListener;
import com.minebone.tnttag.listeners.PlayerQuitListener;

public class ListenerManager {
	
	public static void registerEvents(TNTTag plugin) {
		PluginManager pm = plugin.getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(plugin), plugin);
		pm.registerEvents(new DropItemListener(plugin), plugin);
		pm.registerEvents(new EntityDamageByEntityListener(plugin), plugin);
		pm.registerEvents(new EntityDamageListener(plugin), plugin);
		pm.registerEvents(new FoodLevelChangeListener(plugin), plugin);
		pm.registerEvents(new InventoryClickListener(plugin), plugin);
		pm.registerEvents(new PlayerCommandPreprocessListener(plugin), plugin);
		pm.registerEvents(new PlayerInteractListener(plugin), plugin);
		pm.registerEvents(new PlayerQuitListener(plugin), plugin);
		pm.registerEvents(new PlayerKickListener(plugin), plugin);
	}
}
