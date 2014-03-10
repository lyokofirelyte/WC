package com.github.lyokofirelyte.WC.Listener;

import java.util.ArrayList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WC.WCMain;

public class WCBlockPlace implements Listener{
	
	WCMain plugin;
	WCSystem system;
	WCPlayer wcp;
	
	ArrayList<String> lore;
	
	public WCBlockPlace(WCMain instance){
    plugin = instance;
    } 

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e){
	
		if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode")){
	
			plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskPlaceMode", false);
			double x = e.getBlock().getLocation().getX();
			double y = e.getBlock().getLocation().getY();
			double z = e.getBlock().getLocation().getZ();
			
			WCSystem system = plugin.wcm.getWCSystem("system");
			
			system.addObelisk(plugin.datacore.getString("Obelisks.Latest") + " " + x + " " + y + " " + z);
			plugin.wcm.updateSystem("system", system);
			WCUtils.s(e.getPlayer(), "Added!");
			return;
		}
		
	}
}

