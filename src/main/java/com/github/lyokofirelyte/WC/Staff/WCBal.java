package com.github.lyokofirelyte.WC.Staff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCBal{

	 WCMain pl;
	 public WCBal(WCMain instance){
	 this.pl = instance;
	 }
	 
	 WCPlayer wcpCurrent;
	 
	 @WCCommand(aliases = {"balance", "bal"}, desc = "WC Bal Command", help = "/balance <set, take, give, top> [player] [amount]")
	 public void onBal(Player sender, String[] args){
			  			
			Player p = ((Player)sender);
			WCPlayer wcp = pl.wcm.getWCPlayer(p.getName());
			
			if (args.length == 0){
				s(p, "Balance: &6" + wcp.getBalance());
				return;
			}
			
			switch (args[0].toLowerCase()){
			
				case "set": case "take": case "give":
					
					if (args.length != 3){
						s(p, "/bal " + args[0].toLowerCase() + " <player> <amount>");
						return;
					}
					
					if (!p.hasPermission("wa.admin")){
						s(p, "Admin only command!");
						return;
					}
					
					if (!Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore() || pl.wcm.getWCPlayer(args[1]) == null){
						s(p, "That player is not registered with the API! (Check spelling?)");
						return;
					}
					
					if (!Utils.isInteger(args[2]) || Integer.parseInt(args[2]) > 10000000 || Integer.parseInt(args[2]) < 0){
						s(p, "Invalid number!");
						return;
					}
					
					wcpCurrent = pl.wcm.getWCPlayer(args[1]);
					
				break;
			}
			
			switch (args[0].toLowerCase()){
			
				case "top": balTop(p); break;
				
				case "set":
					
					wcpCurrent.setBalance(Integer.parseInt(args[2]));
					pl.wcm.updatePlayerMap(args[1], wcpCurrent);	
					s(p, "Complete!");
					
				break;
					
				case "take":
					
					wcpCurrent.setBalance(wcpCurrent.getBalance() - Integer.parseInt(args[2]));
					pl.wcm.updatePlayerMap(args[1], wcpCurrent);
					s(p, "Complete! Their new balance is " + wcpCurrent.getBalance());
					
				break;
				
				case "give":
					
					wcpCurrent.setBalance(wcpCurrent.getBalance() + Integer.parseInt(args[2]));
					pl.wcm.updatePlayerMap(args[1], wcpCurrent);
					s(p, "Complete! Their new balance is " + wcpCurrent.getBalance());
					
				break;
				
				case "help":
					
					s(p, "/bal <set || take || give> <player> <amount>");
					s2(p, "&d/bal top");
					s2(p, "&d/bal <player>");
					
				break;
				
				default:
					
					if (pl.wcm.getWCPlayer(args[0]) == null){
						s(p, "Player not found in API! Try /bal help for more options...");
						return;
					}
					
					wcpCurrent = pl.wcm.getWCPlayer(args[0]);
					s(p, pl.wcm.getFullNick(args[0]) + " &dhas a balance of &6" + wcpCurrent.getBalance());
					
				break;
			}

		return;
	 }
	 
	 public void balTop(Player sendTo){
		 
		 List<Integer> balances = new ArrayList<>();
		 List<String> players = new ArrayList<>();
		 
		 for (String p : pl.wcm.getWCSystem("system").getUsers()){
			 if (pl.wcm.getWCPlayer(p).getBalance() > 0){
				 balances.add(pl.wcm.getWCPlayer(p).getBalance());
			 }
		 }

		 Collections.sort(balances);
		 Collections.reverse(balances);
		 
		 for (String p : pl.wcm.getWCSystem("system").getUsers()){
			 for (int x = 0; x <= 10; x++){
				 if (balances.size() > x && balances.get(x) == pl.wcm.getWCPlayer(p).getBalance()){
					 players.add(p);
				 }
			 }
		 }
		 
		 s(sendTo, "Top Balances");
		 
		 for (int i : balances){
			 for (String s : players){
				 if (pl.wcm.getWCPlayer(s).getBalance() == i){
					 s2(sendTo, pl.wcm.getFullNick(s) + " &f// &6" + pl.wcm.getWCPlayer(s).getBalance());
				 }
			 }
		 }
	 }
}