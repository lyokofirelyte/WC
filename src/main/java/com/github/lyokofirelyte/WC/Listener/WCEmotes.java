package com.github.lyokofirelyte.WC.Listener;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Events.PlayerEmoteEvent;
import com.github.lyokofirelyte.WCAPI.Manager.WCMessageType;

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
		
		e.setSentence(e.getSentence().replaceFirst("@" + e.getEmote(), "").replaceFirst(" ", ""));
		System.out.println("'" + e.getSentence() + "'");
		String[] args = e.getSentence().split(" ");
		
		String[] emoteAction = emoteActions.get(emotes.indexOf(e.getEmote())).split(" %s% ");
		String action = emoteAction[0];
		
		if (args[0].equalsIgnoreCase("") || emoteAction.length == 1){
			
			e.setSentence(e.getPlayer().getDisplayName() + " &d" + action);
	  		WCUtils.callChat(WCMessageType.BROADCAST, WCUtils.AS(e.getSentence()));
			
		} else {
			
			String message = WCUtils.createString(args, 0);
			action = emoteAction[1];
			
			e.setSentence(e.getPlayer().getDisplayName() + " &d" + action.replace("%a", "&7" + message + "&d"));
			WCUtils.callChat(WCMessageType.BROADCAST, WCUtils.AS(e.getSentence()));
			
		}
		
		
	}

}
