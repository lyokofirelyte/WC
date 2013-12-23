package com.github.lyokofirelyte.WC.Commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.WCMain;

public class WCHelp implements CommandExecutor {
	
	String WC = "�dWC �5// �d";
	
	WCMain plugin;
	public WCHelp(WCMain instance){
	plugin = instance;
	}
	
	public List <String> WCHelpMail;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if (cmd.getName().equalsIgnoreCase("search")){
			
			WCHelpMail = plugin.help.getStringList("WC.Mail");
			
			if (args.length == 0){
				sender.sendMessage(WC + "Type /search <query>.");
				return true;
			}
			
			int x = 0;
			
		//	for (String search : AdministratumHelpGlobal){
			//	if (search.toLowerCase().contains(args[0].toLowerCase())){
			//		sender.sendMessage(Utils.AS(search.replaceAll(args[0], "&6" + args[0] + "&3")));
			//		x++;
			//	}
		//	}
			
			for (String search : WCHelpMail){
				if (search.toLowerCase().contains(args[0].toLowerCase())){
					sender.sendMessage(Utils.AS(search.replaceAll(args[0], "&6" + args[0] + "&3")));
					x++;
				}
			}
			
			
			sender.sendMessage("�7�o" + x + " �7�oresult(s) found.");
		}
	

		return true;
	}
}
