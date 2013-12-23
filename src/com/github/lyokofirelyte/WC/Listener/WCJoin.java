package com.github.lyokofirelyte.WC.Listener;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

public class WCJoin implements Listener {
  
WCMain plugin;
String completed;
WCPlayer wcp;
WCAlliance wca;

	public WCJoin(WCMain instance){
		plugin = instance;
	}

	@EventHandler(priority=EventPriority.NORMAL)
 	public boolean onPlayerJoin(final PlayerJoinEvent event) {
	  
		final Player p = event.getPlayer();
		event.setJoinMessage(null);

		plugin.wcm.userCreate(p);
		wcp = plugin.wcm.getWCPlayer(p.getName());
		wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
		
		p.setFlying(false);
		p.setAllowFlight(false);
	
		if (!wcp.getInAlliance()) {
			wcp.setAllianceRank("Guest");
		}
		
		plugin.afkTimer.put(p.getName(), 0);
		
		if (plugin.wcm.getWCAlliance(wcp.getAlliance()) == null){
			wcp.setAlliance("ForeverAlone");
			wcp.setInAlliance(false);
			wcp.setAllianceRank("Guest");
			updatePlayer(wcp, p.getName());
		}
	    
		if (wcp.getJoinMessage() == null){
			wcp.setJoinMessage("&eJoined!");
		}
		
		if (wcp.getQuitMessage() == null){
			wcp.setQuitMessage("&eLeft!");
		}
		
		if (wcp.getInAlliance()){
			completed = plugin.wcm.getCompleted(wcp.getNick(), wca.getColor1(), wca.getColor2());
		} else {
			completed = "&7" + wcp.getNick();
		}
	
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
		{ public void run(){
			p.setDisplayName(completed);
			if (completed.length() > 16){
				p.setPlayerListName(Utils.AS(completed).substring(0, 16));
			} else {
				p.setPlayerListName(Utils.AS(completed));
			}
			Bukkit.broadcastMessage(Utils.AS("&2+ " + p.getDisplayName() + " &f(&e" + wcp.getJoinMessage().replace("%p", p.getDisplayName() + "&e") + "&f)"));
		}
		}
		, 20L);

		if (!wcp.getTransfered()){
			File essFile = new File("./plugins/Essentials/userdata/" + p.getName().toLowerCase() + ".yml");
			YamlConfiguration essYaml = YamlConfiguration.loadConfiguration(essFile);
			String oldCash = essYaml.getString("money");
			int a = (int) Math.round(Double.parseDouble(oldCash));
			wcp.setBalance(a);
			wcp.setTransfered(true);
			WCMain.s(p, "&aWCV5 IS HERE! VIEW NEW FEATURES & FIXES AT OHSOTOTES.COM!");
		}
		
		updatePlayer(wcp, p.getName());
	
		WCMain.s(p, "Welcome, " + p.getDisplayName() + "&d! We are currently running &6WaterCloset v5.0 &dby Hugs.");
	
		return true;
  	}

	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
}