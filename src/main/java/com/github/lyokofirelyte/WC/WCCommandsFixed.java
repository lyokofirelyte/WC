package com.github.lyokofirelyte.WC;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.Util.LagUtils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class WCCommandsFixed {
<<<<<<< HEAD
        
        private WCMain main;
        
        public WCCommandsFixed(WCMain main){
                
                this.main = main;
                
        }
        
        @WCCommand(aliases = { "google" }, desc = "Google anything! Anything!", help = "/google <search>", min = 1)
        public void onTheGoogle(Player p, String[] args){
                
                blankB(new String[] {
                                
                                AS(WC + "Google: &6http://lmgtfy.com/?q=") + createString(args, 0).replace(" ", "+"),
                                AS("&5~&7" + p.getDisplayName())
                                
                });
                
        }
        
        @WCCommand(aliases = { "google" }, desc = "Rage to become a member. They can't miss it!", help = "/member", perm = "wa.staff")
        public void onTheMember(Player p, String[] args){
                
                if (args.length > 0){
                        
                        b("&b&lHEY THERE, &4&l" + createString(args, 0) + "&b&l!");
                        
                }
                
                b(new String[] {
                                
                                "&b&lWANT TO &a&lJOIN US AND BUILD&a&l?!",
                                "&e&lCLICK BELOW AND SCROLL DOWN TO &c&lMEMBER APPLICATION&e&l!",
                                "&f&o---> &f&lhttp://tinyurl.com/waregister &f&o<---"
                                
                });
                
        }
        
        @WCCommand(aliases = { "ping" }, desc = "Ping the server. Just to check. The results may shock you.", help = "/ping")
        public void onThePing(Player p, String[] args){
                
                if (args.length == 0){
                        
                        s(p, "PONG!");
                        
                } else {
                        
                        s2(p, createString(args, 0));
                        
                }
                
        }
        
        @WCCommand(aliases = { "wc", "watercloset", "worldscollide" }, desc = "This method is way too long.", help = "/wc ?", min = 1)
        public void onTheHugeWayTooLongMainWCCommand(Player p, String[] args){
                
                WCPlayer wcp = main.wcm.getWCPlayer(p.getName());
                
                switch (args[0].toLowerCase()){
                
                case "troll":
                        
                        if (checkPerm(p, "wa.staff")){
                                
                                long delay = 0L;
                                
                                for (int i = 0; i < 7; i++){
                                        
                                        delay(new Runnable(){
                                                
                                                public void run(){
                                                        
                                                        for (Player p : Bukkit.getOnlinePlayers()){
                                                                
                                                                p.openInventory(p.getInventory());
                                                                
                                                        }
                                                        
                                                }
                                                
                                        }, delay);
                                        
                                        delay = delay + 10L;
                                        
                                        delay(new Runnable(){
                                                
                                                public void run(){
                                                        
                                                        for (Player p : Bukkit.getOnlinePlayers()){
                                                                
                                                                p.closeInventory();
                                                                
                                                        }
                                                        
                                                }
                                                
                                        }, delay);
                                        
                                        delay = delay + 10L;
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "exp": case "xp":
                        
                        if (args.length == 1){
                                
                                s(p, "You currently have &6" + p.getTotalExperience() + " &dexp on your bar.");
                                
                        } else {
                                
                                switch (args[1].toLowerCase()){
                                
                                case "staff":
                                        
                                        
                                        break;
                                        
                                case "bank":
                                        
                                        if (args.length == 2){
                                                
                                                s(p, new String[] {
                                                                
                                                                WC + "You currently have &6" + wcp.getExp() + " &dexp stored in your bank.",
                                                                WC + "That's &6" + (wcp.getExp() / 825) + " &dlevel 30's.",
                                                                WC + "For help, type /wc exp bank ?."
                                                                
                                                });
                                                
                                        } else {
                                                
                                                switch (args[2].toLowerCase()){
                                                
                                                case "staff":
                                                        
                                                        if (checkPerm(p, "wa.mod2")){
                                                                
                                                                String[] help = {
                                                                                
                                                                                WC + "Exp Help Menu",
                                                                                "&e- &6/wc exp bank staff set <player> <amount> [L] &e// &6Set a players exp amount or level.",
                                                                                "&e- &6/wc exp bank staff add <player> <amount> &e// &6Add an exp amount to a player.",
                                                                                "&e- &6/wc exp bank staff rem <player> <amount> &e// &6Remove an exp amount from a player."
                                                                                
                                                                };
                                                                
                                                                if (args.length < 6){
                                                                        
                                                                        s(p, help);
                                                                        
                                                                } else {
                                                                        
                                                                        WCPlayer player = main.wcm.getWCPlayer(args[4]);
                                                                        
                                                                        if (player == null){
                                                                                
                                                                                s(p, "&7" + args[4] + " &dis not a valid player!");
                                                                                
                                                                        } else if (!(isInteger(args[5]))){
                                                                                
                                                                                s(p, "&6" + args[5] + " &dis not a valid number!");
                                                                                
                                                                        } else {
                                                                                
                                                                                int exp = Integer.parseInt(args[5]);
                                                                                
                                                                                switch (args[3].toLowerCase()){
                                                                                
                                                                                case "set":
                                                                                        
                                                                                        player.setExp(exp);
                                                                                        s(p, "&7" + args[4] + "&d's exp bank has been set to &6" + exp + "&d!");
                                                                                        break;
                                                                                        
                                                                                case "add":
                                                                                        
                                                                                        player.setExp(player.getExp() + exp);
                                                                                        s(p, "Added &6" + exp + " &dexp. &7" + args[4] + " &dnow has &6" + player.getExp() + " &dexp in their bank!");
                                                                                        break;
                                                                                        
                                                                                case "rem": case "remove": case "del": case "delete":
                                                                                        
                                                                                        if (player.getExp() > exp){
                                                                                                
                                                                                                s(p, "&7" + args[4] + " &d does not have &6" + exp + " exp in their bank!");
                                                                                                
                                                                                        } else {
                                                                                                
                                                                                                player.setExp(player.getExp() - exp);
                                                                                                s(p, "Removed &6" + exp + " &dexp. &7" + args[4] + " &dnow has &6" + player.getExp() + " &dexp in their bank!");
                                                                                                
                                                                                        }
                                                                                        
                                                                                        break;
                                                                                        
                                                                                }
                                                                                
                                                                        }
                                                                
                                                                }
                                                                
                                                        }
                                                        
                                                        break;
                                                        
                                                }
                                                
                                        }
                                        
                                        break;
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "tp":
                        
                        if (args.length < 2){
                                
                                s(p, "/wc tp <player>");
                                
                        } else {
                                
                                int tokens = wcp.getParagonTps();
                                
                                if (tokens < 1){
                                        
                                        s(p, "You don't have any TP tokens!");
                                        
                                } else if (Bukkit.getPlayer(args[1]) == null){
                                        
                                        s(p, "That player is not online!");
                                        
                                } else {
                                        
                                        wcp.setParagonTps(tokens - 1);
                                        tempOp(p, "tp " + args[1]);
                                        s(p, "Teleporting!");
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "markkit":
                        
                        if (wcp.getParagonMarket()){
                                
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp markkit " + p.getName());
                                
                        } else {
                                
                                s(p, "You don't have a markkit warp!");
                        }
                        
                        break;
                        
                case "back":
                        
                        int tokens = wcp.getParagonBacks();
                        
                        if (tokens < 1){
                                
                                s(p, "You don't have any back tokens!");
                                
                        } else {
                                
                                wcp.setParagonBacks(tokens - 1);
                                tempOp(p, "back");
                                
                        }
                        
                        break;
                        
                case "home":
                        
                        if (wcp.getParagonSpecialHomeSet()){
                                
                                p.teleport(wcp.getParagonSpecialHome());
                                s(p, "Teleporting!");
                                
                        } else {
                                
                                s(p, "You don't have a special home set!");
                                
                        }
                        
                        break;
                        
                case "stafftp":
                        
                        if (checkPerm(p, "wa.staff")){
                                
                                if (args.length < 2){
                                        
                                        s(p, "/wc stafftp <player>");
                                        
                                } else {
                                        
                                        Player pp = Bukkit.getPlayer(args[1]);
                                        
                                        if (pp == null){
                                                
                                                s(p, "That player is not online!");
                                                
                                        } else if (pp.getName().equals(p.getName())){
                                                
                                                s(p, "Why would you even want to check yourself?");
                                                
                                        } else {
                                                
                                                tempOp(p, "tp " + args[1]);
                                                b(WC + "&7" + p.getDisplayName() + " &dhas used a staff teleport for &7" + pp.getDisplayName() + "&d!");
                                                
                                        }
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "creativerank":
                        
                        if (checkPerm(p, "wa.mod2")){
                                
                                if (args.length < 3){
                                        
                                        s(p, "/wc creativerank <player> <rank>");
                                        
                                } else {
                                        
                                        WCPlayer player = main.wcm.getWCPlayer(args[1]);
                                        
                                        if (player == null){
                                                
                                                s(p, "That player does not exist!");
                                                
                                        } else {
                                                
                                                player.setCreativeRank(createString(args, 2));
                                                s(p, "&7" + args[1] + " &dhas given the creative rank &6" + args[2] + "&d!");
                                                
                                        }
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "creative":
                        
                        tempOp(p, "warp wacp");
                        break;
                        
                case "edit":
                        
                        if (checkPerm(p, "wa.staff")){
                                
                                wcp.setMarkkitEditMode(!(wcp.getMarkkitEditMode()));
                                wcp.setCurrentMarkkitEdit("none");
                                
                                if (wcp.getMarkkitEditMode()){
                                        
                                        s(p, "Toggled edit mode &6OFF&d!");
                                        
                                } else {
                                        
                                        s(p, "Toggled edit mode &6ON&d!");
                                        
                                }
                                
                        }
                        
                        break;
                        
                case "wipemails":
                        
                        if (p.getName().equals("Hugh_Jasses")){
                                
                                for (String user : main.wcm.getWCSystem("system").getUsers()){
                                        
                                        WCPlayer player = main.wcm.getWCPlayer(user);
                                        player.setMail(new ArrayList<String>());
                                        
                                }
                                
                        } else {
                                
                                s(p, "Are you Hugh_Jasses? Didn't think so.");
                                
                        }
                        
                        break;
                        
                case "allowdeathmessage":
                        
                        wcp.setAllowDeathLocation(!(wcp.getAllowDeathLocation()));
                        
                        if (wcp.getAllowDeathLocation()){
                                
                                s(p, "Toggled death location to &6ON&d!");
                                
                        } else {
                                
                                s(p, "Toggled death location to &6OFF&d!");
                                
                        }
                        
                        break;
                        
                case "hugdebug":
                        
                        s(p, "This used to be a command. Now it isn't. Sucks, doesn't it.");
                        break;
                        
                case "rootshortcut":
                        
                        wcp.setRootShortCut(!(wcp.getRootShortCut()));
                        
                        if (wcp.getRootShortCut()){
                                
                                s(p, "Toggled the shift-left click shortcut to &6ON&d!");
                                
                        } else {
                                
                                s(p, "Toggled the shift-left click shortcut to &6OFF&d!");
                                
                        }
                        
                        break;
                        
                case "sideboardcoords":
                        
                        wcp.setNamePlate(!(wcp.getNamePlate()));
                        
                        if (wcp.getNamePlate()){
                                
                                s(p, "Toggled the coord display to &6ON&d!");
                                
                        } else {
                                
                                s(p, "Toggled the coord display to &6OFF&d!");
                                
                        }
                        
                        break;
                        
                case "nameplate":
                        
                        wcp.setRootShortCut(!(wcp.getRootShortCut()));
                        
                        if (wcp.getRootShortCut()){
                                
                                s(p, "Toggled the shift-left click shortcut to &6ON&d!");
                                
                        } else {
                                
                                s(p, "Toggled the shift-left click shortcut to &6OFF&d!");
                                
                        }
                        
                        break;
                        
                case "pvp":
                        
                        wcp.setPVP(!(wcp.getPVP()));
                        
                        if (wcp.getPVP()){
                                
                                b("&7" + p.getDisplayName() + " &6has enabled PVP mode!");
                                
                        } else {
                                
                                b("&7" + p.getDisplayName() + " &6has disabled PVP mode!");
                                
                        }
                        
                        break;
                        
                }
                
        }
        
        private boolean checkPerm(Player p, String perm){
                
                if (p.hasPermission(perm)){
                        
                        s(p, "You do not have permission for that!");
                        
                        return true;
                        
                } else {
                        
                        return false;
                        
                }
                
        }
        
        private void tempOp(Player p, String cmd){
                
                if (p.isOp()){
                        
                        p.performCommand(cmd);
                        
                } else {
                        
                        p.setOp(true);
                        p.performCommand(cmd);
                        p.setOp(false);
                        
                }
                
        }
        
}
=======
	
	private WCMain main;
	
	public WCCommandsFixed(WCMain main){
		
		this.main = main;
		
	}
	
	@WCCommand(aliases = { "google" }, desc = "Google anything! Anything!", help = "/google <search>", min = 1)
	public void onTheGoogle(Player p, String[] args){
		
		blankB(new String[] {
				
				AS(WC + "Google: &6http://lmgtfy.com/?q=") + createString(args, 0).replace(" ", "+"),
				AS("&5~&7" + p.getDisplayName())
				
		});
		
	}
	
	@WCCommand(aliases = { "google" }, desc = "Rage to become a member. They can't miss it!", help = "/member", perm = "wa.staff")
	public void onTheMember(Player p, String[] args){
		
		if (args.length > 0){
			
			b("&b&lHEY THERE, &4&l" + createString(args, 0) + "&b&l!");
			
		}
		
		b(new String[] {
				
				"&b&lWANT TO &a&lJOIN US AND BUILD&a&l?!",
				"&e&lCLICK BELOW AND SCROLL DOWN TO &c&lMEMBER APPLICATION&e&l!",
				"&f&o---> &f&lhttp://tinyurl.com/waregister &f&o<---"
				
		});
		
	}
	
	@WCCommand(aliases = { "ping" }, desc = "Ping the server. Just to check. The results may shock you.", help = "/ping")
	public void onThePing(Player p, String[] args){
		
		if (args.length == 0){
			
			s(p, "PONG!");
			
		} else {
			
			s2(p, createString(args, 0));
			
		}
		
	}
	
	@WCCommand(aliases = { "serverinfo", "si" }, desc = "Server info in a nutshell!", help = "/si")
	public void onTheServerInfo(Player p, String[] args){
		
		s(p, new String[] {
				
				WC + "Server Info & Stats",
				"&5- &dBukkit Version&5: &6" + Bukkit.getServer().getBukkitVersion(),
				"&5- &dTicks per Second (tps)&5: &6" + LagUtils.getRoundedTps(),
				"&5- &dLag Percentage&5: &6" + LagUtils.getLagPercent() + "%",
				"&5.....m..u..c..h.....s..t..a..t..s.....",
				WC + "World Name &5// &dLoaded Entities &5// &dLoaded Chunks"
				
		});
		
		for (World world : Bukkit.getWorlds()){
			
			s2(p, "&5- &6" + world.getName() + " &5// &6" + world.getEntities().size() + " &5// &6" + world.getLoadedChunks().length);
			
		}
		
		s2(p, "&5.......m..a..n..y.....i..n..f..o.......");
		
	}
	
	@WCCommand(aliases = { "wc", "watercloset", "worldscollide" }, desc = "This method is way too long.", help = "/wc ?", min = 1)
	public void onTheHugeWayTooLongMainWCCommand(Player p, String[] args){
		
		WCPlayer wcp = main.wcm.getWCPlayer(p.getName());
		
		switch (args[0].toLowerCase()){
		
		case "troll":
			
			if (checkPerm(p, "wa.staff")){
				
				long delay = 0L;
				
				for (int i = 0; i < 7; i++){
					
					delay(new Runnable(){
						
						public void run(){
							
							for (Player p : Bukkit.getOnlinePlayers()){
								
								p.openInventory(p.getInventory());
								
							}
							
						}
						
					}, delay);
					
					delay = delay + 10L;
					
					delay(new Runnable(){
						
						public void run(){
							
							for (Player p : Bukkit.getOnlinePlayers()){
								
								p.closeInventory();
								
							}
							
						}
						
					}, delay);
					
					delay = delay + 10L;
					
				}
				
			}
			
			break;
			
		case "exp": case "xp":
			
			if (args.length == 1){
				
				s(p, "You currently have &6" + p.getTotalExperience() + " &dexp on your bar.");
				
			} else {
				
				switch (args[1].toLowerCase()){
				
				case "staff":
					
					
					break;
					
				case "bank":
					
					if (args.length == 2){
						
						s(p, new String[] {
								
								WC + "You currently have &6" + wcp.getExp() + " &dexp stored in your bank.",
								WC + "That's &6" + (wcp.getExp() / 825) + " &dlevel 30's.",
								WC + "For help, type /wc exp bank ?."
								
						});
						
					} else {
						
						switch (args[2].toLowerCase()){
						
						case "staff":
							
							if (checkPerm(p, "wa.mod2")){
								
								String[] help = {
										
										WC + "Exp Help Menu",
										"&e- &6/wc exp bank staff set <player> <amount> [L] &e// &6Set a players exp amount or level.",
										"&e- &6/wc exp bank staff add <player> <amount> &e// &6Add an exp amount to a player.",
										"&e- &6/wc exp bank staff rem <player> <amount> &e// &6Remove an exp amount from a player."
										
								};
								
								if (args.length < 6){
									
									s(p, help);
									
								} else {
									
									WCPlayer player = main.wcm.getWCPlayer(args[4]);
									
									if (player == null){
										
										s(p, "&7" + args[4] + " &dis not a valid player!");
										
									} else if (!(isInteger(args[5]))){
										
										s(p, "&6" + args[5] + " &dis not a valid number!");
										
									} else {
										
										int exp = Integer.parseInt(args[5]);
										
										switch (args[3].toLowerCase()){
										
										case "set":
											
											player.setExp(exp);
											s(p, "&7" + args[4] + "&d's exp bank has been set to &6" + exp + "&d!");
											break;
											
										case "add":
											
											player.setExp(player.getExp() + exp);
											s(p, "Added &6" + exp + " &dexp. &7" + args[4] + " &dnow has &6" + player.getExp() + " &dexp in their bank!");
											break;
											
										case "rem": case "remove": case "del": case "delete":
											
											if (player.getExp() > exp){
												
												s(p, "&7" + args[4] + " &d does not have &6" + exp + " exp in their bank!");
												
											} else {
												
												player.setExp(player.getExp() - exp);
												s(p, "Removed &6" + exp + " &dexp. &7" + args[4] + " &dnow has &6" + player.getExp() + " &dexp in their bank!");
												
											}
											
											break;
											
										}
										
									}
								
								}
								
							}
							
							break;
							
						}
						
					}
					
					break;
					
				}
				
			}
			
			break;
			
		case "tp":
			
			if (args.length < 2){
				
				s(p, "/wc tp <player>");
				
			} else {
				
				int tokens = wcp.getParagonTps();
				
				if (tokens < 1){
					
					s(p, "You don't have any TP tokens!");
					
				} else if (Bukkit.getPlayer(args[1]) == null){
					
					s(p, "That player is not online!");
					
				} else {
					
					wcp.setParagonTps(tokens - 1);
					tempOp(p, "tp " + args[1]);
					s(p, "Teleporting!");
					
				}
				
			}
			
			break;
			
		case "markkit":
			
			if (wcp.getParagonMarket()){
				
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp markkit " + p.getName());
				
			} else {
				
				s(p, "You don't have a markkit warp!");
			}
			
			break;
			
		case "back":
			
			int tokens = wcp.getParagonBacks();
			
			if (tokens < 1){
				
				s(p, "You don't have any back tokens!");
				
			} else {
				
				wcp.setParagonBacks(tokens - 1);
				tempOp(p, "back");
				
			}
			
			break;
			
		case "home":
			
			if (wcp.getParagonSpecialHomeSet()){
				
				p.teleport(wcp.getParagonSpecialHome());
				s(p, "Teleporting!");
				
			} else {
				
				s(p, "You don't have a special home set!");
				
			}
			
			break;
			
		case "stafftp":
			
			if (checkPerm(p, "wa.staff")){
				
				if (args.length < 2){
					
					s(p, "/wc stafftp <player>");
					
				} else {
					
					Player pp = Bukkit.getPlayer(args[1]);
					
					if (pp == null){
						
						s(p, "That player is not online!");
						
					} else if (pp.getName().equals(p.getName())){
						
						s(p, "Why would you even want to check yourself?");
						
					} else {
						
						tempOp(p, "tp " + args[1]);
						b(WC + "&7" + p.getDisplayName() + " &dhas used a staff teleport for &7" + pp.getDisplayName() + "&d!");
						
					}
					
				}
				
			}
			
			break;
			
		case "creativerank":
			
			if (checkPerm(p, "wa.mod2")){
				
				if (args.length < 3){
					
					s(p, "/wc creativerank <player> <rank>");
					
				} else {
					
					WCPlayer player = main.wcm.getWCPlayer(args[1]);
					
					if (player == null){
						
						s(p, "That player does not exist!");
						
					} else {
						
						player.setCreativeRank(createString(args, 2));
						s(p, "&7" + args[1] + " &dhas given the creative rank &6" + args[2] + "&d!");
						
					}
					
				}
				
			}
			
			break;
			
		case "creative":
			
			tempOp(p, "warp wacp");
			break;
			
		case "edit":
			
			if (checkPerm(p, "wa.staff")){
				
				wcp.setMarkkitEditMode(!(wcp.getMarkkitEditMode()));
				wcp.setCurrentMarkkitEdit("none");
				
				if (wcp.getMarkkitEditMode()){
					
					s(p, "Toggled edit mode &6OFF&d!");
					
				} else {
					
					s(p, "Toggled edit mode &6ON&d!");
					
				}
				
			}
			
			break;
			
		case "wipemails":
			
			if (p.getName().equals("Hugh_Jasses")){
				
				for (String user : main.wcm.getWCSystem("system").getUsers()){
					
					WCPlayer player = main.wcm.getWCPlayer(user);
					player.setMail(new ArrayList<String>());
					
				}
				
			} else {
				
				s(p, "Are you Hugh_Jasses? Didn't think so.");
				
			}
			
			break;
			
		case "allowdeathmessage":
			
			wcp.setAllowDeathLocation(!(wcp.getAllowDeathLocation()));
			
			if (wcp.getAllowDeathLocation()){
				
				s(p, "Toggled death location to &6ON&d!");
				
			} else {
				
				s(p, "Toggled death location to &6OFF&d!");
				
			}
			
			break;
			
		case "hugdebug":
			
			s(p, "This used to be a command. Now it isn't. Sucks, doesn't it.");
			break;
			
		case "rootshortcut":
			
			wcp.setRootShortCut(!(wcp.getRootShortCut()));
			
			if (wcp.getRootShortCut()){
				
				s(p, "Toggled the shift-left click shortcut to &6ON&d!");
				
			} else {
				
				s(p, "Toggled the shift-left click shortcut to &6OFF&d!");
				
			}
			
			break;
			
		case "sideboardcoords":
			
			wcp.setNamePlate(!(wcp.getNamePlate()));
			
			if (wcp.getNamePlate()){
				
				s(p, "Toggled the coord display to &6ON&d!");
				
			} else {
				
				s(p, "Toggled the coord display to &6OFF&d!");
				
			}
			
			break;
			
		case "nameplate":
			
			wcp.setRootShortCut(!(wcp.getRootShortCut()));
			
			if (wcp.getRootShortCut()){
				
				s(p, "Toggled the shift-left click shortcut to &6ON&d!");
				
			} else {
				
				s(p, "Toggled the shift-left click shortcut to &6OFF&d!");
				
			}
			
			break;
			
		case "pvp":
			
			wcp.setPVP(!(wcp.getPVP()));
			
			if (wcp.getPVP()){
				
				b("&7" + p.getDisplayName() + " &6has enabled PVP mode!");
				
			} else {
				
				b("&7" + p.getDisplayName() + " &6has disabled PVP mode!");
				
			}
			
			break;
			
		}
		
	}
	
	private void circleEffects(Location center, int radius, int height, Effect effect){
		
		
		
	}
	
	private boolean checkPerm(Player p, String perm){
		
		if (p.hasPermission(perm)){
			
			s(p, "You do not have permission for that!");
			
			return true;
			
		} else {
			
			return false;
			
		}
		
	}
	
	private void tempOp(Player p, String cmd){
		
		if (p.isOp()){
			
			p.performCommand(cmd);
			
		} else {
			
			p.setOp(true);
			p.performCommand(cmd);
			p.setOp(false);
			
		}
		
	}
	
}
>>>>>>> FETCH_HEAD
