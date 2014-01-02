package com.github.lyokofirelyte.WC.Listener;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.bobacadodl.JSONChatLib.JSONChatColor;
import com.bobacadodl.JSONChatLib.JSONChatExtra;
import com.bobacadodl.JSONChatLib.JSONChatFormat;
import com.bobacadodl.JSONChatLib.JSONChatHoverEventType;
import com.bobacadodl.JSONChatLib.JSONChatMessage;
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

		updatePlayer(wcp, p.getName());
	
        JSONChatMessage message = new JSONChatMessage("Welcome. We are current running ", JSONChatColor.GOLD, null);
        JSONChatExtra extra = new JSONChatExtra("WaterCloset v5", JSONChatColor.DARK_PURPLE, Arrays.asList(JSONChatFormat.BOLD));
        extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, Utils.AS("&6The core plugin of Worlds Apart!"));
        message.addExtra(extra);
        message.sendToPlayer(event.getPlayer());
		return true;
  	}

	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
}