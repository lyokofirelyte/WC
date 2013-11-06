package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import static com.github.lyokofirelyte.WC.Util.Utils.*;

import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;

import static com.github.lyokofirelyte.WC.WCMain.s;

public class WCCommands implements CommandExecutor {
	
  private int groove;
  private HashMap<String, Long> rainoffCooldown = new HashMap<String, Long>();
  private HashMap<String, Long> dragonCooldown = new HashMap<String, Long>();
	
  WCMain plugin;
  WCPlayer wcp;
  String WC = "§dWC §5// §d";
  Boolean home;
  Boolean homeSet;
  Player p;
  List <Integer> laserFwTasks = new ArrayList<Integer>();
  int ltask = -1;
  public static Vector vec = new Vector();

  public WCCommands(WCMain instance){
  this.plugin = instance;
  }  	

  @SuppressWarnings("deprecation")
  public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
	 	  
  
	  if (cmd.getName().equalsIgnoreCase("google")){
		  if (args.length == 0){
			  sender.sendMessage(AS(WC + "Usage: /google <query>"));
		  } else {
			  Bukkit.broadcastMessage(AS(WC + "Google: http://lmgtfy.com/?q=") + createString(args, 0).replace(" ", "+"));
			  Bukkit.broadcastMessage(AS("&5~" + p.getDisplayName()));  
		  }
		  return true;
	  }
	  
	  if (cmd.getName().equalsIgnoreCase("member") && sender.hasPermission("wa.staff")){
		  
		  if (args.length == 1){
			  Bukkit.broadcastMessage(AS("&b&lHEY THERE, " + "&4&l" + args[0] + "&b&l!"));
			  Bukkit.broadcastMessage(AS("&b&lWANT TO &aJOIN US AND BUILD?"));
			  Bukkit.broadcastMessage(AS("&e&lCLICK BELOW AND SCROLL DOWN TO &c&lMEMBER APPLICATION"));
			  Bukkit.broadcastMessage(AS("&f&o---> &f&lhttp://bit.ly/SxATSM &f&o<---"));
		  } else {
			  Bukkit.broadcastMessage(AS("&b&lWANT TO &aJOIN US AND BUILD?"));
			  Bukkit.broadcastMessage(AS("&e&lCLICK BELOW AND SCROLL DOWN TO &c&lMEMBER APPLICATION"));
			  Bukkit.broadcastMessage(AS("&f&o---> &f&lhttp://bit.ly/SxATSM &f&o<---"));
		  }
		  return true;
	  }
	  
