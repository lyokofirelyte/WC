package com.github.lyokofirelyte.WC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCExpSystem implements Listener {
	
      WCMain plugin;
	  public WCExpSystem(WCMain instance){
	  this.plugin = instance;
	  }
	  
	  WCPlayer wcp;
	  
	  @EventHandler (priority = EventPriority.NORMAL)
	  public void onExpGain(final PlayerExpChangeEvent e){
		  
		  Player p = e.getPlayer();
		  wcp = plugin.wcm.getWCPlayer(p.getName());
		  
		  if (!wcp.getExpDeposit()){

		  int expAmount = e.getAmount();
		  int storedAmount = wcp.getExp();
		  wcp.setExp(storedAmount + expAmount);
		  updatePlayer(wcp, p.getName());
		  e.setAmount(0);
		  
		  }
	  }
	  
	  public void updatePlayer(WCPlayer wcp, String name){
		  plugin.wcm.updatePlayerMap(name, wcp);  
		  wcp = plugin.wcm.getWCPlayer(name);
	  }
}
