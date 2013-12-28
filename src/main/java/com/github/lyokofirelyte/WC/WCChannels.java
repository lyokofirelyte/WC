package com.github.lyokofirelyte.WC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
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

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPatrol;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.WCSystem;
import com.github.lyokofirelyte.WCAPI.Events.PlayerEmoteEvent;

import static com.github.lyokofirelyte.WC.WCMain.*;


public class WCChannels implements CommandExecutor, Listener {
	
  WCMain pl;
  String WC = "§dWC §5// §d";
  WCAlliance wca;
  WCPlayer wcp;
  WCPlayer wcpCurrent;
  WCSystem system;
  List<String> chatUsers;
  Player p;
  Player lastChat;
  
  public WCChannels(WCMain instance){
    this.pl = instance;
  }
  
	public void stats(String s, Player p){
		
		if (s.length() == 6){
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + p.getName() + " c:!stats " + p.getName());
			return;
		}
		
		String message[] = s.split(" ");
		wcp = pl.wcm.getWCPlayer(message[1]);
		wca = pl.wcm.getWCAlliance(wcp.getAlliance());
		
		if (wcp == null){
			p.sendMessage(WCMail.WC + "That player has never logged in before!");
			return;
		}
					
		p.sendMessage(new String[]{
			AS(WCMail.WC + "Inspecting player " + message[1] + "."),
			AS("&1| &a&oglobal rank&f: " + WCVault.chat.getPlayerPrefix("world", message[1])),
			AS("&1| &f> > > < < <"),
			AS("&1| &b&oalliance&f: " + pl.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2())),
			AS("&1| &b&oalliance ranking&f: " + wcp.getAllianceRank()),
			AS("&1| &f> > > < < <"),
			AS("&1| &6&oparagon level&f: " + wcp.getParagonLevel()),
			AS("&1| &6&oshiny balance&f: " + wcp.getBalance()),
			AS("&1| &6&odeath count&f: " + wcp.getDeathCount()),
			AS("&1| &f> > > < < <"),
		});
		
		if (Bukkit.getPlayer(message[1]) != null){
			p.sendMessage(AS("&1| &c&ostatus&f: &aONLINE"));
		} else {
			p.sendMessage(AS("&1| &c&ostatus&f: &4OFFLINE"));	
		}
		
	}
  
  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerChat(AsyncPlayerChatEvent e){
	  
	  p = e.getPlayer();
	  wcp = pl.wcm.getWCPlayer(p.getName());
	  
	  if (wcp.getPatrolFormCmd()){
		  e.setCancelled(true);
		  wcp.setPatrolFormCmd(false);
		  wcp.setPatrol(e.getMessage().replaceAll("&", ""));
		  pl.wcm.updatePlayerMap(p.getName(), wcp);
		  WCPatrol wcpp = new WCPatrol(e.getMessage().replaceAll("&", ""));
		  List<String> members = wcpp.getMembers();
		  members.add(p.getName());
		  wcpp.setMembers(members);
		  wcpp.setName(e.getMessage().replaceAll("&", ""));
		  pl.wcm.updatePatrol(e.getMessage().replaceAll("&", ""), wcpp);
		  Bukkit.broadcastMessage(AS(p.getDisplayName() + " &dhas formed a patrol (&3" + e.getMessage().replaceAll("&", "") + "&d)."));
		  s(p, "Talk in patrol chat with /p <message>. Your personal patrol configuration options are in /root -> patrols.");
	  }
	  
	  String globalColor = wcp.getGlobalColor();
		
	  if (globalColor == null){
			wcp.setGlobalColor("7");
			updatePlayer(wcp, p.getName());
	  }

	  if (e.isCancelled()){
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
		  e.setMessage(e.getMessage().replaceAll("%t", pl.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2())));
	  }  

	  e.setCancelled(true);
	  system = pl.wcm.getWCSystem("system");
	  
	  List<String> emotes = system.getEmotes();
	  
	  for (String s : emotes){
		  
		  if (e.getMessage().contains(s) && e.getMessage().startsWith("@")){
			  PlayerEmoteEvent emote = new PlayerEmoteEvent(s, e.getPlayer(), e.getMessage());
	  		  pl.getServer().getPluginManager().callEvent(emote);
	  		  return;
	  	  }
	  }
	  
	  if (!e.getMessage().startsWith("@")){
		  globalChat(p, e.getMessage());
		  return;
	  }
	  
	  if (e.getMessage().startsWith("@list")){
		  
		  StringBuilder sb = new StringBuilder();
		  
		  for (String s : emotes){
			  sb.append("&6" + s + "&f, ");
		  }
		  
		  s(p, sb.toString().trim() + " list");
		  return;
	  }
	  
	  if (e.getMessage().startsWith("@")){
		  s(p, "Emote not found. Try @list for the whole emote list.");
	  }
  }

  public void globalChat(Player p, String message){
	  
	  	Boolean rawr = false;
	  	
	  	if ((message.contains("http://") || message.contains("https://")) && !message.contains("tinyurl") && !message.contains("bit.ly")){
	  		String[] split = message.split(" ");	
	  		for (String s : split){		
	  			if (s.startsWith("http") && s.length() > 16){		
	  				message = message.replace(s, shorten(s));		
	  			}	
	  		}
	  	}
	  	
		for (Player bleh : Bukkit.getOnlinePlayers()){
			
			wcp = pl.wcm.getWCPlayer(bleh.getName());
			wcpCurrent = pl.wcm.getWCPlayer(p.getName());
			
			if (message.contains(bleh.getName())){
				bleh.playSound(bleh.getLocation(), Sound.ORB_PICKUP, 3F, 0.5F);
				message = message.replace(bleh.getName(), bleh.getDisplayName() + "&r");
			}
			
			if (message.contains("&r")){
				message = message.replace("&r", "&" + wcp.getGlobalColor());
			}
			
			if (lastChat != null && lastChat.equals(p)){
				rawr = true;
				bleh.sendMessage(AS("&8>> " + p.getDisplayName() + "&f: &" + wcp.getGlobalColor() + message));
			} else {
				
				String prefix = WCVault.chat.getPlayerPrefix(p);
				String suffix = WCVault.chat.getPlayerSuffix(p);
	
				if (wcpCurrent.getPVP()){
					
					bleh.sendMessage(AS("&6PvP &f// &6" + wcpCurrent.getNick() + "&f: &" + wcp.getGlobalColor() + message));
					
				} else if (prefix.toLowerCase().contains("guest")){
					
					bleh.sendMessage(AS("&7Guest &f// &7" + wcpCurrent.getNick() + "&f: &" + wcp.getGlobalColor() + message));
						
				} else if (prefix.toLowerCase().equals("")){
					
					bleh.sendMessage(AS("&7M &f// " + p.getDisplayName() + "&f: &" + wcp.getGlobalColor() + message));
					
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
		}
	  
		lastChat = p;
		
		if (rawr){
			Bukkit.getServer().getConsoleSender().sendMessage(AS("&8>> " + p.getDisplayName() + "&f: " + message));
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage(AS(WCVault.chat.getPlayerSuffix(p) + " §f// " + p.getDisplayName() + "§f: " + message));
		}
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
	
	        wcp = pl.wcm.getWCPlayer(p.getName());
	        
	        if (wcp.getPMColor() == null){
	        	wcp.setPMColor("d");
	        	updatePlayer(wcp, p.getName());
	        }
	        	
	        for (Player currentPlayer : Bukkit.getServer().getOnlinePlayers()) {
	        	
	        	if (currentPlayer.getName().toLowerCase().contains(args[0].toLowerCase())){
	        		wcpCurrent = pl.wcm.getWCPlayer(currentPlayer.getName());
	        		wcpCurrent.setLastChat(p.getName());
	        		updatePlayer(wcpCurrent, currentPlayer.getName());
	        		currentPlayer.sendMessage(AS("&" + wcpCurrent.getPMColor() + "<< " + p.getDisplayName() + " §f// &" + wcpCurrent.getPMColor() + message2));
	        		sender.sendMessage(AS("&" + wcp.getPMColor() + ">> " + currentPlayer.getDisplayName() + " §f// &" + wcp.getPMColor() + message2));
	        		
	        		if (pl.afkTimer.get(currentPlayer.getName()) >= 180){
	        			sender.sendMessage(AS("&7&oThat player is afk."));
	        		}
	        		break;
	        	}
	        }

	        break;
        
	    case "r":
	
	    	p = ((Player)sender);
	    	wcp = pl.wcm.getWCPlayer(p.getName());
	    	
	    	if (Bukkit.getPlayer(wcp.getLastChat()) == null){
	    		s(p, "That player is not online.");
	    		break;
	    	}
	    	
	    	wcpCurrent = pl.wcm.getWCPlayer(wcp.getLastChat());
	        wcpCurrent.setLastChat(p.getName());
	        updatePlayer(wcpCurrent, wcp.getLastChat());
	        
	        Bukkit.getPlayer(wcp.getLastChat()).sendMessage(AS("&" + wcpCurrent.getPMColor() + "<< " + p.getDisplayName() + " §f// &" + wcpCurrent.getPMColor() + createString(args, 0)));
	        sender.sendMessage(AS("&" + wcp.getPMColor() + ">> " + Bukkit.getPlayer(wcp.getLastChat()).getDisplayName() + " §f// &" + wcp.getPMColor() + createString(args, 0)));
    		if (pl.afkTimer.get(wcp.getLastChat()) >= 180){
    			sender.sendMessage(AS("&7&oThat player is afk."));
    		}
	        break;
    }

    if (label.equalsIgnoreCase("o") && sender instanceof Player && sender.hasPermission("wa.staff")) {
    	
    	chatUsers = pl.datacore.getStringList("StaffChat.Users");
    	
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
	    	  pl.datacore.set("StaffChat.Users", chatUsers);
	    	  
	    	  s(p, "Now talking in o chat!");
	    	  
	          for (String a : chatUsers){
	              if (Bukkit.getOfflinePlayer(a).isOnline()) {
	                Bukkit.getPlayer(a).sendMessage(AS("§c§oOh! §4// " + p.getDisplayName() + " has joined o chat!"));
	              }
	          }
	    	  
		  return true;    
	      
	      case "-leave":
	    	  
	    	  if (!chatUsers.contains(p.getName())){
	    		  s(p, "You're not in o chat.");
	    		  break;
	    	  }
	    	  
	    	  chatUsers.remove(p.getName());
	    	  pl.datacore.set("StaffChat.Users", chatUsers);
	    	  
	    	  s(p, "You've left o chat.");
	    	  
	          for (String a : chatUsers){
	              if (Bukkit.getOfflinePlayer(a).isOnline()) {
	            	  Bukkit.getPlayer(a).sendMessage(AS("§c§oOh! §4// " + p.getDisplayName() + " §chas left o chat!"));
	              }
	          }
	    	 
	      return true;
      
      }

      for (String a : chatUsers){
    	  if (Bukkit.getOfflinePlayer(a).isOnline()) {
    		  Bukkit.getPlayer(a).sendMessage(AS("§c§oOh! §4// " + p.getDisplayName() + "§f: §c" + createString(args, 0)));
          }
      }
        
    return true;
  }

	  if ((cmd.getName().equalsIgnoreCase("l")) && ((sender instanceof Player))) {
		  
	      Player p = (Player)sender;
		  wcp = pl.wcm.getWCPlayer(p.getName());
		  
		  if (!wcp.getInChat()){
			  s(p, "You're not in a chat.");
			  return true;
		  }
		  
		  WCAlliance wcaCurrent = pl.wcm.getWCAlliance(wcp.getCurrentChat());
	
	      for (String a : wcaCurrent.getChatUsers()) {
	    	  if (Bukkit.getOfflinePlayer(a).isOnline()) {
	    		  Bukkit.getPlayer(a).sendMessage(AS(pl.wcm.getCompleted(wcp.getCurrentChat(), wcaCurrent.getColor1(), wcaCurrent.getColor2()) + " &7// &8*" + 
	        	  wcp.getAllianceRank2() + "&8* " + p.getDisplayName() + "&f: &" + wcp.getAllianceColor() + createString(args, 0)));
	          }
	      }
	
	  }
	  
	  if (cmd.getName().equalsIgnoreCase("p")){
		  
		  Player p = (Player)sender;
		  wcp = pl.wcm.getWCPlayer(p.getName());
		  
		  if (wcp.getPatrol() == null){
			  s(p, "You're not in a patrol. Form or join one from /root -> patrols.");
		  } else {
			  WCPatrol wcpp = pl.wcm.getWCPatrol(wcp.getPatrol());
			  for (String s : wcpp.getMembers()){
				  if (Bukkit.getPlayer(s) != null){
					  Bukkit.getPlayer(s).sendMessage(AS("&8>> &3" + wcpp.getName() + " &8>> " + p.getDisplayName() + "&f: " + Utils.createString(args, 0)));
				  }
			  }
		  }
	  }
	  
    return true;
  }
  
  public void updatePlayer(WCPlayer wcp, String name){
	  pl.wcm.updatePlayerMap(name, wcp);  
	  wcp = pl.wcm.getWCPlayer(name);
  }
  
  public void updateAlliance(WCAlliance wca, String name){
	  pl.wcm.updateAllianceMap(name, wca);  
	  wca = pl.wcm.getWCAlliance(name);
  }
  
  public void updateAll(WCAlliance wca, WCPlayer wcp, String alliance, String player){
	  pl.wcm.updateAllianceMap(alliance, wca);
	  pl.wcm.updatePlayerMap(player, wcp);
	
	  wcp = pl.wcm.getWCPlayer(player);
	  wca = pl.wcm.getWCAlliance(alliance);
  }
  
  public static String shorten(String URL){
		
		String link = "";
		
		if (!(URL.startsWith("http"))){
			
			URL = "http://" + URL;
			
		}
		
		int error = 0;
		
		try {
			
			URL url = new URL("http://www.tinyurl.com/api-create.php?url=" + URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			if (connection.getResponseCode() != 200){
				
				error = connection.getResponseCode();
				throw new RuntimeException("Failed to shorten link. HTTP error code: " + error);
				
			} else {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				link = reader.readLine();
				
			}
			
			connection.disconnect();
			
		} catch (Exception e){
			
			link = "&4&l(&cHTTP Error: " + error + "&4&l)&r";
			
		}
		
		return link;
		
	}

}