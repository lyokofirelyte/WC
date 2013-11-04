package com.github.lyokofirelyte.WC.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;

public class WCMail implements CommandExecutor, Listener {
public static String WC = "§dWC §5// §d";
List <String> mail;
private Connection conn;
private PreparedStatement pst;
String message;

	WCMain plugin;
	public WCMail(WCMain instance){
	this.plugin = instance;
	}

	List<String> WCHelpMail;
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	if (cmd.getName().equals("mail")){
		
		WCHelpMail = plugin.help.getStringList("WC.Mail");
		
		if (sender instanceof Player == false){
			sender.sendMessage(Utils.AS(WC + "Sorry console, I'm too lazy to allow you to send mails. That's like, extra effort."));
			return true;
		}
		
		Player p = (Player) sender;
		
		
		switch(args.length){
		
		case 0:
			
			for (String help : WCHelpMail){
	    		sender.sendMessage(Utils.AS(help));
	    	}
	    	
			break;
		
		case 1:
			
			switch(args[0]){

			
			case "send":
				
				sender.sendMessage(Utils.AS(WC + "Try /mail send <player> <message>."));
				break;
	
		    case "read":

		    	mailCheck(p);
		    	break;
		    	
		    case "clear":
		    	
		        clearCheck(p);
		        break;
		        
		    case "quick":
		    	
		    	mailCheck(p);
		    	clearCheck(p);
		    	break;
		        
		    default:
		    	
		    	List <String> WCHelpMail = plugin.help.getStringList("WC.Mail");
		    	
		    	for (String help : WCHelpMail){
		    		sender.sendMessage(Utils.AS(help));
		    	}
		    	
		    break;
		
			}
			
		break;
			
		default:
			
		  switch(args[0]){
		  
		  default:
		    	
			  List <String> WCHelpMail = plugin.help.getStringList("WC.Mail");
		    	
		    	for (String help : WCHelpMail){
		    		sender.sendMessage(Utils.AS(help));
		    	}
		    	
		    break;
		    
		  case "send":
			  
			  switch (args[1]){
		    	
		      case "staff":
		    	mailStaff(p, Utils.createString(args, 2));
		    	break;
		      
			  
			  case "website":		  
				  message = Utils.createString(args, 2);
				  mailSite(sender.getName(), message);
				  sender.sendMessage(Utils.AS(WC + "Your message has been sent to http://www.ohsototes.com/?p=mail"));
				  break;
				  
			  case "all":
				  
				  if (p.hasPermission("wa.staff") == false){
					  p.sendMessage(Utils.AS(WC + "You don't have permission to send global mails."));
					  break;
				  }
				  
				  List <String> userList = plugin.mail.getStringList("Users.Total");
				  sender.sendMessage(Utils.AS(WC + "Message sent!"));
				  
				  for (String current : userList){
					  
					  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(current);
				  	
					  message = Utils.createString(args, 2);
					  mail = plugin.mail.getStringList("Users." + current + ".Mail");
					  mail.add(p.getDisplayName() + " &f-> &2Global &9// &3" + message);
					  plugin.mail.set("Users." + current + ".Mail", mail);
					  	if (sendTo.isOnline()){
					  		Bukkit.getPlayer(current).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
					  	}
					  
				  }
				  
			  break;
		  
			  default:
				  
				  if (args[1].equalsIgnoreCase(p.getName())){
					  sender.sendMessage(Utils.AS(WC + "I'm schizophrenic, and so am I. Together, I can solve this."));
					  break;
				  }
				  
				  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(args[1]);
				  
				  if (sendTo.hasPlayedBefore() == false){
					  sender.sendMessage(Utils.AS(WC + "That player has never logged in before!"));
					  break;
				  }
				  	

				  message = Utils.createString(args, 2);
				  	
				  mail = plugin.mail.getStringList("Users." + sendTo.getName() + ".Mail");
				  mail.add(p.getDisplayName() + " &9// &3" + message);
				  plugin.mail.set("Users." + sendTo.getName() + ".Mail", mail);
				  sender.sendMessage(Utils.AS(WC + "Message sent!"));
				  
				  if (sendTo.isOnline()){
					  Bukkit.getPlayer(args[1]).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
				  }
				  
			   break;
			   
			  }
		  }

	}
	
	}
	return true;
	}
		
	
	private void mailStaff(Player p, String message) {
		
		if (p.hasPermission("wa.staff") == false){
			p.sendMessage(WCMail.WC + "You don't have permission to send mail to staff.");
			return;
		}
		
		List<String> players = plugin.mail.getStringList("Users.Total");
		
			for (String bleh : players){
				
				if (WCVault.perms.has("world", bleh, "wa.staff")){
					
					  mail = plugin.mail.getStringList("Users." + bleh + ".Mail");
					  mail.add(p.getDisplayName() + " &f-> &aStaff &9// &3" + message);
					  plugin.mail.set("Users." + bleh + ".Mail", mail);
					  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(bleh);
					  
					  	if (sendTo.isOnline()){
					  		Bukkit.getPlayer(bleh).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
					  	}
				}
			}
	}


	public void mailSite(String name, String message){
		
		String url = plugin.config.getString("urlMail");
        String username = plugin.config.getString("username");
        String password = plugin.config.getString("password");
		
		try {
	        this.conn = DriverManager.getConnection(url, username, password);

	        this.pst = this.conn.prepareStatement("INSERT INTO mail(timestamp, name, message) VALUES(?, ?, ?)");

	        long timestamp = System.currentTimeMillis() / 1000L;

	        this.pst.setInt(1, (int)timestamp);
	        this.pst.setString(2, name);
	        this.pst.setString(3, message);

	        this.pst.executeUpdate();
	        this.pst.close();
	        this.conn.close();

	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	      }
	    }
	
	public void clearCheck(Player p){
		
		mail = plugin.mail.getStringList("Users." + p.getName() + ".Mail");
		
		if (mail.size() == 0){
			p.sendMessage(Utils.AS(WC + "You have no mail!"));
			return;
		}
		
		plugin.mail.set("Users." + p.getName() + ".Mail", null);
		p.sendMessage(Utils.AS(WC + "Mail cleared!"));
	}
	
	public void mailCheck(Player p){
    	
    	mail = plugin.mail.getStringList("Users." + p.getName() + ".Mail");
		
		if (mail.size() == 0){
			p.sendMessage(Utils.AS(WC + "You have no mail!"));
			return;
		}
		
    	p.sendMessage(Utils.AS(WC + "Viewing Mail &6(" + mail.size() + "&6)"));
    		for (String singleMail : mail){
    			p.sendMessage(Utils.AS("&5| " + singleMail));
    		}
	}
	
	public void mailLogin(Player p){
		
		mail = plugin.mail.getStringList("Users." + p.getName() + ".Mail");
		List <String> userList = plugin.mail.getStringList("Users.Total");
		
		if (mail.size() > 0){
			p.sendMessage(Utils.AS(WC + "You have " + mail.size() + " &dnew messages. Read them with /mail read."));
		}
		
		if (userList.contains(p.getName())){
			return;
		} else {
			userList.add(p.getName());
			plugin.mail.set("Users.Total", userList);
		}
		

	}
	
	@EventHandler(priority=EventPriority.LOW)
	  public boolean onPlayerJoin(final PlayerJoinEvent event){
		mailLogin(event.getPlayer());
		return true;
	}
}
