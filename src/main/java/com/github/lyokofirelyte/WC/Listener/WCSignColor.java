package com.github.lyokofirelyte.WC.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.github.lyokofirelyte.WC.WCMain;

public class WCSignColor implements Listener {
	
	WCMain pl;
	
	public WCSignColor(WCMain instance){
		pl = instance;
    }

	@EventHandler
	public void onColor(SignChangeEvent e){
		
		Player p = e.getPlayer();
		List<Integer> toReplace = new ArrayList<Integer>();
		int x = 0;
		
		for (String s : e.getLines()){
			if (s.contains("&") && p.hasPermission("wa.metropolitan")){
				toReplace.add(x);
			}
			x++;
		}
		
		if (toReplace.size() > 0){
			for (int y : toReplace){
				e.setLine(y, ChatColor.translateAlternateColorCodes('&', e.getLine(y)));
			}
		}
	}
}
