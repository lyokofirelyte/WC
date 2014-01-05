package com.github.lyokofirelyte.WC;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
//import org.kitteh.tag.TagAPI;


import static com.github.lyokofirelyte.WC.Util.Utils.*;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

import static com.github.lyokofirelyte.WC.WCMain.s;

public class WACommandEx {
	
  WCMain plugin;
  WCPlayer wcp;
  WCAlliance wca;
  WCAlliance wcaCurrent;
  WCPlayer wcpCurrent;
  StringBuilder sb;
  String allowedColors = "1 2 3 4 5 6 7 8 9 0 a b c d e f o l m k n r";
  List <Integer> prices = Arrays.asList(60000, 75000, 90000, 105000, 125000, 145000, 165000, 180000);

  public WACommandEx(WCMain instance){
  this.plugin = instance;
  }

  @WCCommand(aliases = {"waa"}, desc = "WC Alliances root menu", help = "/waa")
  public void onWAA(Player sender, String[] args){
    if (sender instanceof Player) {
    	
      Player p = (Player)sender;
      String pl = p.getName();
      wcp = plugin.wcm.getWCPlayer(pl);
      wca = plugin.wcm.getWCAlliance(wcp.getAlliance());

      if (args.length > 0) {
    	  
    	  switch(args[0].toLowerCase()){
    	  	
    	  case "help": default:
    		  
    		  if (args.length == 1){
    			  sender.sendMessage(new String[] { 
    			  WC + "Worlds Apart Alliances Help Core", 
    		      AS("&a| &3/waa help general &f// &3General help commands"), 
    		      AS("&a| &3/waa help leadership &f// &3Alliance upgrading help commands"), 
    		      AS("&a| &3/waa help chat &f// &3Alliance chat help commands"),
    		      AS("&4.w.e...a.r.e...w.a.t.c.h.i.n.g...y.o.u.......a.l.w.a.y.s.")});
    			  break;
    		  }
    		  
    		  switch (args[1].toLowerCase()){
    		  
	    		  case "leadership":
	
	    	            sender.sendMessage(new String[] { 
	    	            WC + "Leadership Help", 
	    	            AS("&a| &3/waa invite <player> &f// &3Invite someone to your alliance"), 
	    	            AS("&a| &3/waa upgrade &f// &3Upgrade your alliance after inspection"), 
	    	            AS("&a| &3/waa colors <color 1> <color 2> &f// &3Set your alliance chat colors"), 
	    	            AS("&a| &3/waa leader <new leader> &f// &3Choose someone else to be leader"), 
	    	            AS("&a| &3/waa setrank <player> <rank> &f// &3Give someone a personal alliance rank"), 
	    	            AS("&a| &3/waa levels &f// &3View upgrade levels and requirements"), 
	    	            AS("&a| &3/waa disband (There is no confirm for this!) &f// &3Goodbye, alliance!"), 
	    	            AS("&a| &3/waa kick <player> <oldrank> <newrank> (STAFF) &f// &3Kick someone from a town")});
	    	            break;
	    		  
	    		  case "general":
	                    sender.sendMessage(new String[] { 
	                    WC + "General Help", 
	                    AS("&a| &3/waa info &f// &3Displays your WAA info"), 
	                    AS("&a| &3/waa lookup <alliance> &f// &3View info about an alliance"), 
	                    AS("&a| &3/waa request &f// &3Request an alliance formation"), 
	                    AS("&a| &3/waa approve <alliance> &f// &3(STAFF) Approve an alliance for tier upgrade"), 
	                    AS("&a| &3/waa accept &f// &3Accept an alliance invite"), 
	                    AS("&a| &3/waa doors &f// &3Toggle doors"), 
	                    AS("&a| &3/waa mobs &f// &3Toggle mobs"), 
	                    AS("&a| &3/waa decline &f// &3Decline an alliance invite"), 
	                    AS("&a| &3/waa pay <alliance> &f// &3Give money to an alliance")});
	                    break;
	                    
	    		  case "chat":
	    			  
	    	            sender.sendMessage(new String[] { 
	    	            WC + "Alliance Chat Help",  
	    	            AS("&a| &3/waa chat leave &f// &3Leave your alliance chat"), 
	    	            AS("&a| &3/waa chat join &f// &3Join your alliance chat"), 
	    	            AS("&a| &3/waa chat kick <player> &f// &3Kick someone from your alliance chat (ADMIN)"), 
	    	            AS("&a| &3/waa chat ban <player> &f// &3Ban someone from your alliance chat (ADMIN)"), 
	    	            AS("&a| &3/waa chat unban <player> &f// &3Unban someone in your alliance chat (ADMIN)"), 
	    	            AS("&a| &3/waa chat admin add <player> &f// &3Add <player> to admin in your alliance chat (LEADER)"), 
	    	            AS("&a| &3/waa chat admin rem <player> &f// &3Remove <player> from admin in your alliance chat (LEADER)"), 
	    	            AS("&a| &3/waa chat visit <alliance> &f// &3Visit a different alliance chat as a guest"), 
	    	            AS("&a| &3/waa chat list &f// &3List the users in your current alliance chat channel"), 
	    	            AS("&a| &3/waa chat color <color &f// &3Change your default chat color. See /news 2 for the list."), 
	    	            AS("&a| &3/l <message> &f// &3Quick message alliance chat")});
	    	            break;	  
    	      }
    		  
    		  break;
    		  
    	  case "chat":
    		  
    		  if (args.length == 1){
    			  s(p, "You have to use alliance chat with /l now, sorry! If you were looking for the help menu, type /waa help chat.");
    			  break;
    		  }
    		  
    		  switch (args[1].toLowerCase()){
    		  
	    		  case "color":
	    			  
	    			    if (args.length != 3 || !allowedColors.contains(args[2])){
	    			    	s(p, "See /news 2 for supported colors!");
	    			    	break;
	    			    }  else {
		      				wcp.setAllianceColor(args[2]);
		      				updatePlayer(wcp, p.getName());
		      				s(p, "You've set your color as &" + args[2] + "this.");
		      				break;
		      			}
	    	      
	    		  case "join":
	    			  
	    			  	if (wcp.getInChat()){
	    			  		s(p, "You're already in a chat!");
	    			  		break;
	    			  	}
	    			  	
	    			  	if (!wcp.getInAlliance()){
	    			  		s(p, "You're not in an alliance. Visit another alliance with /waa chat visit <alliance>.");
	    			  		break;
	    			  	}
	    			  	
	    			  	wcp.setInChat(true);
	    			  	wcp.setCurrentChat(wcp.getAlliance());
	    			  	wcp.setAllianceRank2(wcp.getAllianceRank());
	    			  	wcp.setChatGuest(false);
	    			  	wca.addChatUser(p.getName());
	    			  	updateAll(wca, wcp, wcp.getAlliance(), p.getName());
	    			  		for (String currentMember : wca.getChatUsers()){
	    			  			if (Bukkit.getOfflinePlayer(currentMember).isOnline()){
	    			  				s(Bukkit.getPlayer(currentMember), p.getDisplayName() + " &dhas joined alliance chat!");
	    			  			}
	    			  		}
	    		  break;
	    			  	
	    		  case "leave":
	    			  
	    			    if (!wcp.getInChat()){
	    			    	s(p, "You're not in a chat!");
	    			    	break;
	    			    }
	    			    
	    			    WCAlliance wcaCurrent = plugin.wcm.getWCAlliance(wcp.getCurrentChat());
	    			    wcaCurrent.remChatUser(p.getName());
	    			    wcp.setInChat(false);
	    			    wcp.setAllianceRank2(wcp.getAllianceRank());
	    			    wcp.setChatGuest(true);
	    			    updateAll(wcaCurrent, wcp, wcp.getCurrentChat(), p.getName());
	    			    for (String currentMember : wcaCurrent.getChatUsers()){
	    			    	if (Bukkit.getOfflinePlayer(currentMember).isOnline()){
	    			    		s(Bukkit.getPlayer(currentMember), p.getDisplayName() + " &dhas left alliance chat!");
	    			    	}
    			  		}
	    			    s(p, "You've left alliance chat.");
	    			    
	    	      break;
	    	      
	    		  case "visit":
	    			  
	    			    if (wcp.getInChat()){
	    			    	s(p, "You're already in a chat!");
	    			    	break;
	    			    }
	    			    
	    			    if (args.length != 3){
	    			    	s(p, "Try using /waa chat visit <alliance>!");
	    			    	break;
	    			    }
	    			    
	    			    wcaCurrent = plugin.wcm.getWCAlliance(args[2]);
	    			    
	    			    if (wcaCurrent == null){
	    			    	s(p, "That alliance does not exist!");
	    			    	break;
	    			    }
	    			    
	    			    if (args[2].equalsIgnoreCase(wcp.getAlliance())){
	    			    	s(p, "Use /waa chat join for that!");
	    			    	break;
	    			    }
	    			    
	    			    if (wcaCurrent.getBanList().contains(p.getName())){
	    			    	s(p, "You're banned from that channel!");
	    			    	break;
	    			    }
	    			  	
	    			    wcaCurrent.addChatUser(p.getName());
	    			    wcp.setCurrentChat(args[2]);
	    			    wcp.setInChat(true);
	    			    wcp.setChatGuest(true);
	    			    wcp.setAllianceRank2("Guest");
	    			    updateAll(wcaCurrent, wcp, args[2], p.getName());
	    			    for (String currentMember : wcaCurrent.getChatUsers()){
	    			    	if (Bukkit.getPlayer(currentMember) != null){
	    			    		s(Bukkit.getPlayer(currentMember), p.getDisplayName() + " &dhas joined alliance chat!");
	    			    	}
    			  		}
	    			    
	    		  break;
	    		  
	    		  case "kick":
	    			  
	    			    if (!wcp.getAllianceRank().equals("Leader") && !plugin.wcm.getWCAlliance(wcp.getCurrentChat()).getChatAdmins().contains(p.getName())){
	    			    	s(p, "You must be the leader or a chat admin to do this!");
	    			    	break;
	    			    }
	    			    
	    			    if (args.length != 3){
	    			    	s(p, "Try /waa chat kick <player>!");
	    			    	break;
	    			    }
	    			    
	    			    if (Bukkit.getPlayer(args[2]) == null){
	    			    	s(p, "That player is offline.");
	    			    	break;
	    			    }
	    			    
	    			    Player kicked = Bukkit.getPlayer(args[2]);
	    			    
	    			    wcpCurrent = plugin.wcm.getWCPlayer(kicked.getName());
	    			    wcaCurrent = plugin.wcm.getWCAlliance(wcpCurrent.getCurrentChat());
	    			    
	    			    wcpCurrent.setInChat(false);
	    			    wcpCurrent.setChatGuest(true);
	    			    wcpCurrent.setAllianceRank2("Guest");
	    			    wcaCurrent.remChatUser(kicked.getName());
	    			    updateAll(wcaCurrent, wcpCurrent, wcpCurrent.getCurrentChat(), kicked.getName());
	    			    s(kicked, "You were kicked from the chat by " + p.getDisplayName() + " &d!");
	    			    for (String currentMember : wcaCurrent.getChatUsers()){
    			  			s(Bukkit.getPlayer(currentMember), kicked.getDisplayName() + " &dwas kicked from alliance chat!");
    			  		}
	    			    
	    		 break;
	    		 
	    		 case "list":
	    			 
	    			   if (!wcp.getInChat()){
	    				   s(p, "You're not in a chat.");
	    				   break;
	    			   }
	    			   
	    			   wcaCurrent = plugin.wcm.getWCAlliance(wcp.getCurrentChat());
	    			   s(p, wcp.getCurrentChat() + " @ " + wcaCurrent.getChatUsers().size());
	    			   StringBuilder sb = new StringBuilder();
	    			   for (String s : wcaCurrent.getChatUsers()){
	    				   if (Bukkit.getPlayer(s) != null){
	    					   sb.append(Bukkit.getPlayer(s).getDisplayName() + "&d, ");
	    				   }
	    			   }
	    			  
	    			  String msg = sb.toString().trim();
	    			  int meh = msg.length() - 5;
	 			      msg = msg.substring(0, meh);
	 			      s(p, msg);
	 			    
	 			 break;
	 			
	    		 case "ban":
	    			 
	    			    if (!wcp.getAllianceRank().equals("Leader") && !plugin.wcm.getWCAlliance(wcp.getCurrentChat()).getChatAdmins().contains(p.getName())){
	    			    	s(p, "You must be the leader or a chat admin to do this!");
	    			    	break;
	    			    }
	    			    
	    			    if (args.length != 3){
	    			    	s(p, "Try /waa chat ban <player>!");
	    			    	break;
	    			    }
	    			    
	    			    if (Bukkit.getPlayer(args[2]) == null){
	    			    	s(p, "That player is offline.");
	    			    	break;
	    			    }
	    			    
	    			    if (Bukkit.getPlayer(args[2]).getName().equals(p.getName())){
	    			    	s(p, "You can't ban yourself.");
	    			    	break;
	    			    }
	    			    
	    			    Player banned = Bukkit.getPlayer(args[2]);
	    			    wcaCurrent = plugin.wcm.getWCAlliance(wcp.getCurrentChat());
	    			      			    
	    			    wcaCurrent.addBan(banned.getName());
	    			    wcpCurrent = plugin.wcm.getWCPlayer(banned.getName());  
	    			    wcpCurrent.setInChat(false);
	    			    wcpCurrent.setChatGuest(true);
	    			    wcpCurrent.setAllianceRank2("Guest");
	    			    updateAll(wcaCurrent, wcpCurrent, wcp.getCurrentChat(), banned.getName());
	    			    s(banned, "You were banned from the chat by " + p.getDisplayName() + " &d!");
	    			    for (String currentMember : wcaCurrent.getChatUsers()){
    			  			s(Bukkit.getPlayer(currentMember), banned.getDisplayName() + " &dwas banned from alliance chat!");
    			  		}
	    			    
	    		 break;
	    		 
	    		 case "unban":
	    			 
	    			    if (!wcp.getAllianceRank().equals("Leader") && !plugin.wcm.getWCAlliance(wcp.getCurrentChat()).getChatAdmins().contains(p.getName())){
	    			    	s(p, "You must be the leader or a chat admin to do this!");
	    			    	break;
	    			    }
	    			    
	    			    if (args.length != 3){
	    			    	s(p, "Try /waa chat unban <player>!");
	    			    	break;
	    			    }
	    			    
	    			    if (Bukkit.getPlayer(args[2]) == null){
	    			    	s(p, "That player is offline.");
	    			    	break;
	    			    }
	    			    
	    			    wcaCurrent = plugin.wcm.getWCAlliance(wcp.getCurrentChat());
	    			    
	    			    wcaCurrent.remBan(args[2]);
	    			    updateAlliance(wcaCurrent, wcp.getCurrentChat());
	    			    s(Bukkit.getPlayer(args[2]), "You were unbanned from an alliance chat!");
	    			    
	    	      break;
	    	      
	    		  case "admin":
	    			  
	    			    if (!wcp.getAllianceRank().equals("Leader")){
	    			    	s(p, "Only the leader can add chat admins!");
	    			    	break;
	    			    }
	    			    
	    			    if (args.length != 4){
	    			    	s(p, "/waa chat admin add <player>. OR /waa chat admin rem <player>.");
	    			    	break;
	    			    }
	    			    
	    			    if (!Bukkit.getOfflinePlayer(args[3]).hasPlayedBefore()){
	    			    	s(p, "That player has never logged in before!");
	    			    	break;
	    			    }
	    			    
	    			    switch (args[2]){
	    			    
	    			    	case "add":
	    			    		
	    			    		wca.addChatAdmin(args[3]);
	    			    		updateAlliance(wca, wcp.getAlliance());
	    			    		s(p, "Added!");
	    			    	    break;
	    			        
	    			    	case "rem":
	    			    		
	    			    		wca.remChatAdmin(args[3]);
	    			    		updateAlliance(wca, wcp.getAlliance());
	    			    		s(p, "Removed!");
	    			    		break;
	    			        
	    			        default:
	    			        	
	    			        	s(p, "/waa chat admin add <player>. OR /waa chat admin rem <player>.");
	    			        	break;
	    			    }
	    			    
	    		break;
    		    }
    		  
    		  break;
    		  
    	  	  case "pay":	
    	  		  
    	  		  if (args.length != 3){
    	  			  s(p, "/waa pay <alliance> <amount>");
    	  			  break;
    	  		  }
    	  		  
    	  		  if (plugin.wcm.getWCAlliance(args[1]) == null){
    	  			  s(p, "That alliance does not exist.");
    	  			  break;
    	  		  }
    	  		  
    	  		  if (wcp.getBalance() < Integer.parseInt(args[2])){ 
    	  			  s(p, "You don't have enough money! D:");
    	  			  break;
    	  		  }
    	  		  
    	  		  wcaCurrent = plugin.wcm.getWCAlliance(args[1]);
    	  		  
    	  		  wcp.setBalance(wcp.getBalance() - Integer.parseInt(args[2]));
    	  		  wcaCurrent.setBank(wcaCurrent.getBank() + Integer.parseInt(args[2]));
    	  		  updatePlayer(wcp, p.getName());
    	  		  updateAlliance(wcaCurrent, args[1]);
    	  		  
    	  		  s(p, "Success!");
    	  		  
    	  	  break;
    		  
	          case "info":
	        	  
	        	  if (!wcp.getInAlliance()){
	        		  s(p, "You're not in an alliance!");
	        		  break;
	        	  }
	        	  
	        	  p.sendMessage(new String[] {
	        			  
	        		  AS("&a| &3Alliance &f// &3" + plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2())),
	        		  AS("&a| &3Your Rank &f// &3" + wcp.getAllianceRank()),
	        		  AS("&a| &3Leader &f// &3" + wca.getLeader()),
	        		  AS("&a| &3Funds &f// &3" + wca.getBank()),
	        		  AS("&a| &3Tier &f// &3" + wca.getTier()),
	        		  AS("&a| &3Door Locks &f// &3" + wca.getDoorLocks()),
	        		  AS("&a| &3Mob Spawn &f// &3" + wca.getMobSpawn()),
	        		  AS("&a| &cTown Coords &f// &3" + wca.getCoords()),
	        		  AS("&a| &3Protection Radius &f// &3" + wca.getRadius()),
	        		  AS("&7&oSee ohsototes.com for more info on each tier!")
	        	  });
	        	  
	          break;
	          
	          case "request":
	        	  
	        	     s(p, "See http://tinyurl.com/a25as5b and scroll down to Alliance Applications!");
	        	 
	          break;
	          
	          case "doors":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader") || !p.getWorld().getName().equals("world")){
	        	  		s(p, "Only the leader can do that! (in the survival world)");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (!wcp.getInAlliance()){
	        	  		s(p, "You must be in an alliance...");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (wca.getTier() < 2){
	        	  		s(p, "You must be tier 2 or higher to use this!");
	        	  		break;
	        	  	}
	        	  		  	
	        	  	if (!p.isOp()){
	        	  		wcp.setWCOP(true);
	        	  		p.setOp(true);
	        	  	}
	        	  	
	        	  	if (!wca.getDoorLocks()){
	        	  		
		        	  	wca.setDoorLocks(true);
		        	  	p.performCommand("rg flag " + wcp.getAlliance() + " use allow");
		        	  	s(p, "You've turned your door locks on!");
		        	  	
	        	  	} else {
	        	  		
	        	  		wca.setDoorLocks(false);
		        	  	p.performCommand("rg flag " + wcp.getAlliance() + " use deny");
		        	  	s(p, "You've turned your door locks off!");
	        	  	}
	        	  	
	        	  	if (p.isOp() && wcp.isWCOp()){
	        	  		wcp.setWCOP(false);
	        	  		p.setOp(false);
	        	  	}

	          break;
	          
	          case "mobs":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader") || !p.getWorld().getName().equals("world")){
	        	  		s(p, "Only the leader can do that! (in the survival world)");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (!wcp.getInAlliance()){
	        	  		s(p, "You must be in an alliance...");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (wca.getTier() < 3){
	        	  		s(p, "You must be tier 3 or higher to use this!");
	        	  		break;
	        	  	}
	        	  		  	
	        	  	if (!p.isOp()){
	        	  		wcp.setWCOP(true);
	        	  		p.setOp(true);
	        	  	}
	        	  	
	        	  	if (!wca.getMobSpawn()){
	        	  		
		        	  	wca.setMobSpawn(true);
		        	  	p.performCommand("rg flag " + wcp.getAlliance() + " mob-spawning allow");
		        	  	s(p, "You've turned mob-spawning on!");
		        	  	
	        	  	} else {
	        	  		
	        	  		wca.setMobSpawn(false);
		        	  	p.performCommand("rg flag " + wcp.getAlliance() + " mob-spawning  deny");
		        	  	s(p, "You've turned mob-spawning off!");
	        	  	}
	        	  	
	        	  	if (p.isOp()){
	        	  		wcp.setWCOP(false);
	        	  		p.setOp(false);
	        	  	}

	          break;
	          
	          case "create":
	        	  
	        	     if (!p.hasPermission("wa.staff")){
	        	    	s(p, "You must be staff to form alliances, sorry!");
	        	    	break;
	        	     }
	        	     
	        	     if (args.length != 5){
	        	    	s(p, "/waa create <alliance> <leader> <color 1> <color2>");
	        	    	s(p, "Example: /waa create Winhaven Hugh_Jasses 2 a");
	        	    	break;
	        	     }
	        	  
	        	     if (plugin.wcm.getWCAlliance(args[1]) != null){
	        	    	s(p, "That alliance already exists.");
	        	    	break;
	        	     }
	        	     
	        	     if (!p.isOp()){
	        	    	p.setOp(true);
	        	    	wcp.setWCOP(false);
	        	    	updatePlayer(wcp, p.getName());
	        	     }
	        	     
	        	     if (!allowedColors.contains(args[3]) || !allowedColors.contains(args[4])){
	        	    	s(p, "See /news 2 for acceptable colors!");
	        	    	break;
	        	     }
	        	     
	        	     if (Bukkit.getPlayer(args[2]) == null){
	        	    	s(p, "That player is not online!");
	        	    	break;
	        	     }
	        	     
	        	     wcpCurrent = plugin.wcm.getWCPlayer(Bukkit.getPlayer(args[2]).getName());
	        	     
	        	     if (wcpCurrent.getInAlliance()){
	        	    	 s(p, "That player is already in an alliance!");
	        	    	 break;
	        	     }
	        	     
	        	     wcpCurrent.setAlliance(args[1]);
	        	     wcpCurrent.setAllianceRank("Leader");
	        	     wcpCurrent.setInAlliance(true);
	        	     
	        	     WCAlliance wcaCurrent = new WCAlliance(args[1]);
	        	     wcaCurrent.setTier(0);
	        	     wcaCurrent.setColors(args[3], args[4]);
	        	     wcaCurrent.setCreated(true);
	        	     wcaCurrent.setCoords(Math.round(p.getLocation().getX()) + "," + Math.round(p.getLocation().getY()) + "," + Math.round(p.getLocation().getZ()));
	        	     wcaCurrent.setLeader(Bukkit.getPlayer(args[2]).getName());
	        	     wcaCurrent.setRadius(85);
	        	     wcaCurrent.addMember(Bukkit.getPlayer(args[2]).getName());
	        	     wcaCurrent.getChatUsers().add("system");
	        	     
	        	     p.performCommand("/pos1");
	        	     p.performCommand("/pos2");
	        	     p.performCommand("/setwarp " + args[1]);
	        	     p.performCommand("/outset 85");
	        	     p.performCommand("rg define " + args[1]);
	        	     p.performCommand("rg setpriority " + args[1] + " 10");
	        	     p.performCommand("rg flag " + args[1] + " creeper-explosion deny");
	        	     p.performCommand("rg flag " + args[1] + " fire-spread deny");
	        	     p.performCommand("rg flag " + args[1] + " tnt deny");
	        	     p.performCommand("rg flag " + args[1] + " enderman-grief deny");
	        	     p.performCommand("rg addowner " + args[1] + " g:" + args[1]);
	        	     p.performCommand("pex group " + args[1] + " create");
	        	     p.performCommand("pex group " + args[1] + " user add " + args[2]);
	        	     
	        	     if (wcp.isWCOp()){
	        	    	 wcp.setWCOP(false);
	        	    	 p.setOp(false);
	        	     }
	        	     
	        	     bc(plugin.wcm.getCompleted(args[1], args[3], args[4]) + " &dhas formed an alliance under the leadership of " + Bukkit.getPlayer(args[2]).getDisplayName() + "&d!");
	        	     try {
	        	    	 plugin.wcm.addAlliance(wcaCurrent, args[1]);
	        	     } catch (IOException e1) {
	        	    	 e1.printStackTrace();
	        	     }
	        	     
	        	     updateAll(wcaCurrent, wcpCurrent, args[1], Bukkit.getPlayer(args[2]).getName());
	        	     
	        		 ScoreboardUpdateEvent scoreboardEvent = new ScoreboardUpdateEvent(Bukkit.getPlayer(args[2]));
	        		 plugin.getServer().getPluginManager().callEvent(scoreboardEvent);
	        		 
	        	     Bukkit.getPlayer(args[2]).performCommand("nick " + wcpCurrent.getNick());

	          break;
	         
	          case "colors":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader")){
	        	  		s(p, "Only the leader can change the colors");
	        	  		break;
	        	  	}
  			    	
  			    	if (wca.changedColors()){
  			    		s(p, "Your alliance has already changed the colors.");
  			    		break;
	        	  	}
	        	  	
	        	  	if (args.length != 3){
	        	  		s(p, "Try /waa colors <color1> <color2>");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (!allowedColors.contains(args[1]) || !allowedColors.contains(args[2])){
	        	  		s(p, "See /news 2 for allowed colors.");
	        	  		break;
	        	  	}
	        	  	
	        	  	wca.setChangedColors(true);
	        	  	wca.setColors(args[1], args[2]);
	        	  	updateAlliance(wca, wcp.getAlliance());
	        	  	
	        	  		for (Player b : Bukkit.getOnlinePlayers()){
	        	  			WCPlayer btemp = plugin.wcm.getWCPlayer(b.getName());
	        	  				if (btemp.getAlliance().equals(wcp.getAlliance())){
	        	  					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "enick " + b.getName() + " " + plugin.wcm.getCompleted(btemp.getNick(), args[1], args[2]));
	        	  				}
	        	  		}
	        	  		
	        	  	bc(wcp.getAlliance() + " has changed thier colors! All online users have been updated with the correct color.");
	        	  	
	          break;
	          
	          case "invite":
	        	  
	        	    if (!wcp.getAllianceRank().equals("Leader")){
	        	  	   	s(p, "Only the leader can invite people.");
	        	  	   	break;
  			        }
	        	    
	        	    if (args.length != 2){
	        	    	s(p, "Try /waa invite <player>");
	        	    	break;
	        	    }
	        	    
	        	    if (Bukkit.getPlayer(args[1]) == null){
	        	    	s(p, "That player is not online.");
	        	    	break;
	        	    }
	        	    
	        	    WCPlayer temp = plugin.wcm.getWCPlayer(Bukkit.getPlayer(args[1]).getName());
	        	    
	        	    if (temp.getInAlliance()){
	        	    	s(p, "That player is already in an alliance.");
	        	    	break;
	        	    }
	        	    
	        	    temp.setInvite(wcp.getAlliance());
	        	    temp.setInviter(p.getName());
	        	    temp.setHasInvite(true);
	        	    updatePlayer(temp, Bukkit.getPlayer(args[1]).getName());
	        	    
	        	    String alliance = plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2());
	        	  
	        	    s(Bukkit.getPlayer(args[1]), "You have been invited to join " + alliance + "&d! They current have " + wca.getMemberCount() + " &dmembers.");
	        	    s(Bukkit.getPlayer(args[1]), "Type /waa accept OR /waa deny");
	        	    s(p, "Request sent.");
	        	    
	         break;
	         
	         case "accept":
	        	  
	        	    if (!wcp.hasInvite()){
	        	    	s(p, "You don't have any invites.");
	        	    	break;
	        	    }
	        	    
	        	    wcp.setInAlliance(true);
	        	    wcp.setAlliance(wcp.getInvite());
	        	    wcp.setAllianceRank("Member");
	        	    wcp.setHasInvite(false);
	        	    updatePlayer(wcp, p.getName());
	        	    
	        	    wcaCurrent = plugin.wcm.getWCAlliance(wcp.getInvite());
	        	    wcaCurrent.addMember(p.getName());
	        	    updateAlliance(wcaCurrent, wcp.getAlliance());
	        	    alliance = plugin.wcm.getCompleted(wcp.getInvite(), wcaCurrent.getColor1(), wcaCurrent.getColor2());
	        	    
	        	    p.performCommand("nick " + wcp.getNick());
        	    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + p.getName() + " group add " + wcp.getInvite());
	        	    Utils.bc(p.getDisplayName() + " has joined " + alliance + "&d!");
	        	    
	        		scoreboardEvent = new ScoreboardUpdateEvent(p);
	        		plugin.getServer().getPluginManager().callEvent(scoreboardEvent);
	        	    
	         break;
	          
	         case "deny":
	        	 
	        	    if (!wcp.hasInvite()){
	        	    	s(p, "You don't have any invites.");
	        	    	break;
	        	    }
	        	    
	        	    wcp.setHasInvite(false);
	        	    updatePlayer(wcp, p.getName());
	        	    s(p, "Invite cleared.");
	         break;
	         
	         case "approve":
	        	 
	        	    if (!p.hasPermission("wa.staff")){
	        	    	s(p, "Only staff can approve!");
	        	    	break;
	        	    }
	        	    
	        	    if (args.length != 2){
	        	    	s(p, "Try /waa approve <alliance>.");
	        	    	break;
	        	    }
	        	    
	        	    wcaCurrent = plugin.wcm.getWCAlliance(args[1]);
	        	    
	        	    if (wcaCurrent == null){
	        	    	s(p, "That alliance does not exist!");
	        	    	break;
	        	    }
	        	    
	        	    wcaCurrent.setApproved(true);
	        	    updateAlliance(wcaCurrent, args[1]);
	        	    
	        	    s(p, "Approved. Let them know to /waa upgrade!");
	        	    
	          break;
	         
	          case "upgrade":
	        	  
	        	    if (!wcp.getAllianceRank().equals("Leader")){
	        	  	   	s(p, "Only the leader can upgrade.");
	        	  	   	break;
  			        }
	        	    
	        	    if (wca.getTier() >= 8){
	        	    	s(p, "Your alliance is already at max!");
	        	    	break;
	        	    }
	        	    
	        	    if (!wca.getApproved()){
	        	    	s(p, "Your alliance needs to be approved by staff first!");
	        	    	break;
	        	    }
	        	    
	        	    if(wca.getBank() < prices.get(wca.getTier())){
	        	    	s(p, "Your alliance lacks the funds to do this!");
	        	    	break;
	        	    }
	        	    
	        	    wca.setTier(wca.getTier() + 1);
	        	    wca.setBank(wca.getBank() - prices.get(wca.getTier()));
	        	    
	        	    if (!p.isOp()){
	        	    	wcp.setWCOP(true);
	        	    	p.setOp(true);
	        	    }
	        	    
	        	    updateAll(wca, wcp, wcp.getAlliance(), p.getName());
	        	    p.performCommand("rg select " + wcp.getAlliance());
	        	    p.performCommand("/outset 20");
	        	    p.performCommand("rg redefine " + wcp.getAlliance());
	        	    
	        	    if (wcp.isWCOp()){
	        	    	wcp.setWCOP(false);
	        	    	p.setOp(false);
	        	    }
	        	    
	        	    updatePlayer(wcp, p.getName());
	        	    
	        	    alliance = plugin.wcm.getCompleted(wcp.getAlliance(), wca.getColor1(), wca.getColor2());
	        	    Utils.bc(alliance + " &dhas been upgraded to tier " + (wca.getTier()) + "&d!");
	        	    
	          break;
	          
	          case "disband":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader")){
	        	  	   	s(p, "Only the leader can disband.");
	        	  	   	break;
			        }
	        	  	
	        	  	if (!p.isOp()){
	        	    	wcp.setWCOP(true);
	        	    	p.setOp(true);
	        	    }
	        	    
	        	  	updatePlayer(wcp, p.getName());
	        	  	
	        	    p.performCommand("rg remove " + wcp.getAlliance());
	        	    p.performCommand("pex group " + wcp.getAlliance() + " delete");
	        	    
	        	    if (wcp.isWCOp()){
	        	    	wcp.setWCOP(false);
	        	    	updatePlayer(wcp, p.getName());
	        	    	p.setOp(false);
	        	    }
    
	        	    try {
	        	    	plugin.wcm.remAlliance(wcp.getAlliance());
	        	    } catch (IOException e) {
	        	    	e.printStackTrace();
	        	    }
	        	    
	        	    String wcpp = wcp.getAlliance();
	        	    
	        	    for (Player a : Bukkit.getOnlinePlayers()){
	        	    	wcpCurrent = plugin.wcm.getWCPlayer(a.getName());
	        	    		if (wcpCurrent.getAlliance().equals(wcpp)){
	        	    			wcpCurrent.setAlliance("ForeverAlone");
	        	    			wcpCurrent.setInAlliance(false);
	        	    			wcpCurrent.setAllianceRank("Guest");
	        	    			wcpCurrent.setInChat(false);
	        	    			updatePlayer(wcpCurrent, a.getName());
	        	    			a.performCommand("nick " + wcpCurrent.getNick());
	        	    			a.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	        	        	  	Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(a));
	        	    		}
	        	    }
	        	    
        	    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex group " + wcpp + " delete");
	        	  	Utils.bc(AS(p.getDisplayName() + " &dhas disbanded their alliance! D:"));
	        	  	  	
