package com.minebone.tnttag.managers;

import java.util.Map;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.minebone.tnttag.core.TNTTag;
import com.minebone.tnttag.util.CreateArenaData;

public class TempArenaDataManager {
	
	private TNTTag plugin;
	private Map<String, CreateArenaData> data;
	
	public TempArenaDataManager(TNTTag plugin) {
		this.plugin = plugin;
		data = Maps.newHashMap();
	}
	
	public CreateArenaData getData(Player player) {
		if(data.get(player.getName()) != null) {
			return data.get(player.getName());
		}
		
		CreateArenaData data1 = new CreateArenaData(plugin, player);
		data.put(player.getName(), data1);
		return data1;
	}
	
	public void removeData(Player player) {
		data.remove(player.getName());
	}

}
