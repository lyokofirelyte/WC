package com.github.lyokofirelyte.WC.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCTags implements Listener {

	WCMain plugin;
	WCPlayer wcp;
	WCAlliance wca;
	
	String completed;
	
    public WCTags(WCMain instance){
    plugin = instance;
	}
    
    @EventHandler (priority = EventPriority.NORMAL)
    public void onTag(PlayerReceiveNameTagEvent e){
    	wcp = plugin.wcm.getWCPlayer(e.getNamedPlayer().getName());
    	wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
    	
    	if (wcp.getNamePlate()){
    	
	    	if (wca.equals("ForeverAlone")){
	    		completed = "§7" + wcp.getNick();
	    	} else {
	    		completed = plugin.wcm.getCompleted2(e.getNamedPlayer().getName(), wca.getColor1(), wca.getColor2());
	    	}
	    	
	    	e.setTag(completed);
    	} else {
    		e.setTag(e.getNamedPlayer().getName());
    	}
    }
}