	          break;
	          
	          case "kick":
	        	  
	        	    if (args.length != 4){
	        	    	s(p, "Try /waa kick <player> <oldrank> <newrank>");
	        	    	break;
	        	    }
	        	    
	        	    if (!p.hasPermission("wa.mod2")){
	        	    	s(p, "You must be staff.");
	        	    	break;
	        	    }
	        	    
	        	    if (Bukkit.getPlayer(args[1]) == null){
	        	    	s(p, "That player is offline!");
	        	    	break;
	        	    }
	        	    
	        	    p.performCommand("pex user " + args[1] + " group remove " + args[2]);
	        	    p.performCommand("pex user " + args[1] + " group add " + args[3]);
	        	    
	        	    wcpCurrent = plugin.wcm.getWCPlayer(Bukkit.getPlayer(args[1]).getName());
	        	    
	        	    if (!wcpCurrent.getInAlliance()){
	        	    	s(p, "They're not in an alliance!");
	        	    	break;
	        	    }
	        	    
	        	    String rawr = wcpCurrent.getAlliance();
	        	    
	        	    wcaCurrent = plugin.wcm.getWCAlliance(wcp.getAlliance());
	        	    wcaCurrent.remMember(args[1]);
	        	    wcpCurrent.setAlliance("ForeverAlone");
	        	    wcpCurrent.setInAlliance(false);
	        	    wcpCurrent.setAllianceRank("Guest");
	        	    wcpCurrent.setInChat(false);
	        	    updateAll(wcaCurrent, wcpCurrent, rawr, Bukkit.getPlayer(args[1]).getName());
	        	    Utils.bc(Bukkit.getPlayer(args[1]).getDisplayName() + " &dwas kicked from their alliance by " + p.getName() + "&d!");
        	    	Bukkit.getPlayer(args[1]).performCommand("nick " + wcpCurrent.getNick());
        	    	Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + Bukkit.getPlayer(args[1]).getName() + " group remove " + rawr);
        	    	Bukkit.getPlayer(args[1]).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        			Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(Bukkit.getPlayer(args[1])));
        	    	
	          break;
	          
	          case "levels":
	        	  
	        	    s(p, "Check the website! (I'm too lazy to code this. After all, I did build the entire server's framework.");
	        	  
	          break;
	          
	          case "setrank":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader")){
	        	  		s(p, "Only the leader can disband.");
	        		  	break;
			        }
	        	  	
	        	  	if (args.length != 3 || args[2].equalsIgnoreCase("leader")){
	        	  		s(p, "/waa setrank <player> <rank>");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (Bukkit.getPlayer(args[1]) == null){
	        	  		s(p, "That player is not online.");
	        	  		break;
	        	  	}
	        	  	
	        	  	wcpCurrent = plugin.wcm.getWCPlayer(Bukkit.getPlayer(args[1]).getName());
	        	  	
	        	  	wcpCurrent.setAllianceRank(args[2]);
	        	  	
	        	  	if (wca.getChatUsers().contains(args[1])){
	        	  		wcpCurrent.setAllianceRank2(args[2]);
	        	  	}
	        	  	
	        	  	updatePlayer(wcpCurrent, args[1]);
	        	  
	          break;
	                  
	          case "lookup":
	        	  
	        	  	if (args.length != 2){
	        	  		s(p, "/waa lookup <alliance>");
	        	  		break;
	        	  	}
	        	  	
	        	  	if (plugin.wcm.getWCAlliance(args[1]) == null){
	        	  		s(p, "That alliance does not exist.");
	        	  		break;
	        	  	}
	        	  	
	        	  	wcaCurrent = plugin.wcm.getWCAlliance(args[1]);
	        	  	
		            p.sendMessage(new String[] {
		        			  
			        AS("&a| &3Alliance &f// &3" + plugin.wcm.getCompleted(args[1], wcaCurrent.getColor1(), wcaCurrent.getColor2())),
			        AS("&a| &3Leader &f// &3" + wcaCurrent.getLeader()),
			        AS("&a| &3Funds &f// &3" + wcaCurrent.getBank()),
			        AS("&a| &3Tier &f// &3" + wcaCurrent.getTier()),
			        AS("&a| &3Door Locks &f// &3" + wcaCurrent.getDoorLocks()),
			        AS("&a| &3Mob Spawn &f// &3" + wcaCurrent.getMobSpawn()),
			        AS("&a| &cTown Coords &f// &3" + wcaCurrent.getCoords()),
			        AS("&a| &3Protection Radius &f// &3" + wcaCurrent.getRadius())});
	            	sb = new StringBuilder();
	            	int x = 0;
		            
		            for (String s : wcaCurrent.getUsers()){
		            	x++;
		            	if (x < wcaCurrent.getUsers().size()){
		            		sb.append(plugin.wcm.getCompleted(s, wcaCurrent.getColor1(), wcaCurrent.getColor2()) + ", ");
		            	} else {
		            		sb.append(plugin.wcm.getCompleted(s, wcaCurrent.getColor1(), wcaCurrent.getColor2()));
		            	}
		            }
		            
		            String msg = sb.toString().trim();
		            p.sendMessage(AS(msg));
		            
	          break;
   
	          case "leader":
	        	  
	        	  	if (!wcp.getAllianceRank().equals("Leader") || args[1].equals(p.getName())){
	        	  		s(p, "Only the leader can change leaders. And it can't be yourself.");
	        		  	break;
			        }
	        	  	
	        	  	if (args.length != 2 || Bukkit.getPlayer(args[1]) == null){
	        	  		s(p, "/waa leader <name>. They must be online.");
	        	  		break;
	        	  	}
	        	  	
	        	  	wcpCurrent = plugin.wcm.getWCPlayer(Bukkit.getPlayer(args[1]).getName());
	        	  	
	        	  	if (!wcpCurrent.getAlliance().equals(wcp.getAlliance())){
	        	  		s(p, "They must be in your alliance.");
	        	  		break;
	        	  	}
	        	  	
	        	  	wcpCurrent.setAllianceRank("Leader");
	        	  	wcp.setAllianceRank("Member");
	        	  	
	        	  	if (wca.getChatUsers().contains(args[1])){
	        	  		wcpCurrent.setAllianceRank2(args[1]);
	        	  	}
	        	  	
	        	  	if (wca.getChatUsers().contains(p.getName())){
	        	  		wcp.setAllianceRank2("Member");
	        	  	}
	        	  	
	        	  	wca.setLeader(Bukkit.getPlayer(args[1]).getName());

	        	  	updateAll(wca, wcpCurrent, wcp.getAlliance(), Bukkit.getPlayer(args[1]).getName());
	        	  	updatePlayer(wcp, p.getName());
	        	  	bc("&d" + wcp.getAlliance() + " &dhas changed leaders!");
	        	  	
	          break;
      		}
    	} else {
    		s(p, "Try /waa help!");
    	}
  	}
  }

    @WCCommand(aliases = {"rn"}, desc = "Learn the real name of a player", help = "/rn <nickname>", min = 1)
    public void onRealName(Player p, String[] args){
    	
    	if (args.length != 1){
    		s(p, "/rn <name>");
    	} else {
    		for (Player pp : Bukkit.getOnlinePlayers()){
    			if (pp.getDisplayName().replaceAll("&", "").toLowerCase().contains(args[0].toLowerCase())){
    				WCMain.s2(p, pp.getDisplayName() + " &f>> &7" + pp.getName());
    			}
    		}
    	}
    }
    
    @WCCommand(aliases = {"list"}, desc = "List all players on the server", help = "/list")
    public void onList(Player p, String[] args){

    	StringBuilder sb = new StringBuilder();
    	
    	for (Player pp : Bukkit.getOnlinePlayers()){
    		sb.append("&7" + pp.getName() + " ");
    	}
    	String s = sb.toString().trim();
    	s(p, s.replace(" ", "&8, "));
    }
  
    @WCCommand(aliases = {"nick"}, desc = "Give yourself a new nickname", help = "/nick <nick>", min = 1 , max = 1)
    public void onNick(Player p, String[] args){

    	wcp = plugin.wcm.getWCPlayer(p.getName());
    	wca = plugin.wcm.getWCAlliance(wcp.getAlliance());
    	
    	if (args.length != 1){
    		s(p, "/nick <name>");
    		return;
    	}
    	
    	if (!args[0].substring(0, 3).equalsIgnoreCase(p.getName().substring(0, 3))){
    		s(p, "Your nick must start with the first three letters of your name.");
    		return;
    	}
    	
    	if (args[0].length() > 16){
    		s(p, "You can't have a nick over 16 letters long.");
    		return;
    	}
    	
    	if (args[0].contains("&") || args[0].contains("§")){
    		s(p, "Your color will be updated automatically based on which alliance you are in. Don't use &.");
    		return;
    	}
    	
    	wcp.setNick(args[0]);
    	wcp.setHasNick(true);
    	
		if (plugin.wcm.getCompleted2(wcp.getNick(), wca.getColor1(), wca.getColor2()).length() > 16){
			p.setPlayerListName(Utils.AS(plugin.wcm.getCompleted2(wcp.getNick(), wca.getColor1(), wca.getColor2()).substring(0, 16)));
		} else {
			p.setPlayerListName(Utils.AS(plugin.wcm.getCompleted2(wcp.getNick(), wca.getColor1(), wca.getColor2())));
		}
		
    	updatePlayer(wcp, p.getName());
    	p.setDisplayName(plugin.wcm.getCompleted(wcp.getNick(), wca.getColor1(), wca.getColor2()));
    	s(p, "Updated!");
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