package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WC.Util.Utils.*;

import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCPay{

      WCMain pl;
	  public WCPay(WCMain instance){
	  this.pl = instance;
	  }
	  
	  @WCCommand(aliases = {"pay"}, help = "Give away your money", max = 2, player = true)
	  public void onPay(Player sender, String[] args){
			
			Player p = ((Player)sender);
			
			if (args.length != 2){
				s(p, "Try /pay <player> <amount>");
				return;
			}
			
			if (pl.wcm.getWCPlayer(args[0]) == null){
				s(p, "That player does not exist! (Check spelling!)");
				return;
			}
			
			if (!isInteger(args[1]) || args[1].startsWith("-")){
				s(p, "That's not a number!");
				return;
			}
			
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			WCPlayer wcpCurrent = pl.wcm.getWCPlayer(args[0]);
			
			if (wcp.getBalance() < Integer.parseInt(args[1])){
				s(p, "You lack the funds to do that!");
				return;
			}
					
			wcp.setBalance(wcp.getBalance() - Integer.parseInt(args[1]));
			wcpCurrent.setBalance(wcpCurrent.getBalance() + Integer.parseInt(args[1]));
			pl.wcm.updatePlayerMap(args[0], wcpCurrent);
			pl.wcm.updatePlayerMap(p.getName(), wcp);
			
			s(p, "Sent!");
			
			if (Bukkit.getOfflinePlayer(args[0]).isOnline()){
				s(Bukkit.getPlayer(args[0]), p.getDisplayName() + " &dhas given you &6" + args[1] + " &dshinies.");
			}
		  
		return;
	  }
}
