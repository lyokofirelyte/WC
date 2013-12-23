package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.Util.Utils.*;

import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCPay implements CommandExecutor {

      WCMain pl;
	  public WCPay(WCMain instance){
	  this.pl = instance;
	  }
	  
	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		if (cmd.getName().equalsIgnoreCase("pay")){
			
			Player p = ((Player)sender);
			
			if (args.length != 2){
				s(p, "Try /pay <player> <amount>");
				return true;
			}
			
			if (pl.wcm.getWCPlayer(args[0]) == null){
				s(p, "That player does not exist! (Check spelling!)");
				return true;
			}
			
			if (!isInteger(args[1])){
				s(p, "That's not a number!");
				return true;
			}
			
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			WCPlayer wcpCurrent = pl.wcm.getWCPlayer(args[0]);
			
			if (wcp.getBalance() < Integer.parseInt(args[1])){
				s(p, "You lack the funds to do that!");
				return true;
			}
					
			wcp.setBalance(wcp.getBalance() - Integer.parseInt(args[1]));
			wcpCurrent.setBalance(wcpCurrent.getBalance() + Integer.parseInt(args[1]));
			pl.wcm.updatePlayerMap(args[0], wcpCurrent);
			pl.wcm.updatePlayerMap(p.getName(), wcp);
			
			s(p, "Sent!");
			
			if (Bukkit.getOfflinePlayer(args[0]).isOnline()){
				s(Bukkit.getPlayer(args[0]), p.getDisplayName() + " &dhas given you &6" + args[1] + " &dshinies.");
			}
		}
		  
		return true;
	  }
}