    if (cmd.getName().equalsIgnoreCase("wc") || cmd.getName().equalsIgnoreCase("watercloset")) {

      p = ((Player)sender);
      wcp = plugin.wcm.getWCPlayer(p.getName());

      if (args.length < 1) {
        s(p, "I'm not sure what you mean. Try /wc help or /wc ?");
        return true;
      }
      
      switch (args[0].toLowerCase()){
      
      case "pvp":
    	  
    	  if (wcp.getPVP()){
    		  wcp.setPVP(false);
    		  updatePlayer(wcp, p.getName());
    		  bc(AS(p.getDisplayName() + " &6has disabled PVP mode."));
    		  break;
    	  }
    	  
    	  wcp.setPVP(true);
    	  updatePlayer(wcp, p.getName());
    	  bc(AS(p.getDisplayName() + " &6has enabled PVP mode."));
    	  
      break;
         
      case "fling":

    	  
    	  final Player pq = p;
    	  
    	  if (pq.hasPermission("wa.admin")){
  
                for (Entity e1 : ((Player)sender).getNearbyEntities(15.0D, 15.0D, 15.0D)){
                e1.setVelocity(e1.getLocation().getDirection().multiply(-5));
                }

        		List<Location> circleblocks = circle(pq, pq.getLocation(), 1, 1, true, false, 0);
        		List<Location> circleblocks2 = circle(pq, pq.getLocation(), 2, 1, true, false, 1);
        		List<Location> circleblocks3 = circle(pq, pq.getLocation(), 3, 1, true, false, 1);
        		List<Location> circleblocks4 = circle(pq, pq.getLocation(), 4, 1, true, false, 1);
        		List<Location> circleblocks5 = circle(pq, pq.getLocation(), 5, 1, true, false, 1);
        		List<Location> circleblocks6 = circle(pq, pq.getLocation(), 6, 1, true, false, 1);
        		pq.getWorld().playSound(pq.getLocation(), Sound.BLAZE_HIT, 3.0F, 0.5F);
        		long delay = 0L;
        		
        			for (final Location l : circleblocks){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
        			
    				delay = delay + 10L;
        			
        			for (final Location l : circleblocks2){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
        			
    				delay = delay + 10L;
        			
        			for (final Location l : circleblocks3){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
        			
    				delay = delay + 10L;
        			
        			for (final Location l : circleblocks4){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
        			
    				delay = delay + 10L;
        			
        			for (final Location l : circleblocks5){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
        			
    				delay = delay + 10L;
        			
        			for (final Location l : circleblocks6){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
         			    {
         			      public void run()
         			      {
            				pq.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
            				pq.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
         			      }
         			    }
         			    , delay);
        			}
    	  }
    	  
      break;
   		
      case "homesounds":
    	 
    	  	if (!wcp.getHomeSounds()){
    	  		wcp.setHomeSounds(true);
    	  		s(p, "Home sounds turned on!");
    	  	} else {
    	  		wcp.setHomeSounds(false);
    	  		s(p, "Home sounds turned off!");
    	  	}
    	  	
    	  	updatePlayer(wcp, p.getName());
    	  	
      break;
      
      case "emotes":
    	  
    	if (!wcp.getEmotes()){
  	  		wcp.setEmotes(true);
  	  		s(p, "Auto-emotes turned on!");
  	  	} else {
  	  		wcp.setEmotes(false);
  	  		s(p, "Auto-emotes turned off!");
  	  	}
  	  	
  	  	updatePlayer(wcp, p.getName());
  	  	
  	  break;
   	  
      case "globalcolor":
    	  
    	  if (args.length != 2){
    		  s(p, "/wc globalcolor <color>.");
    		  break;
    	  }
    	  
    	  List <String> c2 = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "k");
    	  
    	  if (!c2.contains(args[1])){
    		  s(p, "That's not a color! Choose from " + c2 + ".");
    		  break;
    	  }
    	  
    	  sender.sendMessage(AS(WC + "You've changed your color to &" + args[1] + "this."));
    	  wcp.setGlobalColor(args[1]);
    	  updatePlayer(wcp, p.getName());
    	  
      break;
      
      case "poke":
    	  
    	  if (wcp.getAllowPokes()){
    		  wcp.setAllowPokes(false);
    		  s(p, "Pokes off.");
    	  } else {
    		  wcp.setAllowPokes(true);
    		  s(p, "Pokes on.");
    	  }
    	  
      break;
 	      	  
      case "hamdrax":
    	  
    	  	Boolean aval = plugin.datacore.getBoolean("Users." + p.getName() + ".Hamdrax");
    	  		if (aval){
    	  			plugin.datacore.set("Users." + p.getName() + ".Hamdrax", false);
		    	    ArrayList<String> lore;
		    	    ItemStack token = new ItemStack(Material.DIAMOND_PICKAXE, 1);
			        ItemMeta name = token.getItemMeta();
			        lore = new ArrayList<String>();
			        name.addEnchant(Enchantment.DIG_SPEED, 5, true);
			        name.addEnchant(Enchantment.DURABILITY, 3, true);
			        name.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
			        name.addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
			        name.setDisplayName("§a§o§lHAMDRAX OF " + p.getDisplayName());
			        lore.add("§7§oForm: Pick");
			        name.setLore(lore);
			        token.setItemMeta((ItemMeta)name);
			        token.setDurability((short) 780);
			        p.getInventory().addItem(token);
			        p.updateInventory();
    	  		} else {
    	  			s(p, "You need to purchase this @ the paragon shop!");
    	  		}
    	  		
	  break;

      case "hamrepair":

    	  aval = plugin.datacore.getBoolean("Users." + p.getName() + ".HamdraxRepair");
    	  
	  		if (aval){
	    	  if (p.getItemInHand().hasItemMeta()){
					if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().hasDisplayName()){
						if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains("HAMDRAX")){
							plugin.datacore.set("Users." + p.getName() + ".HamdraxRepair", false);
							p.getItemInHand().setDurability((short) 0);
							s(p, "All good! :D");
							break;
						}
					}
				}
	    	  s(p, "Please hold the hamdrax in your hand!");
	    	  break;
	  		}
			
			s(p, "You need to purchase this @ the paragon shop!");
			
	  break;
					
      case "throw":
    	  	  
    	  	if (wcp.itemThrow()){
    	  		wcp.setItemThrow(false);
    	  		updatePlayer(wcp, p.getName());
    	  		s(p, "You won't throw items by shift-right clicking anymore.");
    	  	} else {
    	  		wcp.setItemThrow(true);
    	  		updatePlayer(wcp, p.getName());
    	  		s(p, "You WILL throw items by shift-right clicking.");
    	  	}
    	  	
      break;
      
      case "fork":
    	  
    	  s(p, "LET'S DO THE FORK IN THE GARBAGE DISPOSAL!");
    	  final Location self = p.getLocation();
    	  final double x1 = self.getX();
    	  final double y1 = self.getY();
    	  final double z1 = self.getZ();
    	  int c = 100;
    	  long dl = 0L;
  	  	
  	  while (c > 0){	  
  	  
  		  c--;
  		  dl = dl+2L;
  		  
    	  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
    	    {
    	      public void run()
    	      {

    	    		  p.sendMessage("DING");
    	    		  Random rand = new Random();
    	  			  int randomNumber = rand.nextInt(7);
    	  			  int randomNumber2 = rand.nextInt(7);
    	  			  int yawRandom = rand.nextInt(360);
    	  			  int pitchRandom = rand.nextInt(90);
    	  			  int plusMinus = rand.nextInt(1);
    	  			  p.getWorld().playSound(self, Sound.NOTE_BASS_DRUM, 3.0F, 0.5F);
    	  			  
    	  			  if (plusMinus == 0){
    	  				  Location current = new Location(p.getWorld(), x1+randomNumber, y1, z1+randomNumber2, yawRandom, pitchRandom);
    	  				  p.teleport(current);
    	  			  }
    	  			  
    	  			  if (plusMinus == 1){
    	  				  Location current = new Location(p.getWorld(), x1-randomNumber, y1, z1-randomNumber2, yawRandom, (pitchRandom)-(pitchRandom*2));
    	  				  p.teleport(current);
  	  			  		}
    	  			
    	  			if (randomNumber2 == 5){
    	  				FireworkShenans fplayer = new FireworkShenans();
          	        	try {
        			
    							fplayer.playFirework(p.getWorld(), self,
    							FireworkEffect.builder().with(Type.BURST).withColor(Color.FUCHSIA).build());
    						} catch (IllegalArgumentException e) {
    							e.printStackTrace();
    						} catch (Exception e) {
    							e.printStackTrace();
    						}        	      }
    	  			}
    	      }
    	    
    	    , dl);
      
      }
  	  
  	  break;
  	    	  
      case "cookietop":
    	  
      	List <String> cookieUsers = plugin.datacore.getStringList("Users.Total");
      	List <Integer> Cookielevels = new ArrayList<Integer>();
      	int s = 0;
      		
      		for (String current : cookieUsers){
      			int paragonLevel = plugin.datacore.getInt("Users." + current + ".Cookies");
      				if (Cookielevels.contains(Integer.valueOf(paragonLevel)) == false){
      					Cookielevels.add(Integer.valueOf(paragonLevel));
      				}
      		}
      		
      		for (int top : Cookielevels){
      			if (top > s){
      				s = top;
      			}
      		}
      		
      		Cookielevels.remove(Integer.valueOf(s));
      		
      		int firstPlace = s;
      		s = 0;
      		
      		for (int top : Cookielevels){
      			if (top > s){
      				s = top;
      			}
      		}
      		
      		Cookielevels.remove(Integer.valueOf(s));
      		
      		int secondPlace = s;
      		s = 0;
      		
      		for (int top : Cookielevels){
      			if (top > s){
      				s = top;
      			}
      		}
      		
      		Cookielevels.remove(Integer.valueOf(s));
      		
      		int thirdPlace = s;

      		sender.sendMessage(new String[]{
      			WC + "Cookie Leaderboards",
      			AS("&f>>> >>> <<< <<<"),
      			AS("&7&ofirst place @ " + firstPlace)});
      		
					for (String current : cookieUsers){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".Cookies");
							if (paragonLevel == firstPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					sender.sendMessage(AS("&7&osecond place @ " + secondPlace));
      		  
					for (String current : cookieUsers){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".Cookies");
							if (paragonLevel == secondPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					sender.sendMessage(AS("&7&othird place @ " + thirdPlace));
	        		  
					for (String current : cookieUsers){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".Cookies");
							if (paragonLevel == thirdPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					break;

      case "pmcolor":
    	  
    	  if (args.length != 2){
    		  s(p, "/wc pmcolor <color>. EX: /wc pmcolor &5.");
    		  break;
    	  }
    	  
    	  List <String> colorAvail = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f");
    	  
    	  if (colorAvail.contains(args[1]) == false){
    		  s(p, "That's not a color! Choose from " + colorAvail + ".");
    		  break;
    	  }
    	  
    	  sender.sendMessage(AS(WC + "You've changed your color to &" + args[1] + "this."));
    	  wcp.setPMColor(args[1]);
    	  updatePlayer(wcp, p.getName());
    	  
      break;

      
      case "timecode":
    	  	  
    	  	if (wcp.getTimeCode()){
    	  		wcp.setTimeCode(false);
    	  		s(p, "You will no longer see time codes in chat.");
    	  	} else {
    	  		wcp.setTimeCode(true);
    	  		s(p, "You will see time codes in chat.");
    	  	}
    	  	
    	  	updatePlayer(wcp, p.getName());
    	  	
      break;
 
      case "spawnworks":

    	  	spawnWorks(p.getLocation(), p);
    	  	
      break;
    	  
      case "exp": case "xp":

    	  if (args.length == 1 || args.length == 2){
    		  
    		  if (args.length == 2 && args[1].equalsIgnoreCase("toggle")){
        		  
    			  Boolean toggle = plugin.datacore.getBoolean("Users." + sender.getName() + ".expWarn");
    		  		if (toggle){
    		  			plugin.datacore.set("Users." + sender.getName() + ".expWarn", false);
    		  			s(p, "You will recieve notifications when your XP is stored into the database.");
    		  		} else {
    		  			plugin.datacore.set("Users." + sender.getName() + ".expWarn", true);
    		  			s(p, "You will NOT recieve notifications when your XP is stored into the database.");
    		  		}
    		  	
    		  		break;
    		  }
    		  
    		  int xp = wcp.getExp();
    		  s(p, "You currently have §6" + xp + " §dexp stored. (/wc xp take <amount>)");
    		  int l30 = (xp / 825);
    		  s(p, "That's §6" + l30 + "§d level 30's.");
    		  break;
    	  }
     
    	  
    	  if (args[1].equalsIgnoreCase("take")){
    		  
    		  if (isInteger(args[2]) == false){
    			  s(p, "Do you even KNOW what a number is? You can't withdraw fish amount of xp, you silly human.");
    			  break;
    		  }
    		  
    		  int xp = wcp.getExp();
    		  
    		  if (xp < Integer.parseInt(args[2])){
    			  s(p, "I know math isn't your strong suit, but did you really think you had THAT much xp?");
    			  break;
    		  }
    		  
    		  if (args[2].startsWith("0") || args[2].startsWith("-") || args[2].startsWith("+")){
    			  s(p, "You can't withdrawl / deposit 0 or negitive amounts of xp. I'm a little concerned about you now.");
    			  break;
    		  }
    		  
    		  plugin.datacore.set("Users." + sender.getName() + ".expDeposit", true);
    		  wcp.setExp(xp-Integer.parseInt(args[2]));
    		  p.giveExp(Integer.parseInt(args[2]));
    		  s(p, "You've taken some XP out of your storage facility.");
    		  plugin.datacore.set("Users." + sender.getName() + ".expDeposit", false);
    		  break;
    	  }
    	  
    	  if (args[1].equalsIgnoreCase("store")){
    		  
    		  s(p, "Sorry, you can't put the xp back because of a Bukkit bug I can't do anything about.");
    	  }	

       break;  
       
       case "paragons": case "paragon":
    	   
    	   if (args.length == 1){
    	  
    	  sender.sendMessage(new String[]{
    		    AS("&5| &dParagon Information Complex"),
    			AS("&5| &f--- ___ --- ___ --- ___ ---"),
    			AS("&5| &bCommands&f:"),
    			AS("&5| &a/wc rewards &f// &aShows your rewards!"),
    			AS("&5| &a/wc tp <player> &f// &aUse a TP token"),
    			AS("&5| &a/wc sethome &f// &aSet a special home!"),
    			AS("&5| &a/wc home &f// &aTP to your special home!"),
    			AS("&5| &a/wc back &f// &aUse a Back token!"),
    			AS("&5| &a/wc paragon self &f// &aSee your paragon stats!"),
    			AS("&5| &bSee&a ohsototes.com/?p=paragon &bfor full info on how to get and use them!")
    	  });
    	  
    	  break;
    	  
    	   } else {
    		   
    		   int mineral = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.mineral");
    		   int dragon = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.dragon");
    		   int nature = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.nature");
    		   int crystal = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.crystal");
    		   int sun = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.sun");
    		   int hell = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.hell");
    		   int earth = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.earth");
    		   int industrial = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.industrial");
    		   int life = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.life");
    		   int inferno = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.inferno");
    		   int death = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.death");
    		   int aquatic = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.aquatic");
    		   int refined = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.refined");
    		   int frost = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.frost");
    		   int total = plugin.datacore.getInt("Users." + sender.getName() + ".Paragons.Total");
    		   
    		   sender.sendMessage(new String[]{
    				   
    		   
    				AS("&5| &dParagon Donation History"),
    				AS("&1| &aTotal &f// &a" + total),
    				AS("&1| &bMineral &f// &b" + mineral),
    				AS("&1| &bDragon &f// &b" + dragon),
    				AS("&1| &bNature &f// &b" + nature),
    				AS("&1| &bCrystal &f// &b" + crystal),
    				AS("&1| &bDeath &f// &b" + death),
    				AS("&1| &bSun &f// &b" + sun),
    				AS("&1| &bHell &f// &b" + hell),
    				AS("&1| &bEarth &f// &b" + earth),
    				AS("&1| &bIndustrial &f// &b" + industrial),
    				AS( "&1| &bLife &f// &b" + life),
    				AS("&1| &bInferno &f// &b" + inferno),
    				AS("&1| &bAquatic &f// &b" + aquatic),
    				AS("&1| &bRefined &f// &b" + refined),
    				AS("&1| &bFrost &f// &b" + frost),
    	   });
    		   
    		   break;
    	   }
    		   
       case "rewards": case "purchases":
    	   
    	   List <String> purchases = plugin.datacore.getStringList("Users." + sender.getName() + ".Purchases");
    	   s(p, "Paragon Purchases:");
    	   	for (String lampPost : purchases){
    	   		sender.sendMessage(AS("&5| &d" + lampPost));
    	   	}
    	   	
    	break;
    	
       case "tp":
    	   
    	   if (args.length != 2){
    		   s(p, "Correct usage: /wc tp <player>");
    		   break;
    	   }
    	   
    	   int teleports = plugin.datacore.getInt("Users." + sender.getName() + ".TPTokens");
    	   
    	   if (teleports < 1){
    		   s(p, "You don't have enough TP tokens!");
    		   break;
    	   }
    	   
    	   
			teleports = teleports - 1;
			plugin.datacore.set("Users." + sender.getName() + ".TPTokens", teleports);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tp " + sender.getName() + " " + args[1]);
			break;
			
      case "market":

    	    Boolean market = plugin.datacore.getBoolean("Users." + sender.getName() + ".Market");
    	    
    	    	if (market == false){
    	    		s(p, "You don't have a market warp!");
    	    		break;
    	    	}
    	    	
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp markkit " + sender.getName());
			break;
			
       case "back":
    	   
    	   int backs = plugin.datacore.getInt("Users." + sender.getName() + ".BackTokens");
    	   
    	   if (backs < 1){
    		   s(p, "You don't have enough Back tokens!");
    		   break;
    	   }

    	    backs = backs - 1;
			plugin.datacore.set("Users." + sender.getName() + ".BackTokens", backs);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + sender.getName() + " add essentials.back");
			Bukkit.getServer().dispatchCommand(sender, "eback");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "pex user " + sender.getName() + " remove essentials.back");
			break;
			
       case "sethome":
    	   
    	   home = plugin.datacore.getBoolean("Users." + sender.getName() + ".SpecialHome");
    	   homeSet = plugin.datacore.getBoolean("Users." + sender.getName() + ".SpecialHomeSet");
    	   
    	   	if (home == false){
    	   		s(p, "You don't have a special home to set!");
    	   		break;
    	   	}
    	   	
    	   	if (homeSet){
    	   		s(p, "You have already set your special home!");
    	   		break;
    	   	}
    	   	
    	   	plugin.datacore.set("Users." + sender.getName() + ".SpecialHomeSet", true);
    	   	
    	   	double x = p.getLocation().getBlockX();
    	   	double y = p.getLocation().getBlockY();
    	   	double z = p.getLocation().getBlockZ();
    	   	float yaw = p.getLocation().getYaw();
    		float pitch = p.getLocation().getPitch();
    	   	String w = p.getWorld().getName();
    	   	
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocX", x);
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocY", y);
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocZ", z);
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocW", w);
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocYAW", yaw);
    	   	plugin.datacore.set("Users." + p.getName() + ".SpecialHomeLocP", pitch);
    	   	
    	   	s(p, "HOME SET!");
    	   	break;
    	   	
       case "home":
    	   
    	   home = plugin.datacore.getBoolean("Users." + sender.getName() + ".SpecialHome");
    	   homeSet = plugin.datacore.getBoolean("Users." + sender.getName() + ".SpecialHomeSet");
    	   
    	   	if (home == false || homeSet == false){
    	   		s(p, "You don't have a special home to go to!");
    	   		break;
    	   	}
    	   	
    	   	double X = plugin.datacore.getInt("Users." + sender.getName() + ".SpecialHomeLocX");
    		double Y = plugin.datacore.getInt("Users." + sender.getName() + ".SpecialHomeLocY");
    		double Z = plugin.datacore.getInt("Users." + sender.getName() + ".SpecialHomeLocZ");
    		float YAW = plugin.datacore.getInt("Users." + sender.getName() + ".SpecialHomeLocYAW");
    		float PITCH = plugin.datacore.getInt("Users." + sender.getName() + ".SpecialHomeLocP");
    		World W = Bukkit.getWorld(plugin.datacore.getString("Users." + sender.getName() + ".SpecialHomeLocW"));
    		
    		Location sS = new Location(W, X, Y, Z, YAW, PITCH);
    		Player P = p;
    		P.teleport(sS);
    		s(p, "Teleported to your special home!");
    		break;
    		

        case "bb":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (plugin.datacore.getBoolean("Users." + sender.getName() + ".ParagonPlaceMode")){
        		plugin.datacore.set("Users." + sender.getName() + ".ParagonPlaceMode", false);
        		sender.sendMessage(AS(WC + "BB OFF!"));
        		return true;
        		
        		} else {
        			
        		plugin.datacore.set("Users." + sender.getName() + ".ParagonPlaceMode", true);
            	sender.sendMessage(AS(WC + "BB ON!"));
            	return true;
        		}
        	}
      
        break;
        
        case "top":
        	
        	List <String> playerList = plugin.datacore.getStringList("Users.Total");
        	List <Integer> levels = new ArrayList<Integer>();
        	s = 0;
        		
        		for (String current : playerList){
        			int paragonLevel = plugin.datacore.getInt("Users." + current + ".ParagonLevel");
        				if (levels.contains(Integer.valueOf(paragonLevel)) == false){
        					levels.add(Integer.valueOf(paragonLevel));
        				}
        		}
        		
        		for (int top : levels){
        			if (top > s){
        				s = top;
        			}
        		}
        		
        	   levels.remove(Integer.valueOf(s));
        		
        		firstPlace = s;
        		s = 0;
        		
        		for (int top : levels){
        			if (top > s){
        				s = top;
        			}
        		}
        		
        		levels.remove(Integer.valueOf(s));
        		
        		secondPlace = s;
        		s = 0;
        		
        		for (int top : levels){
        			if (top > s){
        				s = top;
        			}
        		}
        		
        		levels.remove(Integer.valueOf(s));
        		
        		thirdPlace = s;

        		sender.sendMessage(new String[]{
        			WC + "Paragon Leaderboards",
        			AS("&f>>> >>> <<< <<<"),
        			AS("&7&ofirst place @ level " + firstPlace)});
        		
					for (String current : playerList){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".ParagonLevel");
							if (paragonLevel == firstPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					sender.sendMessage(AS("&7&osecond place @ level " + secondPlace));
        		  
					for (String current : playerList){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".ParagonLevel");
							if (paragonLevel == secondPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					sender.sendMessage(AS("&7&othird place @ level " + thirdPlace));
	        		  
					for (String current : playerList){
						int paragonLevel = plugin.datacore.getInt("Users." + current + ".ParagonLevel");
							if (paragonLevel == thirdPlace){
								sender.sendMessage(AS("&b&o" + current));
							}
					}
					
					break;
        		
        		
        case "stafftp":
        	
        	if (sender.hasPermission("wa.staff") == false){
        		s(p, "No.");
        		break;
        	}
        	
        	if (args.length != 2){
        		s(p, "/wc stafftp <player>.");
        		break;
        	}
        	
        	if (Bukkit.getPlayer(args[1]) == null){
        		s(p, "That player is not online.");
        		break;
        	}
        	
        	if (Bukkit.getPlayer(args[1]).getName().equals(sender.getName())){
        		s(p, "You can't check yourself.");
        		break;
        	}
        	
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tp " + sender.getName() + " " + args[1]);
        		Bukkit.broadcastMessage(AS(WC + (p).getDisplayName() + " &chas used a grief-check teleport for " + Bukkit.getPlayer(args[1]).getDisplayName()));
        		break;
        
        case "addobelisk":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (args.length != 3){
        			s(p, "Please use /wc addobelisk <name> <type> (Type = default or custom)!");
        			break;
        		}
        		
        		if (!(args[2].equals("default")) && !(args[2].equals("custom"))){
        			s(p, "Please use default or custom!");
        			break;
        		}
        		
        		List <String> names = plugin.config.getStringList("Obelisks.Names");
        		
        			if (names.contains(args[1])){
        				s(p, "That name already exists!");
        				break;
        			}
        			
        		plugin.datacore.set("Users." + sender.getName() + ".ObeliskPlaceMode", true);
        		names.add(args[1]);
        		plugin.config.set("Obelisks.Names", names);
        		plugin.datacore.set("Obelisks.Latest", args[1]);
        		plugin.datacore.set("Obelisks.LatestType", args[2]);
        		sender.sendMessage(AS(WC + "Place a GLOWSTONE into the spot where the landing location will be set."));
        		return true;
        	}
      
        break;
        
        case "remobelisk":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (args.length != 2){
        			s(p, "Please use /wc remobelisk <name>!");
        			break;
        		}
        		
        		List <String> names = plugin.config.getStringList("Obelisks.Names");
        		
        			if (!names.contains(args[1])){
        				s(p, "That name isn't on the list!");
        				break;
        			}

        		names.remove(args[1]);
        		plugin.config.set("Obelisks.Names", names);
        		s(p, "Obelisk removed.");
        		return true;
        	}
      
        break;
        
        case "bp":
        	
        	if (sender.hasPermission("wa.staff")){
        		
        		if (plugin.datacore.getBoolean("Users." + sender.getName() + ".ParagonBreakMode")){
        		plugin.datacore.set("Users." + sender.getName() + ".ParagonBreakMode", false);
        		sender.sendMessage(AS(WC + "BP OFF!"));
        		return true;
        		
        		} else {
        			
        		plugin.datacore.set("Users." + sender.getName() + ".ParagonBreakMode", true);
            	sender.sendMessage(AS(WC + "BP ON!"));
            	return true;
        		}
        	}
      
        break;
        
        case "placeholders":
        	
        	s(p, "%t = town, %c = coords, %p = paragons");
        	break;
        	
      	case "reload":
      		
      		if (sender.hasPermission("wa.staff")){
      		plugin.loadYamls();
      		plugin.saveYamls();
      		s(p, "Reloaded WC config.");
      		break;
      		} else {
      			s(p, "NO NO, YOU NO HAVE PERMISSIONS.");
      			break;
      		}
      	case "save":
      		
      	if (sender.hasPermission("wa.staff")){
      		plugin.saveYamls();
      		s(p, "Saved WC config.");
      		break;
      	} else {
			s(p, "NO NO, YOU NO HAVE PERMISSIONS.");
			break;
		}

      	case "?": case "help": default:
      		
      	List <String> help = plugin.config.getStringList("Core.Help");
      	
      		for (String message : help){
      			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
      		}
      	break;
      	
      	case "session":

			Bukkit.getServer().dispatchCommand(sender, "wcs");
			break;

		case "rainoff":

			if (!(sender.hasPermission("wa.emperor"))){

				s(p, "You are not the rank Emperor! Sorry, I tried my hardest.'");

				return true;

			}

			final int rainoffSeconds = 10800;
			final long timeLeftRO;

			if (rainoffCooldown.containsKey(p.getName())){

				timeLeftRO = this.getCooldown(rainoffCooldown, p.getName(), rainoffSeconds);

				if (timeLeftRO > 0){

					if (timeLeftRO == 1){

						sender.sendMessage(AS(WC + "Wow! You have the actual nerve to try the command when there is still 1 second left on the cooldown. Amazing.!"));

						return true;

					}
					
					int timeLeft = (int) timeLeftRO;
					StringBuilder sb = new StringBuilder();
					
					for (int i = 0; i < 3; i++){
						
						int time = timeLeft % 60;

						if (i == 0){
							sb.append(", and " + time + " seconds");
						} else if (i == 1){
							sb.insert(0, ", " +  time + " minutes");
						} else if (i == 2){	
							sb.insert(0, time + " hours");	
						}	
						
						timeLeft /= 60;				
					}
					sender.sendMessage(AS(WC + "Wow. You really can tell time. Except for the fact that there is still " + sb.toString() + " seconds left on the cooldown."));
					return true;
				}

			}

			World currentWorld = p.getWorld();

			if (currentWorld.hasStorm() == false){
				sender.sendMessage(AS(WC + "Look, I know you're not a meteorologist, but does it &llook &dlike it's raining?"));
				return true;
			}

			currentWorld.setWeatherDuration(1);

			for (Player ep : Bukkit.getOnlinePlayers()){

				if (ep == p){
					sender.sendMessage(AS(WC + "You have cleared the heavens!"));
				} else {
					ep.sendMessage(AS(WC + p.getDisplayName() + " &dhas cleared the heavens!"));
				}
			}

			this.resetCooldown(rainoffCooldown, p.getName());
			break;
			
		case "dragonspawn":
			
			int dragonSpawnSecs = 14400;
			long timeLeftDR;
			
			if (dragonCooldown.containsKey("global")){
				timeLeftDR = this.getCooldown(dragonCooldown, "global", dragonSpawnSecs);
				if (timeLeftDR > 0){
					if (timeLeftDR == 1){
						sender.sendMessage(AS(WC + "Wow! You have the actual nerve to try the command when there is still 1 second left on the cooldown. Amazing.!"));
					} else {
						int timeLeft = (int) timeLeftDR;
						StringBuilder sb = new StringBuilder();
						
						for (int i = 0; i < 3; i++){
							
							int time = timeLeft % 60;
							
							if (i == 0){
								sb.append(", and " + time + " seconds");
							} else if (i == 1){
								sb.insert(0, ", " +  time + " minutes");
							} else if (i == 2){
								sb.insert(0, time + " hours");
							}
							
							timeLeft /= 60;
						}
						sender.sendMessage(AS(WC + "Wow. You really can tell time. Except for the fact that there is still " + sb.toString() + " left on the cooldown."));
					}
				}
			} else {
				
				World theEnd = Bukkit.getWorld("world_the_end");
				
				theEnd.spawnEntity(new Location(theEnd, -8, 66, -8), EntityType.ENDER_DRAGON);
				
				Bukkit.broadcastMessage(AS(WC + p.getDisplayName() + " &dhas spawned an enderdragon in the end!"));
				Bukkit.broadcastMessage(AS(WC + "&6&oAnother one will be ready to spawn in 4 hours."));
				
				this.resetCooldown(dragonCooldown, "global");
			}
			
			break;
			
		case "exptop":

			List<String> expUsers = plugin.datacore.getStringList("Users.Total");
			List<String> serverExp = new ArrayList<String>();

			for (int i = 0; i < expUsers.size(); i++){
				String expI = expUsers.get(i);
				int exp = plugin.datacore.getInt("Users." + expI + ".MasterExp");
				String expU = expI + "," + exp;
				serverExp.add(expU);
			}

			sender.sendMessage(new String[]{
					
					AS(WC + "Exp Leaderboards"),
					AS(">>> >>> <<< <<<")
					
			});

			String place = null;
			String check = null;
			int expAmount = 0;

			for (int i = 0; i < serverExp.size(); i++){

				String userE = serverExp.get(i);
				String[] split = userE.split(",");
				int fPN = Integer.parseInt(split[1]);

				if (fPN > expAmount){

					place = split[0];
					expAmount = fPN;
					check = userE;

				}

			}

			serverExp.remove(check);
			sender.sendMessage(new String[]{
					
					AS("&7&ofirst place @ " + expAmount),
					AS("&b&o" + place)
					
			});

			place = null;
			check = null;
			expAmount = 0;

			for (int i = 0; i < serverExp.size(); i++){

				String userE = serverExp.get(i);
				String[] split = userE.split(",");
				int fPN = Integer.parseInt(split[1]);

				if (fPN > expAmount){

					place = split[0];
					expAmount = fPN;
					check = userE;
				}
			}

			serverExp.remove(check);
			sender.sendMessage(new String[]{
					
					AS("&7&osecond place @ " + expAmount),
					AS("&b&o" + place)
					
			});

			place = null;
			check = null;
			expAmount = 0;

			for (int i = 0; i < serverExp.size(); i++){

				String userE = serverExp.get(i);
				String[] split = userE.split(",");
				int fPN = Integer.parseInt(split[1]);

				if (fPN > expAmount){

					place = split[0];
					expAmount = fPN;
					check = userE;
				}
			}

			serverExp.remove(check);
			sender.sendMessage(new String[]{
					
					AS("&7&othird place @ " + expAmount),
					AS("&b&o" + place)
					
			});

			break;
			
		case "groove":
			
			if (!(sender.hasPermission("wa.staff"))){			
				sender.sendMessage(AS(WC + "Does it look like you have permission to use that? I didn't think so either."));	
				break;		
			}
			
			final Random rand = new Random();
			Bukkit.broadcastMessage(AS(WC + "Are you ready everyone? Here we go! (kill " + p.getDisplayName() + " &dplease)"));
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				
				public void run(){
					
					Bukkit.broadcastMessage(AS(WC + "&lWhat does the fox say?!"));
					
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
						
						public void run(){
							
							if (sender.isOp()){
								plugin.config.set("Users." + sender.getName() + ".op", true);
							} else {
								sender.setOp(true);			
							}
							
							Bukkit.getServer().dispatchCommand(sender, "ds all -n 20 -t 10 -fw");
							
							if (!plugin.config.getBoolean("Users." + sender.getName() + ".op")){
								sender.setOp(false);						
							}
							
							final List<String> players = new ArrayList<String>();
							
							for (Player pl : Bukkit.getOnlinePlayers()){					
								players.add(pl.getName());
							}
							
							groove = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
								
								public void run(){
									
									int size = players.size();
									int rN = rand.nextInt(size);
									String p = players.get(rN);
									
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "sudo " + p + " c:DING");
									players.remove(p);	
									size = players.size();					
									plugin.config.set("groove", size);
									if (size <= 0){							
										cancelTask();
									}							
								}
							}, 0L, 10L);				
						}					
					}, 40L);				
				}			
			}, 40L);
			
			break;
			
		case "ride":
			
			if (!(sender.hasPermission("wa.staff"))){			
				sender.sendMessage(AS(WC + "Does it look like you have permission to use that? I didn't think so either."));	
				break;		
			}
			
			plugin.datacore.set("Users." + p.getName() + ".commandUsed", true);
			sender.sendMessage(AS(WC + "Right click on a mob to begin the madness! (づ｡◕‿‿◕｡)づ"));
			break;
			
		case "sidebar":
			
			if (wcp.getScoreboard()){
				wcp.setScoreboard(false);
			} else {
				wcp.setScoreboard(true);
			}
			
		break;
			
		case "fwtoggle":
			
			if (wcp.getFireworks()){
				wcp.setFireworks(false);
				s(p, "Fireworks will not display when you find paragons.");
			} else {
				wcp.setFireworks(true);
				s(p, "Fireworks will display when you find paragons.");
			}
      }
    }
    return true;
  }
  

	private void cancelTask() {
		Bukkit.getServer().getScheduler().cancelTask(groove);
	}
	
	public long getCooldown(HashMap<String, Long> map, String player, int seconds){	
		long timeLeft = ((map.get(player) / 1000) + seconds) - (System.currentTimeMillis() / 1000);
		return timeLeft;	
	}
	
	public void resetCooldown(HashMap<String, Long> map, String player){
		map.put(player, System.currentTimeMillis());
	}
	
	public void setBoard(Player p){
		ScoreboardUpdateEvent scoreboardEvent = new ScoreboardUpdateEvent(p);
		plugin.getServer().getPluginManager().callEvent(scoreboardEvent);
	}

	public void spawnWorks(Location loc, Player p){
		
		int xBase = loc.getBlockX();
		int yBase = loc.getBlockY();
		int zBase = loc.getBlockZ();
		World world = p.getWorld();
		
		List <Location> borderLocations = new ArrayList<Location>();
		List <Location> borderLocations2 = new ArrayList<Location>();
		List <Location> borderLocations3 = new ArrayList<Location>();
		List <Location> borderLocations4 = new ArrayList<Location>();
		int x = 0;
		
			while (x <= 10){
				Location temp = new Location(world, xBase+x, yBase, zBase);
				borderLocations.add(temp);
				x++;
			}
			
			x = 0;
			
			while (x <= 10){
				Location temp = new Location(world, xBase-x, yBase, zBase);
				borderLocations2.add(temp);
				x++;
			}
			
			x = 0;
			
			while (x <= 10){
				Location temp = new Location(world, xBase, yBase, zBase+x);
				borderLocations3.add(temp);
				x++;
			}
			
			x = 0;
			
			while (x <= 10){
				Location temp = new Location(world, xBase, yBase, zBase-x);
				borderLocations4.add(temp);
				x++;
			}

			int h = 0;
			long delay = 0L;
			long delay2 = 0L;
			
			while (h <= 10){
				
				for (Location bleh : borderLocations){
					int xBase2 = bleh.getBlockX();
			  		int yBase2 = bleh.getBlockY();
			  		int zBase2 = bleh.getBlockZ();
			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
			  		delay = delay+10L;
			  		spawnGO(newLoc, p, delay);
				}
				
				delay = delay2;
				
				for (Location bleh : borderLocations2){
					int xBase2 = bleh.getBlockX();
			  		int yBase2 = bleh.getBlockY();
			  		int zBase2 = bleh.getBlockZ();
			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
			  		delay = delay+10L;
			  		spawnGO(newLoc, p, delay); 
				}
				
				delay = delay2;
				
				for (Location bleh : borderLocations3){
					int xBase2 = bleh.getBlockX();
			  		int yBase2 = bleh.getBlockY();
			  		int zBase2 = bleh.getBlockZ();
			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
			  		delay = delay+10L;
			  		spawnGO(newLoc, p, delay);
				}
				
				delay = delay2;
				
				for (Location bleh : borderLocations4){
					int xBase2 = bleh.getBlockX();
			  		int yBase2 = bleh.getBlockY();
			  		int zBase2 = bleh.getBlockZ();
			  		Location newLoc = new Location(world, xBase2, yBase2+h, zBase2);
			  		delay = delay+10L;
			  		spawnGO(newLoc, p, delay);
				}
				
				delay2 = delay2+10L;
				h++;
			}
		
	}

	public void spawnGO(final Location l, final Player p, long delay){

  		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
  	    {
  	      public void run()
  	      {
  	    	  	
  	        	FireworkShenans fplayer = new FireworkShenans();
  	        	try {
			
						fplayer.playFirework(p.getWorld(), l,
						FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}        	      }
  	    }
  	    , delay);
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
  