package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;
import com.github.lyokofirelyte.WCAPI.Loops.WCLoop;

import static com.github.lyokofirelyte.WC.Util.Utils.*;

public class WCKill {
	
	private WCMain main;
	
	public WCKill(WCMain main){
		
		this.main = main;
		
	}
	
	@WCCommand(aliases = { "kill" }, desc = "Kill a player, any player!", help = "/kill <player>", min = 1, max = 1, perm = "wa.staff", player = true)
	public void onTheKill(Player p, String[] args){
		
		Player pp = Bukkit.getPlayer(args[0]);
		
		if (pp == null){
			
			s(p, "That player is not online, you twat!");
			
		} else if (pp.getName().equalsIgnoreCase("tdstaz69")){
			
			s(p, "You're cute, trying to kill Trace.");
			
		} else {
			
			s(p, "Killing " + pp.getDisplayName() + "&d. Enjoy.");
			
			s(pp, "Wow, you must've pissed off a staff member...");
			main.api.ls.callLoop(main, this, "scream", pp);
			main.api.ls.callDelay(main, this, "delay", pp);
			
		}
		
	}
	
	@WCLoop(time = 0, delay = 10, repeats = 5)
	public void scream(CraftPlayer p){
		
		p.getWorld().playSound(p.getLocation(), Sound.ENDERMAN_STARE, 1.0F, 1.0F);
		
	}
	
	@WCDelay(time = 140)
	public void delay(Player p){
		
		p.damage(1000.0);
		
	}
	
}
