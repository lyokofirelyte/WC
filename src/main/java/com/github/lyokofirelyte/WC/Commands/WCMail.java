package com.github.lyokofirelyte.WC.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.github.lyokofirelyte.WC.WCMain.s;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;

public class WCMail implements Listener {
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
	
	@WCCommand(aliases = {"mail"}, help = "Send mail to another player", min = 1)
	public void onMail(Player sender, String[] args){
	
		
		WCHelpMail = plugin.help.getStringList("WC.Mail");
		
		if (sender instanceof Player == false){
			sender.sendMessage(Utils.AS(WC + "Sorry console, I'm too lazy to allow you to send mails. That's like, extra effort."));
			return;
		}
		
		Player p = (Player) sender;

		switch(args.length){
		
		case 0:
			
			for (String help : WCHelpMail){
	    		s(p, help);
	    	}
	    	
			break;
		
		case 1:
			
			switch(args[0]){

			
			case "send":
				
				s(p, "Try /mail send <player> <message>.");
				break;
	
		    case "read":

		    	mailCheck(p, plugin.wcm.getWCPlayer(p.getName()));
		    	break;
		    	
		    case "clear":
		    	
		    	clearCheck(p, plugin.wcm.getWCPlayer(p.getName()));
		        break;
		        
		    case "quick":
		    	
		    	mailCheck(p, plugin.wcm.getWCPlayer(p.getName()));
		    	clearCheck(p, plugin.wcm.getWCPlayer(p.getName()));
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
		    		s(p, help);
		    	}
		    	
		    break;
		    
		  case "send":
			  
			  switch (args[1]){
		    	
		      case "staff":
		    	  mailStaff(p, Utils.createString(args, 2));
		    	  break;
		    	  
		      case "alliance":
		    	  mailAlliance(p, Utils.createString(args, 2));
		    	  break;
			  
			  case "website":		  
				  message = Utils.createString(args, 2);
				  mailSite(sender.getName(), message);
				  s(p, "Your message has been sent to http://www.ohsototes.com/?p=mail");
				  break;
				  
			  case "all":
				  
				  if (p.hasPermission("wa.staff") == false){
					  s(p, "You don't have permission to send global mails.");
					  break;
				  }
				  
				  List <String> userList = plugin.wcm.getWCSystem("system").getUsers();
				  message = Utils.createString(args, 2);
				  s(p, "Message sent to everyone!");
				  
				  for (String current : userList){
					  sendMail(p, current, "&2Global", message);
				  }
				  
			  break;
		  
			  default:
				  
				  if (args[1].equalsIgnoreCase(p.getName())){
					  sender.sendMessage(Utils.AS(WC + "I'm schizophrenic, and so am I. Together, I can solve this."));
					  break;
				  }
				  
				  OfflinePlayer sendTo = Bukkit.getOfflinePlayer(args[1]);
				  
				  if (sendTo.hasPlayedBefore() == false){
					  s(p, "That player has never logged in before!");
					  break;
				  }
				  	
				  message = Utils.createString(args, 2);  	
				  sendMail(p, args[1], "&6you", message);
				  s(p, "Message sent!");
				  
			   break;
			   
			  }
		  }
		}
	
	return;
	}
	
	private void mailAlliance(Player p, String message){
		
		WCPlayer wcp = plugin.wcm.getWCPlayer(p.getName());
		
		if (!wcp.getInAlliance()){
			s(p, "You're not in an alliance.");
		} else {
			for (String s : plugin.wcm.getWCSystem("system").getUsers()){
				WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(s);
				if (wcpCurrent != null && wcpCurrent.getInAlliance() && wcpCurrent.getAlliance().equals(wcp.getAlliance())){
					sendMail(p, s, "&bAlliance", message);
				}
			}
			s(p, "Sent!");
		}
	}
		
	private void mailStaff(Player p, String message) {
		
		if (!p.hasPermission("wa.staff")){
			s(p, "You don't have permission to send mail to staff.");
			return;
		}
		
		List<String> players = plugin.systemYaml.getStringList("Users.Total");
		
		for (String bleh : players){
				
			if (WCVault.perms.has("world", bleh, "wa.staff")){

				sendMail(p, bleh, "&aStaff", message);
				
				OfflinePlayer sendTo = Bukkit.getOfflinePlayer(bleh);
					  
			    if (sendTo.isOnline()){
			    	s(Bukkit.getPlayer(bleh), "You've recieved a new mail! Check it with /mail read.");
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
	
	public void clearCheck(Player p, WCPlayer mailReceiver){
		
		if (mailReceiver.getMail().size() <= 0){
			s(p, "You have no mail!");
			return;
		}
		
		mailReceiver.setMail(new ArrayList<String>());
		plugin.wcm.updatePlayerMap(p.getName(), mailReceiver);
		s(p, "Mail cleared!");
	}
	
	public void mailCheck(Player p, WCPlayer mailReceiver){
    	
    	mail = mailReceiver.getMail();
		
		if (mail.size() == 0){
			p.sendMessage(Utils.AS(WC + "You have no mail!"));
			return;
		}
		
    	s(p, "Viewing Mail &6(" + mail.size() + "&6)");
    	
    	for (String singleMail : mail){
    		p.sendMessage(Utils.AS("&5| " + singleMail));
    	}
	}
	
	public void mailLogin(Player p){
		
		WCPlayer mailReceiver = plugin.wcm.getWCPlayer(p.getName());
	
		if (mailReceiver.getMail().size() > 0){
			p.sendMessage(Utils.AS(WC + "You have " + mailReceiver.getMail().size() + " &dnew messages. Type /mail read or /mail quick."));
			mailCheck(p, mailReceiver);
		} else {
			p.sendMessage(Utils.AS(WC + "You have no new messages."));
		}
		
	}
	
	public void sendMail(Player p, String player, String type, String message){

		WCPlayer mailReceiver = plugin.wcm.getWCPlayer(player);
		  	
		mail = mailReceiver.getMail();
		mail.add(p.getDisplayName() + " &f-> " + type + " &9// &3" + message);
		mailReceiver.setMail(mail);
		plugin.wcm.updatePlayerMap(player, mailReceiver);
			  
		if (Bukkit.getPlayer(player) != null){
			Bukkit.getPlayer(player).sendMessage(Utils.AS(WC + "You've recieved a new mail! Check it with /mail read."));
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST) 
	public void onPlayerJoin(PlayerJoinEvent e){
		mailLogin(e.getPlayer());
	}
}
