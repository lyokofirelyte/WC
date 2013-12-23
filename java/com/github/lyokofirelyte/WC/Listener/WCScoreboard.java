package com.github.lyokofirelyte.WC.Listener;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

public class WCScoreboard implements Listener {
	
	WCMain pl;
	WCPlayer wcp;
	WCAlliance wca;
	WCSystem wcs;
	Boolean rebooted = false;
	int timeLeft = 300;
	
	public WCScoreboard(WCMain instance){
	   pl = instance;
    } 
	
	private void warning(){
		for (Player p : Bukkit.getOnlinePlayers()){
			p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName("§4REBOOT: §c" + timeLeft);
		}
		timeLeft--;
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onScoreBoard(ScoreboardUpdateEvent e){
		
	     final Player p = e.getPlayer();
		 wcp = pl.wcm.getWCPlayer(p.getName());
		 wca = pl.wcm.getWCAlliance(wcp.getAlliance());
		 wcs = pl.wcm.getWCSystem("system");
		 Objective o1 = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		 
		 if (wcp == null){
			 pl.wcm.setupPlayer(e.getPlayer().getName());
			 wcp = pl.wcm.getWCPlayer(p.getName());
		 }
		 
		 if (wcs.isRebooting()){
			 if (o1 == null){
				 ScoreboardManager manager = Bukkit.getScoreboardManager();
				 Scoreboard board = manager.getNewScoreboard();	
				 Objective o2 = board.registerNewObjective("wa", "dummy");
				 o2.setDisplaySlot(DisplaySlot.SIDEBAR);
				 o2.setDisplayName("§4REBOOT INITIATED");
				 p.setScoreboard(board);
			 }
			 
			 if (!rebooted){
				 Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
				 public void run() { warning();} }, 0L, 20L);
				 rebooted = true;
			 }
			 
			 return;
		 }
		 
		 if (o1 != null && wcs.getPatrolCrystal() != null && !wcs.getPatrolCrystal().isDead() && wcs.getPatrolHotSpotParticipants().contains(e.getPlayer().getName())){
			 return;
		 }
		 
		 if (o1 != null && wcp.getScoreboard()){
			 
			if (pl.afkers.contains(p)){
				o1.setDisplayName(Utils.AS("&7[afk]"));
			}
			 
			if (!wcp.getScoreboardCoords() && !pl.afkers.contains(p)){
			 
				if (p.getDisplayName().length() > 16){
					o1.setDisplayName(Utils.AS(p.getDisplayName().substring(0, 16)));
				} else {
					o1.setDisplayName(Utils.AS(p.getDisplayName()));
				}
			}
							
			Score balance = o1.getScore(Bukkit.getOfflinePlayer("§3Shinies:"));
			Score paragons = o1.getScore(Bukkit.getOfflinePlayer("§3Patrol Lvl:"));
			Score online = o1.getScore(Bukkit.getOfflinePlayer("§9Online:"));
			Score rank = o1.getScore(Bukkit.getOfflinePlayer("§3Rank: " + Utils.AS(WCVault.chat.getPlayerPrefix(p))));
			Score options = o1.getScore(Bukkit.getOfflinePlayer("§5/root"));

			if (wcp.getInAlliance()){
				
				Score alliance;
				String completed = (pl.wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
						
				if (completed.length() >= 16){
					alliance = o1.getScore(Bukkit.getOfflinePlayer(completed.substring(0, 16)));
				} else {
					alliance = o1.getScore(Bukkit.getOfflinePlayer(completed));
				}
						
				alliance.setScore(wca.getMemberCount());
			}
						
			paragons.setScore(wcp.getPatrolLevel());
			balance.setScore((int) wcp.getBalance());
			rank.setScore(1);
			online.setScore(Bukkit.getOnlinePlayers().length);
			options.setScore(0);
				
		 } else if (wcp.getScoreboard()){
			 
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();	
			Objective o2 = board.registerNewObjective("wa", "dummy");
			o2.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			if (pl.afkers.contains(p)){
				o2.setDisplayName(Utils.AS("&7[afk]"));
			}
			
			if (!wcp.getScoreboardCoords() && !pl.afkers.contains(p)){
			
				if (p.getDisplayName().length() > 16){
					o2.setDisplayName(Utils.AS(p.getDisplayName().substring(0, 16)));
				} else {
					o2.setDisplayName(Utils.AS(p.getDisplayName()));
				}
			
			}
						
			Score balance = o2.getScore(Bukkit.getOfflinePlayer("§3Shinies:"));
			Score paragons = o2.getScore(Bukkit.getOfflinePlayer("§3Patrol Lvl:"));
			Score online = o2.getScore(Bukkit.getOfflinePlayer("§9Online:"));
			Score rank = o2.getScore(Bukkit.getOfflinePlayer("§3Rank: " + Utils.AS(WCVault.chat.getPlayerPrefix(p))));
			Score options = o2.getScore(Bukkit.getOfflinePlayer("§5/root"));
						
			if (wcp.getInAlliance()){
				
				Score alliance;
				String completed = (pl.wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
						
				if (completed.length() >= 16){
					alliance = o2.getScore(Bukkit.getOfflinePlayer(completed.substring(0, 16)));
				} else {
					alliance = o2.getScore(Bukkit.getOfflinePlayer(completed));
				}
						
				alliance.setScore(wca.getMemberCount());
			}
						
			paragons.setScore(wcp.getPatrolLevel());
			balance.setScore((int) wcp.getBalance());
			rank.setScore(1);
			online.setScore(Bukkit.getOnlinePlayers().length);
			options.setScore(0);
			p.setScoreboard(board);
		 } else {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		 }
	}
}
