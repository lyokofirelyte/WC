package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.Util.Utils.*;

import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.PlayerMoneyTransferEvent;

public class WCPay implements CommandExecutor, Listener {

      WCMain plugin;
	  public WCPay(WCMain instance){
	  this.plugin = instance;
	  }
	  
	  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		  
		if (cmd.getName().equalsIgnoreCase("pay")){
			
			Player p = ((Player)sender);
			
			if (args.length != 2){
				s(p, "Try /pay <player> <amount>");
				return true;
			}
			
			if (Bukkit.getPlayer(args[0]) == null){
				s(p, "That player is not online!");
				return true;
			}
			
			if (!isInteger(args[1])){
				s(p, "That's not a number!");
				return true;
			}
			
			PlayerMoneyTransferEvent e = new PlayerMoneyTransferEvent(p, Bukkit.getPlayer(args[0]), Integer.parseInt(args[1]));
			plugin.getServer().getPluginManager().callEvent(e);
		}
		
		if (cmd.getName().equalsIgnoreCase("balance") || cmd.getName().equalsIgnoreCase("money")){
			WCPlayer wcp = plugin.wcm.getWCPlayer(((Player)sender).getName());
			WCMain.s((Player)sender, "Shinies: " + wcp.getBalance());
		}
		  
	  return true;
	  }
}
