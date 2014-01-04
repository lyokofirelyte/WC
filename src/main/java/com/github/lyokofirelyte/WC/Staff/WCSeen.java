package com.github.lyokofirelyte.WC.Staff;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;

public class WCSeen implements Listener {

	 WCMain pl;
	 public WCSeen(WCMain instance){
	 this.pl = instance;
	 }

	 @WCCommand(aliases = {"seen"}, desc = "Last login information of players",help = "/seen <playername>", min = 1, max = 1, perm = "wa.settler")
	 public void onSeen(Player sender, String[] args){

			 
			 Player p = ((Player)sender);
			 
			 if (args.length == 0){
				 s(p, "/seen <player>");
				 return;
			 }
			 
			 if (pl.wcm.getWCPlayer(args[0]) == null){
				 s(p, "That player can't be found.");
				 return;
			 }
			 
			 WCPlayer wcpCurrent = pl.wcm.getWCPlayer(args[0]);
			 
			 s(p, "Information for &6" + pl.wcm.getFullNick(args[0]));
			 s2(p, "&f>> &dLast Login: &6" + Math.round(((System.currentTimeMillis() / 1000L) - wcpCurrent.getLastLogin()) / 60) + " &dminutes ago, or &6" + Math.round((((System.currentTimeMillis() / 1000L) - wcpCurrent.getLastLogin()) / 60) / 60) + " &dhours ago");
			 s2(p, "&f>> &dLogoff Location: " + wcpCurrent.getLastLoginLocation());
			 if (Bukkit.getPlayer(args[0]) != null){
				 s2(p, "&2>> &dCurrently: &aonline");
			 } else {
				 s2(p, "&4>> &dCurrently: &coffline");
			 }
	 }
	 
	 @EventHandler
	 public void onQuit(PlayerQuitEvent e){
		 
		 WCPlayer wcp = pl.wcm.getWCPlayer(e.getPlayer().getName());
		 Player p = e.getPlayer();
		 wcp.setLastLoginLocation("&6" + p.getWorld().getName() + " &6@ " + Math.floor(p.getLocation().getX()) + "&6, " + Math.floor(p.getLocation().getY()) + "&6, " + Math.floor(p.getLocation().getZ()));
		 wcp.setLastLogin(System.currentTimeMillis() / 1000L);
	 }
	 
}
