package com.github.lyokofirelyte.WC.Listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.PlayerEmoteEvent;

public class WCEmotes implements Listener {
	
	WCMain plugin;
	public WCEmotes(WCMain instance){
	plugin = instance;
	}
	
	WCPlayer wcp;
	static List<String> emotes;
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onEmote(PlayerEmoteEvent e){
		
		wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
		emotes = plugin.datacore.getStringList("EmotesList");
		List<String> emoteActions = plugin.datacore.getStringList("EmoteActions");
		
		if (!wcp.getEmotes()){
			e.setCancelled(true);
			return;
		}

		e.setSentence(e.getPlayer().getDisplayName() + " &d" + emoteActions.get(emotes.indexOf(e.getEmote())));
		
  		Bukkit.broadcastMessage(Utils.AS(e.getSentence()));
	}


	public static List<String> getEmoteList(){
		return emotes;
	}
}
