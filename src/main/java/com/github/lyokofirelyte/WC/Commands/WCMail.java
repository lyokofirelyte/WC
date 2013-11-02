package com.github.lyokofirelyte.WC.Commands;

import java.io.IOException;
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

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;

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
		
		WCPlayer wcp = plugin.wcm.getWCPlayer(sender.getName());
		
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
			  
		        
		      case "alliance":
		    	
		    	mailAlliance(p, Utils.createString(args, 2), wcp);
		    	break;
		    	
		      case "staff":
		    	mailStaff(p, Utils.createString(args, 2));
		    	break;
		      
			  
			  case "website":		  
				  message = Utils.createString(args, 2);
				  mailSite(sender.getName(), message);
				  sender.sendMessage(Utils.AS(WC + "Your message has been sent to http://www.ohsototes.com/?p=mail"));
				  break;
				  
			  case "all": // so.. this "might" lag. We shall see. :3
				  
				  if (p.hasPermission("wa.staff") == false){
					  p.sendMessage(Utils.AS(WC + "You don't have permission to send global mails."));
					  break;
				  }
				  
				  List <String> userList = plugin.datacore.getStringList("Users.Total");
				  sender.sendMessage(Utils.AS(WC + "Message sent!"));
				  
				  for (String current : userList){
					  
					  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(current);
					  	if (!sendTo.isOnline()){
					  		plugin.wcm.setupPlayer(sendTo.getName());
					  	}
					  	
					  	WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(current);
				  	
					  message = Utils.createString(args, 2);
					  mail = wcpCurrent.getMail();
					  mail.add(p.getDisplayName() + " &f-> &2Global &9// &3" + message);
					  updatePlayer(wcpCurrent, current);
					  	if (sendTo.isOnline()){
					  		Bukkit.getPlayer(current).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
					  	} else {
					  		try {
								plugin.wcm.savePlayer(sendTo.getName());
							} catch (IOException e) {
								e.printStackTrace();
							}
					  	}
					  
				  }
				  
			  break;
		  
			  default:
				  
				  if (args[1].equalsIgnoreCase(p.getName())){
					  sender.sendMessage(Utils.AS(WC + "I'm schizophrenic, and so am I. Together, I can solve this."));
					  break;
				  }
				  
				    OfflinePlayer sendTo = Bukkit.getOfflinePlayer(args[1]);
				    	if (!sendTo.isOnline()){
				    		plugin.wcm.setupPlayer(sendTo.getName());
				    	}
				    
				  	if (sendTo.hasPlayedBefore() == false){
				  		sender.sendMessage(Utils.AS(WC + "That player has never logged in before!"));
				  		break;
				  	}
				  
				  	
				  	message = Utils.createString(args, 2);
				  	String lastWord = message.substring(message.lastIndexOf(" ")+1);
				  	
				  		if (message.contains("!exp") && Utils.isInteger(lastWord)){
		 
							 WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(sendTo.getName());
				  			 int xp = wcp.getExp();
				  			 int xpOther = wcpCurrent.getExp();
				  			 
				  			 	if (xp < Integer.parseInt(lastWord)){
				  			 		sender.sendMessage(WC + "You don't have that much XP! You tried to send: " + lastWord + ".");
				  			 		break;
				  			 	}
				  			 	
				  			 wcpCurrent.setExp(xpOther + Integer.parseInt(lastWord));
				  			 wcp.setExp(xp - Integer.parseInt(lastWord));
				  			 updatePlayer(wcp, p.getName());
				  			 updatePlayer(wcpCurrent, sendTo.getName());
				  			 
				  			 mail = wcpCurrent.getMail();
				  			 mail.add(p.getDisplayName() + " &9// &3" + message.replaceAll("!exp", "").replaceAll(lastWord, ""));
				  			 mail.add(p.getDisplayName() + " &3 has included &c" + lastWord + " &3exp in this mail.");
				  			 wcpCurrent.setMail(mail);
				  			 
				  			 updatePlayer(wcpCurrent, sendTo.getName());
				  			 sender.sendMessage(Utils.AS(WC + "Message sent!"));
				  			 
						  		if (sendTo.isOnline()){
						  			
						  			Bukkit.getPlayer(args[1]).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
						  		}
						  		
						  	break;
				  		}
				  		
				  	WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(sendTo.getName());
				  	mail = wcpCurrent.getMail();
				  	mail.add(p.getDisplayName() + " &9// &3" + message);
				  	wcpCurrent.setMail(mail);
				  	updatePlayer(wcpCurrent, sendTo.getName());
				  	sender.sendMessage(Utils.AS(WC + "Message sent!"));
				  		if (sendTo.isOnline()){
				  			Bukkit.getPlayer(args[1]).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
				  		} else {
				    		try {
								plugin.wcm.savePlayer(sendTo.getName());
							} catch (IOException e) {
								e.printStackTrace();
							}
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
		
		List<String> players = plugin.datacore.getStringList("Users.Total");
		
			for (String bleh : players){
				
				if (Utils.hasPerms(p.getWorld().getName(), bleh, "wa.staff")){
					
					if (!Bukkit.getOfflinePlayer(bleh).isOnline()){
						plugin.wcm.setupPlayer(bleh);
					}
					
					  WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(bleh);
					  
					  mail = wcpCurrent.getMail();
					  mail.add(p.getDisplayName() + " &f-> &aStaff &9// &3" + message);
					  wcpCurrent.setMail(mail);
					  updatePlayer(wcpCurrent, bleh);
					  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(bleh);
					  WCMain.s(p, "Mail sent!");
					  
					  	if (sendTo.isOnline()){
					  		Bukkit.getPlayer(bleh).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
					  	} else {
					  		try {
								plugin.wcm.savePlayer(sendTo.getName());
							} catch (IOException e) {
								e.printStackTrace();
							}
					  	}
				}
			}
			
		
	}


	private void mailAlliance(Player p, String message, WCPlayer wcp) {
		
		
			if (!wcp.getInAlliance()){
				p.sendMessage(WCMail.WC + "You're not in alliance. You're.... you're... FOREVER ALONE! ASHHEAHEHAHEaha.. .ah.");
				return;
			} 
			
			WCAlliance wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
		
			if (wcp.getAllianceRank().equals("Leader") || wca.getChatAdmins().contains(p.getName())){
			
				p.sendMessage(Utils.AS(WC + "Message sent!"));
				
				for (String bleh : wca.getUsers()){
							
							OfflinePlayer sendTo = Bukkit.getOfflinePlayer(bleh);	
							
								if (!sendTo.isOnline()){
									plugin.wcm.setupPlayer(sendTo.getName());
								}
								
							WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(sendTo.getName());
							
							mail = wcpCurrent.getMail();
							mail.add(p.getDisplayName() + " &f-> " + plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2()) + " &9// &3" + message);
							wcpCurrent.setMail(mail);
							updatePlayer(wcpCurrent, sendTo.getName());
							  	if (sendTo.isOnline()){
							  		Bukkit.getPlayer(bleh).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
							  	} else {
							  		try {
										plugin.wcm.savePlayer(sendTo.getName());
									} catch (IOException e) {
										e.printStackTrace();
									}
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
		
		WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
		mail = wcp.getMail();
		
		if (mail.size() == 0){
			p.sendMessage(Utils.AS(WC + "You have no mail!"));
			return;
		}
		
		wcp.clearMail();
		updatePlayer(wcp, p.getName());
		p.sendMessage(Utils.AS(WC + "Mail cleared!"));
	}
	
	public void mailCheck(Player p){
    	
		WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
    	mail = wcp.getMail();
		
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
		
		WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
		
		if (wcp.getMail().size() > 1){
			mail = wcp.getMail();
		} else {
			WCMain.s(p, "You have no new mail.");
			return;
		}

		List <String> userList = plugin.datacore.getStringList("Users.Total");
		
		if (mail.size() > 0){
			p.sendMessage(Utils.AS(WC + "You have " + mail.size() + " &dnew messages. Read them with /mail read."));
		}
		
		if (userList.contains(p.getName())){
			return;
		} else {
			userList.add(p.getName());
			plugin.datacore.set("Users.Total", userList);
		}
		

	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	  public boolean onPlayerJoin(final PlayerJoinEvent event){
		mailLogin(event.getPlayer());
		return true;
	}
	
	 public void updatePlayer(WCPlayer wcp, String name){
			plugin.wcm.updatePlayerMap(name, wcp);  
			wcp = plugin.wcm.getWCPlayer(name);
	 }
		  
	 public void updateAlliance(WCAlliance wca, String name){
		plugin.wcm.updateAllianceMap(name, wca);  
		wca = plugin.wcm.getWCAlliance(name);
	 }
		  
	public void updateAll(WCAlliance wca, WCPlayer wcp, String alliance, String player){
		plugin.wcm.updateAllianceMap(alliance, wca);
		plugin.wcm.updatePlayerMap(player, wcp);
			
		wcp = plugin.wcm.getWCPlayer(player);
		wca = plugin.wcm.getWCAlliance(alliance);
    }
}
