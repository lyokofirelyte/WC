package com.github.lyokofirelyte.WC.Listener;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.lyokofirelyte.WC.WCMain;

public class WCQuit implements Listener {
	
  WCMain plugin;
  public WCQuit(WCMain instance){
  this.plugin = instance;
  }

  @EventHandler(priority=EventPriority.HIGH)
  public boolean onPlayerQuit(final PlayerQuitEvent e) {
	  
	e.setQuitMessage(null);
	  
	try {
		plugin.wcm.savePlayer(e.getPlayer().getName());
	} catch (IOException ee) {
		ee.printStackTrace();
	}  
	    
    List <String> quitMessages = this.plugin.config.getStringList("Core.QuitMessages");
    
    Random rand = new Random();
    int randomNumber = rand.nextInt(quitMessages.size());
    final String quitMessage = (String)quitMessages.get(randomNumber);

    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    { public void run() {
        Player p = e.getPlayer();
        Bukkit.broadcastMessage("§e" + quitMessage.replace("%p", new StringBuilder("§7").append(p.getDisplayName()).append("§e").toString()).replace(" ", " §e"));
    } } , 5L);

    return true;
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerKick(PlayerKickEvent event){
    event.setLeaveMessage(null);
  }
  

}