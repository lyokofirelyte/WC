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
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

public class WCScoreboard implements Listener {
	
	WCMain pl;
	WCPlayer wcp;
	WCAlliance wca;
	
	public WCScoreboard(WCMain instance){
	   pl = instance;
    } 

	@EventHandler (priority = EventPriority.NORMAL)
	public void onScoreBoard(ScoreboardUpdateEvent e){
		
	     Player p = e.getPlayer();
		 wcp = pl.wcm.getWCPlayer(p.getName());
		 wca = pl.wcm.getWCAlliance(wcp.getAlliance());
		 Objective o1 = p.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
		 
		 if (o1 != null && wcp.getScoreboard()){	
			 
			if (p.getDisplayName().length() > 16){
				o1.setDisplayName(p.getDisplayName().substring(0, 16));
			} else {
				o1.setDisplayName(p.getDisplayName());
			}
						
			Score balance = o1.getScore(Bukkit.getOfflinePlayer("§3Balance:"));
			Score paragons = o1.getScore(Bukkit.getOfflinePlayer("§3Paragon Lvl:"));
			Score online = o1.getScore(Bukkit.getOfflinePlayer("§9Online:"));
			Score rank = o1.getScore(Bukkit.getOfflinePlayer("§3Rank: " + Utils.AS(WCVault.chat.getPlayerPrefix(p))));
			Score options = o1.getScore(Bukkit.getOfflinePlayer("§5/root"));
			Score alliance2;
						
			if (!wcp.getInAlliance()){		
				Score alliance = o1.getScore(Bukkit.getOfflinePlayer("§7Forever§8Alone"));
				alliance.setScore(1);
			} else {
				
			String completed = (pl.wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
						
			if (completed.length() >= 16){
					alliance2 = o1.getScore(Bukkit.getOfflinePlayer(completed.substring(0, 16)));
				} else {
					alliance2 = o1.getScore(Bukkit.getOfflinePlayer(completed));
				}
						
				alliance2.setScore(wca.getMemberCount());
			}
						
			paragons.setScore(wcp.getParagonLevel());
			balance.setScore((int) WCVault.econ.getBalance(p.getName()));
			rank.setScore(1);
			online.setScore(Bukkit.getOnlinePlayers().length);
			options.setScore(0);
				
		 } else if (wcp.getScoreboard()){
			 
			   ScoreboardManager manager = Bukkit.getScoreboardManager();
			Scoreboard board = manager.getNewScoreboard();	
			Objective o2 = board.registerNewObjective("wa", "dummy");
			o2.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			if (p.getDisplayName().length() > 16){
				o2.setDisplayName(p.getDisplayName().substring(0, 16));
			} else {
				o2.setDisplayName(p.getDisplayName());
			}
						
			Score balance = o2.getScore(Bukkit.getOfflinePlayer("§3Balance:"));
			Score paragons = o2.getScore(Bukkit.getOfflinePlayer("§3Paragon Lvl:"));
			Score online = o2.getScore(Bukkit.getOfflinePlayer("§9Online:"));
			Score rank = o2.getScore(Bukkit.getOfflinePlayer("§3Rank: " + Utils.AS(WCVault.chat.getPlayerPrefix(p))));
			Score options = o2.getScore(Bukkit.getOfflinePlayer("§5/root"));
			Score alliance2;
						
			if (!wcp.getInAlliance()){
				Score alliance = o2.getScore(Bukkit.getOfflinePlayer("§7Forever§8Alone"));
				alliance.setScore(1);
			} else {
				String completed = (pl.wcm.getCompleted2(wcp.getAlliance(), wca.getColor1(), wca.getColor2()));
						
				if (completed.length() >= 16){
					alliance2 = o2.getScore(Bukkit.getOfflinePlayer(completed.substring(0, 16)));
				} else {
					alliance2 = o2.getScore(Bukkit.getOfflinePlayer(completed));
				}
						
				alliance2.setScore(wca.getMemberCount());
			}
						
			paragons.setScore(wcp.getParagonLevel());
			balance.setScore((int) WCVault.econ.getBalance(p.getName()));
			rank.setScore(1);
			online.setScore(Bukkit.getOnlinePlayers().length);
			options.setScore(0);
			p.setScoreboard(board);
		 } else {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		 }
	}
}
