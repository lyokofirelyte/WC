package com.github.lyokofirelyte.WC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCExpSystem implements Listener {
	
      WCMain plugin;
	  public WCExpSystem(WCMain instance){
	  this.plugin = instance;
	  }
	  
	  WCPlayer wcp;
	  
	  @EventHandler (priority = EventPriority.NORMAL)
	  public void onExpGain(final PlayerExpChangeEvent e){
		  
		  if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".expDeposit") == false){
		  
		  Player p = e.getPlayer();
		  wcp = plugin.wcm.getWCPlayer(p.getName());
		  int expAmount = e.getAmount();
		  int storedAmount = wcp.getExp();
		  wcp.setExp(storedAmount + expAmount);
		  e.setAmount(0);
		  
		  	if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".expWarn") == false){
		  		p.sendMessage(WCMail.WC + "Your xp has been stored in /wc xp. (Toggle this warning with /wc xp toggle)");
		  	}
	  }
	  	}
	  
	  
}
