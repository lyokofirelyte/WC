package com.github.lyokofirelyte.WC;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import static com.github.lyokofirelyte.WC.Util.Utils.*;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import static com.github.lyokofirelyte.WC.WCMain.*;


public class WCChannels implements CommandExecutor, Listener {
	
  WCMain plugin;
  String WC = "§dWC §5// §d";
  WCAlliance wca;
  WCPlayer wcp;
  WCPlayer wcpCurrent;
  List<String> chatUsers;
  Player p;
  
  public WCChannels(WCMain instance){
    this.plugin = instance;
  }

  public static String getTime() {
  	Calendar cal = Calendar.getInstance();
  	cal.getTime();
  	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
  	return ( sdf.format(cal.getTime()) );
  }
  
	public void stats(String s, Player p){
		
		if (s.length() == 6){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + p.getName() + " c:!stats " + p.getName());
			return;
		}
		
		String message[] = s.split(" ");
		OfflinePlayer player = Bukkit.getOfflinePlayer(message[1].toString());
		wcp = plugin.wcm.getWCPlayer(player.getName());
		wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
		
			if (player.hasPlayedBefore() == false){
				p.sendMessage(WCMail.WC + "That player has never logged in before!");
				return;
			}
			
			if (wcp.getAlliance().equals("ForeverAlone")){
				wcp.setAllianceRank("Guest");
				updatePlayer(wcp, player.getName());
			}
			
