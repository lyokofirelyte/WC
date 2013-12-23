package com.github.lyokofirelyte.WC.Listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.PlayerEmoteEvent;

public class WCEmotes implements Listener {
	
	WCMain plugin;
	public WCEmotes(WCMain instance){
	plugin = instance;
	}
	
	WCPlayer wcp;
	WCSystem wcs;
    List<String> emotes;
	List<String> emoteActions;
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onEmote(PlayerEmoteEvent e){
		
		wcs = plugin.wcm.getWCSystem("system");
		emotes = wcs.getEmotes();
		emoteActions = wcs.getEmoteActions();

		e.setSentence(e.getPlayer().getDisplayName() + " &d" + emoteActions.get(emotes.indexOf(e.getEmote())));
		
  		Bukkit.broadcastMessage(Utils.AS(e.getSentence()));
	}

}
