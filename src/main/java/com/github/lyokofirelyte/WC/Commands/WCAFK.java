package com.github.lyokofirelyte.WC.Commands;

import static com.github.lyokofirelyte.WC.Util.Utils.AS;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

public class WCAFK {
	
	WCMain pl;
	public WCAFK(WCMain instance){
		pl = instance;
    }
	
	@WCCommand(aliases = {"afk", "wcafk"}, help = "Toggles your AFK status!")
	public void afkToggle(Player p, String[] args){
					
			if (pl.afkers.contains(p)){
				pl.afkers.remove(p);
				Bukkit.broadcastMessage(AS("&7&o" + p.getDisplayName() + " &7&ois no longer afk. They were afk for " + Math.round(pl.afkTimer.get(p.getName()) / 60) + " &7&ominutes."));
				if ((p.getDisplayName()).length() > 16){
					p.setPlayerListName(AS(p.getDisplayName()).substring(0, 16));
				} else {
					p.setPlayerListName(AS(p.getDisplayName()));
				}
			    pl.afkers.remove(p);
				Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
				return;
			}
	
			Bukkit.broadcastMessage(AS("&7&o" + p.getDisplayName() + " &7&ois afk."));
			
			if (("&7[afk] " + p.getDisplayName()).length() > 16){
				p.setPlayerListName(AS("&7[afk] " + p.getDisplayName()).substring(0, 16));
			} else {
				p.setPlayerListName(AS("&7[afk] " + p.getDisplayName()));
			}
			
			pl.afkers.add(p);
			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		}
}