		p.sendMessage(new String[]{
			AS(WCMail.WC + "Inspecting player " + player.getName() + "."),
			AS("&1| &a&oglobal rank&f: " + WCVault.chat.getPlayerPrefix("world", message[1])),
			AS("&1| &f> > > < < <"),
			AS("&1| &b&oalliance&f: " + plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2())),
			AS("&1| &b&oalliance ranking&f: " + wcp.getAllianceRank()),
			AS("&1| &f> > > < < <"),
			AS("&1| &6&oparagon level&f: " + wcp.getParagonLevel()),
			AS("&1| &6&oshiny balance&f: " + WCVault.econ.getBalance(player.getName())),
			AS("&1| &6&odeath count&f: " + wcp.getDeathCount()),
			AS("&1| &f> > > < < <"),
		});
		
		if (player.isOnline()){
			p.sendMessage(AS("&1| &c&ostatus&f: &aONLINE"));
		} else {
			p.sendMessage(AS("&1| &c&ostatus&f: &4OFFLINE"));	
		}
		
	}
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerChat(AsyncPlayerChatEvent e){
	  
	  p = e.getPlayer();
	  wcp = plugin.wcm.getWCPlayer(p.getName());
	  
	  String globalColor = wcp.getGlobalColor();
		
	  if (globalColor == null){
			wcp.setGlobalColor("7");
			updatePlayer(wcp, p.getName());
	  }

	  if (e.isCancelled()){
		  return;
	  }
	  
	  if (plugin.datacore.getBoolean("Users." + e.getPlayer().getName() + ".ObeliskTemp")){
		  e.setCancelled(true);
		  
		  if (plugin.config.getStringList("Obelisks.Names").contains(e.getMessage().toLowerCase())){
			  plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskSelection", true);
		  	  plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskLocation", e.getMessage().toLowerCase());
		  	  e.getPlayer().sendMessage(WC + "Alright! Now just right click the glowstone and you're ready to go!");
		      plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskTemp", null);
		  } else if (e.getMessage().equals("##")){
			  plugin.datacore.set("Users." + e.getPlayer().getName() + ".ObeliskTemp", null);
		  		e.getPlayer().sendMessage(WC + "Cancelled your teleport!");
		  } else {
			  e.getPlayer().sendMessage(WC + "That location does not exist. Try again or type ## to cancle.");
		  }  
		  
		  return;
	  }

	  if (e.getMessage().startsWith("!stats")){
		  e.setCancelled(true);
		  stats(e.getMessage(), e.getPlayer());
		  return;
	  }
	  
	  if (e.getMessage().contains("%c")) {
		  Location loc = e.getPlayer().getLocation();
		  int x = loc.getBlockX();
		  int y = loc.getBlockY(); 
		  int z = loc.getBlockZ(); 
		  e.setMessage(e.getMessage().replaceAll("%c", x + "," + y + "," + z)); 
	  } 
	  
	  if (e.getMessage().contains("%p")){
		  e.setMessage(e.getMessage().replaceAll("%p", "ohsototes.com/?p=paragon"));
	  }
	  
	  if (e.getMessage().contains("%t")) { 
		  e.setMessage(e.getMessage().replaceAll("%t", plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2())));
	  }  

	  e.setCancelled(true);
	  globalChat(p, e.getMessage(), wcp, wca);
  }
  


  public void globalChat(Player p, String message, WCPlayer wcp, WCAlliance wca){

		for (Player bleh : Bukkit.getOnlinePlayers()){
			
			WCPlayer wcpCurrent = wcp;
			wcp = plugin.wcm.getWCPlayer(bleh.getName());
			wcpCurrent = plugin.wcm.getWCPlayer(p.getName());
			
			if (message.contains("&r")){
				message = message.replace("&r", wcp.getGlobalColor());
			}
				  
			String prefix = WCVault.chat.getPlayerPrefix(p);
			String suffix = WCVault.chat.getPlayerSuffix(p);

			
			if (wcpCurrent.getPVP()){
				
				bleh.sendMessage(AS("&6PvP &f// &6" + wcpCurrent.getNick() + "&f: &" + wcp.getGlobalColor() + message));
				
			} else if (prefix.toLowerCase().contains("guest")){
				
				bleh.sendMessage(AS("&7Guest &f// &7" + wcpCurrent.getNick() + "&f: &" + wcp.getGlobalColor() + message));
					
			} else {
				
				if (p.hasPermission("wa.staff") || p.hasPermission("wa.citizen")){
						
					if (wcp.getTimeCode()){
						String time = "&f[" + getTime() + "&f] ";
						bleh.sendMessage(AS(time + prefix + suffix + " §f// " + p.getDisplayName() + "§f: &" + wcp.getGlobalColor() + message));  
					} else {
						bleh.sendMessage(AS(prefix + suffix + " §f// " + p.getDisplayName() + "§f: &" + wcp.getGlobalColor() + message));  
					}
						
				} else {
						
					if (wcp.getTimeCode()){
						String time = "&f[" + getTime() + "&f] ";
						bleh.sendMessage(AS(time + prefix + suffix + " §f// " + p.getDisplayName() + "§f: &" + wcp.getGlobalColor()) + message); 	
					} else {
						bleh.sendMessage(AS(prefix + suffix + " §f// " + p.getDisplayName() + "§f: &" + wcp.getGlobalColor()) + message);  
					}
				}
			}
		}
		
		Bukkit.getServer().getConsoleSender().sendMessage(AS(WCVault.chat.getPlayerSuffix(p) + " §f// " + p.getDisplayName() + "§f: " + message));
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	 
    switch (cmd.getName()) { 

    	case "wcstats":
    		
    	stats("!stats" + createString(args, 0), ((Player)sender));
    	
    	break;
    	
    	case "msg": case "tell": case "t":
    		
    	p = ((Player)sender);
     
        if (args.length <= 1){
          s(p, "It seems you were trying to message someone. Try /msg <player> <message> instead...");
          break;
        }

        String message2 = createString(args, 1);

        if (sender.getName().toLowerCase().contains(args[0].toLowerCase())){
          s(p, "Welp, you're forever alone. #sendingmessagestomyself");
          break;
        }

        wcp = plugin.wcm.getWCPlayer(p.getName());
        
        if (wcp.getPMColor() == null){
        	wcp.setPMColor("d");
        	updatePlayer(wcp, p.getName());
        }
        	
        for (Player currentPlayer : Bukkit.getServer().getOnlinePlayers()) {
        	
          if (currentPlayer.getName().toLowerCase().contains(args[0].toLowerCase())){
        	 wcpCurrent = plugin.wcm.getWCPlayer(currentPlayer.getName());
        	 wcpCurrent.setLastChat(p.getName());
        	 updatePlayer(wcpCurrent, currentPlayer.getName());
             currentPlayer.sendMessage(AS("&" + wcp.getPMColor() + "<- " + p.getDisplayName() + " §f// &" + wcp.getPMColor() + message2));
             sender.sendMessage(AS("&" + wcp.getPMColor() + "-> " + currentPlayer.getDisplayName() + " §f// &" + wcp.getPMColor() + message2));
             break;
          }
        }

    break;
        
    case "r":

    	p = ((Player)sender);
    	wcp = plugin.wcm.getWCPlayer(sender.getName());
    	
    	if (Bukkit.getPlayer(wcp.getLastChat()) == null){
    		s(p, "That player is not online.");
    		break;
    	}
    	
    	wcpCurrent = plugin.wcm.getWCPlayer(wcp.getLastChat());
        wcpCurrent.setLastChat(p.getName());
        updatePlayer(wcpCurrent, wcp.getLastChat());
        
        Bukkit.getPlayer(wcp.getLastChat()).sendMessage(AS("&" + wcpCurrent.getGlobalColor() + "<- " + p.getDisplayName() + " §f// &" + wcpCurrent.getGlobalColor() + createString(args, 0)));
        sender.sendMessage(AS("&" + wcp.getGlobalColor() + "-> " + ((Player)sender).getDisplayName() + " §f// &" + wcp.getGlobalColor() + createString(args, 0)));
        break;
    }

    if (label.equalsIgnoreCase("o") && sender instanceof Player && sender.hasPermission("wa.staff")) {
    	
	  chatUsers = plugin.datacore.getStringList("StaffChat.Users");
    	
      Player p = ((Player)sender);
      
      if (args.length <= 0) {
    	  s(p, "Try /o -join, /o -leave, or /o -list");
    	  return true;
      }
      
      switch (args[0].toLowerCase()){
            
	      case "-list":
	    	  
	          s(p, "Chat Users:");
	
	          for (String a : chatUsers) {
		          if (Bukkit.getOfflinePlayer(a).isOnline()){
		            s2(p, a);
		          }
	          }
	          
		  return true;
	      
	      case "-join":
	    	  
	    	  if (chatUsers.contains(p.getName())){
	    		  s(p, "You're already in o chat");
	    		  break;
	    	  }
	    	  
	    	  chatUsers.add(p.getName());
	    	  plugin.datacore.set("StaffChat.Users", chatUsers);
	    	  
	    	  s(p, "Now talking in o chat!");
	    	  
	          for (String a : chatUsers){
	              if (Bukkit.getOfflinePlayer(a).isOnline()) {
	                Bukkit.getPlayer(a).sendMessage("§c§oOh! §4// " + p.getDisplayName() + " has joined o chat!");
	              }
	          }
	    	  
		  return true;    
	      
	      case "-leave":
	    	  
	    	  if (!chatUsers.contains(p.getName())){
	    		  s(p, "You're not in o chat.");
	    		  break;
	    	  }
	    	  
	    	  chatUsers.remove(p.getName());
	    	  plugin.datacore.set("StaffChat.Users", chatUsers);
	    	  
	    	  s(p, "You've left o chat.");
	    	  
	          for (String a : chatUsers){
	              if (Bukkit.getOfflinePlayer(a).isOnline()) {
	                Bukkit.getPlayer(a).sendMessage("§c§oOh! §4// " + p.getDisplayName() + " §chas left o chat!");
	              }
	          }
	    	 
	      return true;
      
      }

        for (String a : chatUsers){
          if (Bukkit.getOfflinePlayer(a).isOnline()) {
            Bukkit.getPlayer(a).sendMessage("§c§oOh! §4// " + p.getDisplayName() + "§f: " + createString(args, 0));
          }
        }
        
    return true;
  }

  if ((cmd.getName().equalsIgnoreCase("l")) && ((sender instanceof Player))) {
	  
      Player p = (Player)sender;
	  wcp = plugin.wcm.getWCPlayer(p.getName());
	  
	  if (!wcp.getInChat()){
		  s(p, "You're not in a chat.");
		  return true;
	  }
	  
	  WCAlliance wcaCurrent = plugin.wcm.getWCAlliance(wcp.getCurrentChat());

        for (String a : wcaCurrent.getChatUsers()) {
          if (Bukkit.getOfflinePlayer(a).isOnline()) {
        	  Bukkit.getPlayer(a).sendMessage(AS(plugin.wcm.getCompleted(wcp.getCurrentChat(), wcaCurrent.getColor1(), wcaCurrent.getColor2()) + " &7// &8*" + 
        	  wcp.getAllianceRank2() + "&8* " + p.getDisplayName() + "&f: &" + wcp.getAllianceColor() + createString(args, 0)));
          }
        }

      }
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