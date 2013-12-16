package com.github.lyokofirelyte.WC.Listener;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPatrol;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCQuit implements Listener {
	
  WCMain pl;
  public WCQuit(WCMain instance){
	  this.pl = instance;
  }

  @EventHandler(priority=EventPriority.HIGH)
  public boolean onPlayerQuit(final PlayerQuitEvent e) {
	  
	e.setQuitMessage(null);
	WCPlayer wcp = pl.wcm.getWCPlayer(e.getPlayer().getName());
	
	if (wcp.getPatrol() != null){
		WCPatrol wcpp = pl.wcm.getWCPatrol(wcp.getPatrol());
		List<String> members = wcpp.getMembers();
		members.remove(e.getPlayer().getName());
		wcpp.setMembers(members);
		pl.wcm.updatePatrol(wcp.getPatrol(), wcpp);
	}
	
	wcp.setPatrol(null);
	pl.wcm.updatePlayerMap(e.getPlayer().getName(), wcp);
	
	if (pl.afkers.contains(e.getPlayer())){
		pl.afkers.remove(e.getPlayer());
	}
	  
	try {
		pl.wcm.savePlayer(e.getPlayer().getName());
	} catch (IOException ee) {
		ee.printStackTrace();
	}  

    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable()
    { public void run() {
        Player p = e.getPlayer();
		Bukkit.broadcastMessage(Utils.AS(("&4- " + p.getDisplayName() + " &f(&e" + pl.wcm.getWCPlayer(p.getName()).getQuitMessage() + "&f)").replace("%p", p.getDisplayName() + "&e")));
    } } , 5L);

    return true;
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerKick(PlayerKickEvent event){
    event.setLeaveMessage(null);
  }
  
}