package com.github.lyokofirelyte.WC;

import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;
import static com.github.lyokofirelyte.WCAPI.WCUtils.bc;
import static com.github.lyokofirelyte.WCAPI.WCUtils.circle;
import static com.github.lyokofirelyte.WCAPI.WCUtils.isInteger;
import static com.github.lyokofirelyte.WCAPI.WCUtils.s;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;
import com.dsh105.holoapi.api.HologramFactory;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.WCAlliance;
import com.github.lyokofirelyte.WCAPI.WCUtils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WCAPI.Manager.SkillType;

public class WCCommands {
	
  private HashMap<String, Long> rainoffCooldown = new HashMap<String, Long>();
  private HashMap<String, Long> dragonCooldown = new HashMap<String, Long>();
	
  WCMain plugin;
  WCPlayer wcp;
  String WC = "§dWC §5// §d";
  Boolean home;
  Boolean homeSet;
  public int task;
  Player p;
  List <Integer> laserFwTasks = new ArrayList<Integer>();
  int ltask = -1;
  public static Vector vec = new Vector();

  public WCCommands(WCMain instance){
	  plugin = instance;
  }  	

	@SuppressWarnings("deprecation")
	@WCCommand(aliases = {"wc", "watercloset"}, desc = "WC Root Menu", help = "/wc", player = true)
	public void onWC(final Player p, String[] args){

      wcp = plugin.wcm.getWCPlayer(p.getName());

      if (args.length < 1) {
        s(p, "I'm not sure what you mean. Try /wc help or /wc ?. Also, /root is very helpful.");
        return;
      }
      
      switch (args[0].toLowerCase()){
      
      case "patrolreset":
    	  
    	  if (p.getName().equals("msnijder30")){
    		  plugin.wcpp.startPatrol();
    	  }
    	  
    	  break;
      
      case "effecttest":
			plugin.wcm.getWCPlayer(p.getName()).setHomeEffect(p.getItemInHand().getType().toString());
  			plugin.api.wcutils.effects(p, p.getLocation());
    	  break;

      case "setchathologram":
    	  
    	  if (p.hasPermission("wa.staff")){
    		  
    		  Hologram hologram = new HologramFactory(plugin)
    		    .withLocation(new Vector(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()), "world")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .withText(".")
    		    .build();
    		  
    		  /*String ID = plugin.wcm.getWCSystem("system").getHolograms().get("Spawn");
    		  Hologram holoSpawn = HoloAPI.getManager().getHologram(ID);
    		  holoSpawn.*/
    		  plugin.wcm.getWCSystem("system").getHolograms().put("Spawn", hologram.getSaveId());
    	  }
    	  
      break;
      
      case "hud":
    	  
    	  if (!plugin.wcm.getWCSystem("system").getHolograms().containsKey(p.getName())){
    	  
	    	  Hologram hologram = new HologramFactory(plugin)
	    	  	.withLocation(p.getEyeLocation())
	    	  	.withText(AS("&7" + WCUtils.getTime() + " " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ()))
	    	  	.withText(".")
	    	  	.withText(".")
	    	  	.withText(".")
	    	  	.build();
	    	  
	    	  hologram.clearAllPlayerViews();
	    	  hologram.show(p);
	    	  
	    	  plugin.wcm.getWCSystem("system").getHolograms().put(p.getName(), hologram.getSaveId());
	    	  s(p, "HUD created!");
	    	  
    	  } else {
    		  HoloAPI.getManager().getHologram(plugin.wcm.getWCSystem("system").getHolograms().get(p.getName())).clearAllPlayerViews();
    		  plugin.wcm.getWCSystem("system").getHolograms().remove(p.getName());
    	  }
    	  
      break;

      case "troll":
    	  
    	  long delay = 0L;
    	  
    	  if (p.hasPermission("wa.staff")){
    	    	for (int x = 7; x > 0; x--){
    	    		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
    	    		  public void run() { 
    	    				  
    	        		  for (Player p : Bukkit.getOnlinePlayers()){
    	        			  p.openInventory(p.getInventory());
    	        		  }
    	    			  
    	    		  } }, delay);
    	    		  
    	    		  delay = delay + 10L;
    	    		  
    	    		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
    	    		  public void run() { 
    	    				  
    	        		  for (Player p : Bukkit.getOnlinePlayers()){
    	        			  p.closeInventory();
    	        		  }
    	    			  
    	    		  } }, delay);
    	    		  
    	    		  delay = delay + 10L;
    	    	}
    	  }
    	  
      break;
      
      case "websitereset":
    	  
    	  if (p.getName().equals("Hugh_Jasses")){
    		  plugin.wcm.getWCPlayer(args[1]).setWebsiteRegistered(false);
    	  }
    	  
      break;
      
      case "website":
    	  
    	  if (!wcp.websiteRegistered()){
    		  
    		String code = WCUtils.encrypt(p.getName(), "MD5").substring(0, 5);
    		  
    		try {
    		  
	    		Connection conn = DriverManager.getConnection("jdbc:mysql://99.63.17.88:3306/website", "david", "coltonishot");
	  	        PreparedStatement pst = conn.prepareStatement("INSERT INTO users_code(username, code) VALUES(?, ?)");
	  	        pst.setString(1, p.getName());
	  	        pst.setString(2, code);
	  	        pst.executeUpdate();
	  	        pst.close();
	  	        conn.close();
	  	        
    			s(p, "New code created. Your code is &6" + code);
    			wcp.setWebsiteCode(code);
    			wcp.setWebsiteRegistered(true);
    			updatePlayer(wcp, p.getName());
	  	        
    		} catch (Exception e){
    			e.printStackTrace();
    			s(p, "An error occured. See console.");
    		}
    		
    	  } else {
    		  s(p, "Your code is &6" + wcp.getWebsiteCode());
    	  }
    	  
      break;
      
      case "wcmmodebug":
    	  
    	  if (p.getName().equals("Hugh_Jasses")){
    		  for (SkillType s : SkillType.values()){
    			  plugin.wcm.getWCPlayer(p.getName()).skills().put(s.name(), 99);
    		  }
    	  }
    	  
      break;
      
      case "setexp":
    	  
    	  if (p.hasPermission("wa.mod2")){
    		  if (args.length != 3 || !WCUtils.isInteger(args[2]) || plugin.wcm.getWCPlayer(args[1]) == null){
    			  s(p, "/wc setexp <player> <amount>. You either didn't type a number or the player was not found.");
    		  } else {
    			  plugin.wcm.getWCPlayer(args[1]).setExp(Integer.parseInt(args[2]));
    			  s(p, "Updated!");
    		  }
    	  }
    	  
      break;
      
      case "chatbar":
    	  
    	  if (wcp.useChatBar()){
    		  wcp.setChatBar(false);
    		  s(p, "You are no longer using the chat bar.");
    	  } else {
    		  wcp.setChatBar(true);
    		  s(p, "You are now using the chat bar.");
    	  }
    	  
    	  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    	  
      break;
      
      case "creativerank":
    	  
    	  if (args.length < 3){
    		  s(p, "/wc creativerank <player> <rank>");
    	  } else if (p.hasPermission("wa.mod2")){
    		  WCPlayer giveRank = plugin.wcm.getWCPlayer(args[1]);
    		  if (giveRank == null){
    			  s(p, "That player was not found in the API!");
    		  } else {
    			  giveRank.setCreativeRank(WCUtils.createString(args, 2));
    			  s(p, "Updated!");
    		  }
    	  } else {
    		  s(p, "Nope!");
    	  }
    	  
      break;
      
      case "creative":
    	  
    	  if (!p.isOp()){
    		  p.setOp(true);
    		  p.performCommand("warp wacp");
    		  p.setOp(false);
    	  } else {
    		  p.performCommand("warp wacp");
    	  }
    	  
      break;

      case "reloadmarkkitconfig":
    	  if(p.hasPermission("wa.staff")){
    		  try {
				plugin.markkitYaml.load(plugin.markkitFile);
			} catch (Exception e){
				
			}
    	  }
    	  break;
    	  
      case "setmarket":
    	  if(args.length < 4){
    		  s(p, "Correct usage: /wc setmarket <the full buyprice> <the full sellprice> <inventory name>");
    		  return;
    	  }
    	  if(!WCUtils.isInteger(args[1]) || !WCUtils.isInteger(args[2])){
    		  s(p, "That is not a number!");
    		  return;
    	  }
    	  if(!p.hasPermission("wa.staff")){
    		  s(p, "You do not have permission!");
    		  return;
    	  }
    	  if(p.getItemInHand().getAmount() == 64){
    		  
	    	  int buyprice = Integer.parseInt(args[1]);
	    	  int sellprice = Integer.parseInt(args[2]);
	    	  String name = args[3].replace("-", " ");
	    	  ItemStack full = p.getInventory().getItem(0);
	    	  
	    	  
	    	  plugin.markkitYaml.set("Items." + name, null);
	    	  
	    	  plugin.markkitYaml.set("Items." + name + ".ID", full.getTypeId());
	    	  plugin.markkitYaml.set("Items." + name + ".Damage", full.getDurability());
    		  
	    	  plugin.markkitYaml.set("Items." + name + ".64.buyprice", buyprice);
    		  plugin.markkitYaml.set("Items." + name + ".64.sellprice", sellprice);
    		  
	    	  if(buyprice/2 >= 1){
    		  plugin.markkitYaml.set("Items." + name + ".32.buyprice", buyprice/2);
    		  plugin.markkitYaml.set("Items." + name + ".32.sellprice", sellprice/2);
	    	  }
	    	  
	    	  if(buyprice/4 >= 1){
    		  plugin.markkitYaml.set("Items." + name + ".16.buyprice", buyprice/4);
    		  plugin.markkitYaml.set("Items." + name + ".16.sellprice", sellprice/4);
	    	  }
	    	  
	    	  if(buyprice/8 >= 1){
    		  plugin.markkitYaml.set("Items." + name + ".8.buyprice", buyprice/8);
    		  plugin.markkitYaml.set("Items." + name + ".8.sellprice", sellprice/8);
	    	  }
	    	 	    	  
	    	  if(buyprice/64 >= 1){
    		  plugin.markkitYaml.set("Items." + name + ".1.buyprice", buyprice/64);
    		  plugin.markkitYaml.set("Items." + name + ".1.sellprice", sellprice/64);
	    	  }
	    	  
    	  }else if(p.getItemInHand().getAmount() == 1){
    		  
    		  int buyprice = Integer.parseInt(args[1]);
    		  int sellprice = Integer.parseInt(args[2]);
    		  String name = args[3].replace("-", " ");
	    	  ItemStack full = p.getInventory().getItem(0);
	    	  
	    	  
	    	  plugin.markkitYaml.set("Items." + name, null);
	    	  
	    	  plugin.markkitYaml.set("Items." + name + ".ID", full.getTypeId());
	    	  plugin.markkitYaml.set("Items." + name + ".Damage", full.getDurability());
    		  
    		  plugin.markkitYaml.set("Items." + name + ".1.buyprice", buyprice);
    		  plugin.markkitYaml.set("Items." + name + ".1.sellprice", sellprice);
    		  
    	  }
      break;
      
      case "revokemails":
    	  
    	  if (p.getName().equals("Hugh_Jasses")){
    		  for (String s : plugin.wcm.getWCSystem("system").getUsers()){
    			  WCPlayer wcpCurrent = plugin.wcm.getWCPlayer(s);
    			  wcpCurrent.setMail(new ArrayList<String>());
    			  plugin.wcm.updatePlayerMap(s, wcpCurrent);
    		  }
    		  s(p, "Wiped all mails.");
    	  }
    	  
      break;
      
      
      case "allowdeathmessage":
    	  
    	  if (wcp.getAllowDeathLocation()){
    		  wcp.setAllowDeathLocation(false);
    	  } else {
    		  wcp.setAllowDeathLocation(true);
    	  }
    	  
    	  plugin.wcm.updatePlayerMap(p.getName(), wcp);  
    	  s(p, "Updated. Wow, you actually use this feature? Glad I didn't waste my time.");
    	  
      break;
      
      case "hugdebug":
    	  
    	  if (p.getName().equals("Hugh_Jasses")){
    		  p.getInventory().addItem(plugin.invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 10, 11, Material.INK_SACK, 1));
  			  ItemStack i = plugin.invManager.makeItem("§3Soul Split", "", true, Enchantment.DURABILITY, 10, 0, Material.ARROW, 1);
  			  ItemMeta im = i.getItemMeta();
  			  List<String> lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Steals life from monsters");
  			  lore.add("§3and adds to patrol members");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Defender Shield", "", true, Enchantment.DURABILITY, 10, 0, Material.BOWL, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3The wearer takes 25%");
  			  lore.add("§3of other patrol member's");
  			  lore.add("§3incoming damage");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Teleport Cube", "", true, Enchantment.DURABILITY, 10, 0, Material.GLASS, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Allows teleportation to");
  			  lore.add("§3other patrol members while");
  			  lore.add("§3worn. /ptp <member>");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Build Charm", "", true, Enchantment.DURABILITY, 10, 0, Material.APPLE, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Allows building in");
  			  lore.add("§3hotspot zones while worn");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Quick Bone", "", true, Enchantment.DURABILITY, 10, 0, Material.BONE, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Allows faster walk speed");
  			  lore.add("§3while worn");
  			  lore.add("§4UNLOCK AT LEVEL 10");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Majjykk Stick", "", true, Enchantment.DURABILITY, 10, 0, Material.STICK, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Randomly blocks attacks");
  			  lore.add("§3from incoming monsters");
  			  lore.add("§4UNLOCK AT LEVEL 20");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
  			  i = plugin.invManager.makeItem("§3Destruction Ward", "", true, Enchantment.DURABILITY, 10, 0, Material.PORTAL, 1);
  			  im = i.getItemMeta();
  			  lore = new ArrayList<String>();
  			  lore.add("§b§lPATROL ITEM");
  			  lore.add("§3Monsters will randomly");
  			  lore.add("§3explode during combat");
  			  lore.add("§4UNLOCK AT LEVEL 25");
  			  im.setLore(lore);
  			  i.setItemMeta(im);
  			  p.getInventory().addItem(i);
    	  }
    	  
      break;
      
      case "rootshortcut":
    	 
    	  if (wcp.getRootShortCut()){
    		  wcp.setRootShortCut(false);
    		  s(p, "You won't trigger the root menu with shift-left click anymore.");
    	  } else {
    		  wcp.setRootShortCut(true);
    		  s(p, "You will trigger the root menu with shift-left click now.");
    	  }
    	  
    	  updatePlayer(wcp, p.getName());
    	  
      break;
      
      case "sideboardcoords":
    	  
    	  if (wcp.getScoreboardCoords()){
    		  wcp.setScoreboardCoords(false);
    		  s(p, "Coord display inactive.");
    	  } else {
    		  wcp.setScoreboardCoords(true);
    		  s(p, "Coord display active.");
    	  }
    	  
    	  updatePlayer(wcp, p.getName());
  
      break;
      
      case "nameplate":
    	  
    	  if (wcp.getNamePlate()){
    		  wcp.setNamePlate(false);
    		  s(p, "Your name-plate is now default. You will need to re-log for others to see your skin.");
    	  } else {
    		  wcp.setNamePlate(true);
    		  s(p, "Your name-plate now has alliance colors. You will need to re-log for this to take effect.");
    	  }
    	  
    	  updatePlayer(wcp, p.getName());
    	  
      break;

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
  
                for (Entity e1 : p.getNearbyEntities(15.0D, 15.0D, 15.0D)){
                e1.setVelocity(e1.getLocation().getDirection().multiply(-5));
                }

        		List<Location> circleblocks = circle(pq.getLocation(), 1, 1, true, false, 0);
        		List<Location> circleblocks2 = circle(pq.getLocation(), 2, 1, true, false, 1);
        		List<Location> circleblocks3 = circle(pq.getLocation(), 3, 1, true, false, 1);
        		List<Location> circleblocks4 = circle(pq.getLocation(), 4, 1, true, false, 1);
        		List<Location> circleblocks5 = circle(pq.getLocation(), 5, 1, true, false, 1);
        		List<Location> circleblocks6 = circle(pq.getLocation(), 6, 1, true, false, 1);
        		pq.getWorld().playSound(pq.getLocation(), Sound.BLAZE_HIT, 3.0F, 0.5F);
        		delay = 0L;
        		
        			for (final Location l : circleblocks){
        				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
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
    	  
    	  p.sendMessage(AS(WC + "You've changed your color to &" + args[1] + "this."));
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
 	      	  
      case "paragonhamdrax":
    	  
    	  if (checkInv(p, 64)){
    		  
	    	    ItemStack hamdrax = plugin.invManager.makeItem(AS("§a§o§lHAMDRAX OF " + p.getDisplayName()), "§7§oForm: Pick", true, Enchantment.DIG_SPEED, 5, 1, Material.DIAMOND_PICKAXE, 1);
	    	    hamdrax.getItemMeta().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, true);
	    	    hamdrax.getItemMeta().addEnchant(Enchantment.DURABILITY, 3, true);
	    	    hamdrax.getItemMeta().addEnchant(Enchantment.PROTECTION_FIRE, 10, true);
		        hamdrax.setDurability((short) 780);
		        p.getInventory().addItem(hamdrax);
		        p.updateInventory();
		        Bukkit.broadcastMessage(AS(WC + p.getDisplayName() + " has purchased a Hamdrax!"));
		        break;
    	  }
    	  
    	  s(p, "You lack the funds to do this. (64 tokens)");
    	  
	  break;

      case "paragonhamdraxrepair":
    	  
    	  if (checkInv(p, 15)){
	    	  if (p.getItemInHand().hasItemMeta()){
					if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().hasDisplayName()){
						if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains("HAMDRAX")){
							p.getItemInHand().setDurability((short) 0);
							s(p, "All good! :D");
							break;
						}
					}
				}
	    	  s(p, "Please hold the hamdrax in your hand!");
			  returnItem(p, 15);
	    	  break;
	  		}
			
			s(p, "You lack the funds to do this (15 tokens).");
			
	  break;
	  
      case "paragonback":
    	  
    	  if (checkInv(p, 10)){
    		  wcp.setParagonBacks(wcp.getParagonBacks() + 1);
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "Added 1 back!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (10 tokens).");
    	  
      break;
      
      case "paragontp":
    	  
    	  if (checkInv(p, 15)){
    		  wcp.setParagonTps(wcp.getParagonTps() + 3);
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "Added 3 Tps!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (15 tokens).");
    	  
      break;
      
      case "paragonhearts":
    	  
    	  if (checkInv(p, 10)){
    		  p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 1));
    		  s(p, "Boosted!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (10 tokens).");
    	  
      break;
      
      case "paragonarmor":
    	  
    	  if (checkInv(p, 5)){
    		  p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 1));
    		  s(p, "Boosted!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (5 tokens).");
    	  
      break;
      
      case "paragonarmor2":
    	  
    	  if (checkInv(p, 20)){
    		  p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 2));
    		  s(p, "Boosted!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (20 tokens).");
    	  
      break;
      
      case "paragonhome":
    	  
    	  if (checkInv(p, 10)){
    		  if (wcp.getParagonSpecialHomeSet()){
    			  s(p, "You already have you home set. You can change the location by purchasing a reset token.");
    			  returnItem(p, 10);
    			  break;
    		  }
    		  wcp.setParagonSpecialHomeSet(true);
    		  wcp.setParagonSpecialHome(p.getLocation());
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "Home set! Use /wc home to return here. To set a new one, purchase a reset token from /root.");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (10 tokens).");
    	  
      break;
      
      case "paragonresethome":
    	  
    	  if (checkInv(p, 20)){
    		  if (!wcp.getParagonSpecialHomeSet()){
    			  s(p, "You don't have a home set, so why waste money?");
    			  returnItem(p, 20);
    			  break;
    		  }
    		  wcp.setParagonSpecialHomeSet(false);
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "You can now re-purchase a WC special home. Yes, you have to buy it again. :)");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (20 tokens).");
    	  
      break;
      
      case "paragonteleport":
    	  
    	  if (checkInv(p, 30)){
    		  if (p.getInventory().firstEmpty() == -1){
    			  s(p, "You inventory is full.");
    			  returnItem(p, 30);
    			  break;
    		  }
    		  p.getInventory().addItem(plugin.invManager.makeItem("§aTELEPORT PAD", "§2Connect two with active redstone", false, Enchantment.OXYGEN, 0, 1, Material.DAYLIGHT_DETECTOR, 1));
    		  s(p, "Purchase complete.");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this. (30 tokens).");
    	  
      break;
      
      case "paragonspawnegg":
    	  
    	  int count = 0;
    	  
    	  if (checkInv(p, 25)){
    		  for (ItemStack i : p.getInventory()){
    			  if (i == null || i.getType().equals(Material.AIR)){
    				  count++;
    			  }
    		  }
    		  if (count < 5){
    			  s(p, "Your inventory is full. You should fix that. You need 5 slots open.");
    			  returnItem(p, 25);
    			  break;
    		  }
    		  p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (byte) 90));
    		  p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (byte) 95));
    		  p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (byte) 91));
    		  p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (byte) 92));
    		  p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (byte) 120));
    		  s(p, "Purchase complete.");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (25 tokens).");
    	  
      break;
      
      case "paragonobelisk":

    	  if (checkInv(p, 80)){
    		  if (wcp.getInAlliance() && wcp.getAllianceRank().equals("Leader")){
    			  if (plugin.wcm.getWCAlliance(wcp.getAlliance()).getTier() >= 6){
    				  for (String s : plugin.systemYaml.getStringList("TotalUsers")){
    					  if (WCVault.perms.has("world", s, "wa.staff")){
    						  WCPlayer receiver = plugin.wcm.getWCPlayer(s);
    						  List<String> mail = receiver.getMail();
    						  mail.add("&5| &6system &f// &d" + p.getName() + " &dis approved for an obelisk!");
    						  receiver.setMail(mail);
    						  updatePlayer(receiver, s);
    						  if (Bukkit.getOfflinePlayer(s).isOnline()){
    							  s(Bukkit.getPlayer(s), "Mail received about an obelisk! Check it with /mail read.");
    						  }
    					  }
    				  }
    			  }
    		  }
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (80 tokens), you are not a leader, or you are not tier 6. One or more of those things.");
    	  
      break;
      
      case "joinmessage":
    	  
    	  if (wcp.getMessageCount() < 1){
    		  s(p, "You don't have any messages left to set.");
    		  break;
    	  }
    	  
    	  if (args.length == 1){
    		  s(p, "/wc joinmessage <message>.");
    		  break;
    	  }
    	  
    	  wcp.setJoinMessage(WCUtils.createString(args, 1));
    	  wcp.setMessageCount(wcp.getMessageCount() - 1);
    	  updatePlayer(wcp, p.getName());
    	  s(p, "Your join message has been changed! You have " + wcp.getMessageCount() + " &dchanges left.");
    	  
      break;
      
      case "quitmessage":
    	  
    	  if (wcp.getMessageCount() < 1){
    		  s(p, "You don't have any messages left to set.");
    		  break;
    	  }
    	  
    	  if (args.length == 1){
    		  s(p, "/wc quitmessage <message>.");
    		  break;
    	  }
    	  
    	  wcp.setQuitMessage(WCUtils.createString(args, 1));
    	  wcp.setMessageCount(wcp.getMessageCount() - 1);
    	  updatePlayer(wcp, p.getName());
    	  s(p, "Your quit message has been changed! You have " + wcp.getMessageCount() + " &dchanges left.");
    	  
      break;
      
      case "paragonmessage":
    	  
    	  if (checkInv(p, 20)){
    		  wcp.setMessageCount(wcp.getMessageCount() + 4);
    		  updatePlayer(wcp, p.getName());
    		  s(p, "You have added 4 changes. Setting a join or quit message will take one of your allowed changes. Type /wc joinmessage <message> and /wc quitmessage <message>.");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (20 tokens).");
    	  
      break;
      
      case "paragonmarket":
    	  
    	  if (checkInv(p, 20)){
    		  if (wcp.getParagonMarket()){
    			  s(p, "You already have /wc market.");
    			  break;
    		  }
    		  wcp.setParagonMarket(true);
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "You can now use /wc market to TP directly to the market.");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (20 tokens).");
    	  
      break;
      
      case "paragonmoney":
    	  
    	  if (checkInv(p, 45)){
    		  if (wcp.getParagonMoney()){
    			  s(p, "You have already claimed your cash.");
    			  break;
    		  }
    		  wcp.setParagonMoney(true);
    		  wcp.setBalance(wcp.getBalance() + 85000);
    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
    		  s(p, "Money!");
    		  break;
    	  }
    	  
    	  s(p, "You lack the funds to do this (45 tokens).");
    	  
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
          	        	try {
        			
    							plugin.fw.playFirework(p.getWorld(), self,
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
    	  
    	  p.sendMessage(AS(WC + "You've changed your color to &" + args[1] + "this."));
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
    		  
    		  int xp = wcp.getExp();
    		  s(p, "You currently have §6" + xp + " §dexp stored. (/wc xp take <amount>)");
    		  int l30 = (xp / 825);
    		  s(p, "That's §6" + l30 + "§d level 30's.");
    		  break;
    	  }
     
    	  
    	  if(args[1].equalsIgnoreCase("sell")){
    		  if(WCUtils.isInteger(args[2])){
	    		  if(wcp.getExp() >= Integer.parseInt(args[2]) && Integer.parseInt(args[2]) > 0){
	    			  wcp.setExp(wcp.getExp() - Integer.parseInt(args[2]));
	    			  wcp.setBalance(wcp.getBalance() + Integer.parseInt(args[2]) * 2);
	    			  plugin.wcm.updatePlayerMap(p.getName(), wcp);
	    			  WCUtils.s(p, "You sold " + args[2] + " experience for " + Integer.parseInt(args[2]) * 2 + " shinies total!");
	    		  }
    		  }else{
    			  WCUtils.s(p, "That's not a number!");
    		  }
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
    		  
    		  wcp.setDepositExp(true);
    		  updatePlayer(wcp, p.getName());
    		  
    		  wcp.setExp(xp-Integer.parseInt(args[2]));
    		  p.giveExp(Integer.parseInt(args[2]));
    		  s(p, "You've taken some XP out of your storage facility.");
    		  
    		  wcp.setDepositExp(false);
    		  updatePlayer(wcp, p.getName());
    		  break;
    	  }
    	  
    	  if (args[1].equalsIgnoreCase("store")){
    		s(p, "Sorry, you can't put the xp back because of a Bukkit bug I can't do anything about.");
    	  }
//    		  if(!plugin.expToggle){
//    			  s(p, "exp depositing is disabled.");
//    		  }else{
//    			  if(p.getWorld().getName().equalsIgnoreCase("world_the_end") || p.getWorld().getName().equalsIgnoreCase("world_nether") || p.getWorld().getName().equalsIgnoreCase("world")){
//		    		  if(isInteger(args[2])){
//		    			  int amount = Integer.parseInt(args[2]);
//		    			  if(amount > 0){
//		    				  if(amount <= p.getTotalExperience()){
//		    					  if(p.getTotalExperience() - amount >= 0){
//					    		  int exp = p.getTotalExperience() - amount;
//					    		  p.setTotalExperience(0);
//					    		  p.setExp(0);
//					    		  p.setLevel(0);
//					    		  p.giveExp(exp);
//					    		  wcp.setExp(wcp.getExp() + amount);
//					    		  plugin.wcm.updatePlayerMap(p.getName(), wcp);
//					    		  s(p, "You stored " + ChatColor.GOLD + amount + ChatColor.LIGHT_PURPLE + " exp");
//		    					  }else{
//		        					  s(p, "You don't have enough experience");
//		        				  }
//		    					  }else{
//		    					  s(p, "You don't have enough experience");
//		    				  }
//		    				}else{
//							  s(p, "The number must be higher than 0");
//		    			  }
//		    		  }else{
//						  s(p, "That is not a number!");
//		    		  }
//	    		  }else{
//	    			  s(p, "nope.jpg");
//	    		  }
//	    	  }	
//    	  }
    	  if(args[1].equalsIgnoreCase("send")){
    		  if(args[2] != null){
    			  if(args[3] != null){
    				  if(isInteger(args[3])){
    					  int amount = Integer.parseInt(args[3]);
    					  if(amount > 0){
    						  if(Bukkit.getServer().getPlayer(args[2]) !=null && plugin.wcm.getWCPlayer(args[2]) != null){
    							  WCPlayer wcPlayer = plugin.wcm.getWCPlayer(args[2]);
				    			  Player player = Bukkit.getServer().getPlayer(args[2]);
				    			  if(wcp.getExp() >= amount){
					    			  wcp.setExp(wcp.getExp() - amount);
					    			  wcPlayer.setExp(wcPlayer.getExp() + amount);
					    			  plugin.wcm.updatePlayerMap(p.getName(), wcp);
					    			  plugin.wcm.updatePlayerMap(player.getName(), wcPlayer);

					    			  s(p, "You sent " + ChatColor.GOLD + amount + ChatColor.LIGHT_PURPLE + " exp to " + ChatColor.GOLD + player.getName());
					    			  s(player, "You received " + ChatColor.GOLD + amount + ChatColor.LIGHT_PURPLE + " exp from " + ChatColor.GOLD + p.getName());
				    			  }else{
				    				  s(p, "Did you really think you have THAT much exp?!");
				    			  }
			    			  }else{
							  s(p, "Can't find player " + args[2]);
    						  }
    					  }else{
    						  s(p, "The number must be higher than 0");
    					  }
    				  }else{
    					  s(p, "That is not a number!");
    				  }
    			  }else{
    				  s(p, "Correct usage: /wc exp send <player> <amount>");
    			  }
    		  }else{
				  s(p, "Correct usage: /wc exp send <player> <amount>");
    		  }
    	  }
       break;  
       
       case "paragons": case "paragon":

    	  p.sendMessage(new String[]{
    		    AS("&5| &dParagon Information Complex"),
    			AS("&5| &f--- ___ --- ___ --- ___ ---"),
    			AS("&5| &bCommands&f:"),
    			AS("&5| &a/wc tp <player> &f// &aUse a TP token"),
    			AS("&5| &a/wc home &f// &aTP to your special home!"),
    			AS("&5| &a/wc back &f// &aUse a Back token!"),
    			AS("&5| &bSee&a ohsototes.com/?p=paragon &bfor full info on how to get and use them!")
    	  });

       break;
    		   
       case "rewards": case "purchases":
    	   
    	   List <String> purchases = plugin.datacore.getStringList("Users." + p.getName() + ".Purchases");
    	   s(p, "Paragon Purchases:");
    	   	for (String lampPost : purchases){
    	   		p.sendMessage(AS("&5| &d" + lampPost));
    	   	}
    	   	
    	break;
    	
       case "tp":
    	   
    	   if (args.length != 2){
    		   s(p, "Correct usage: /wc tp <player>");
    		   break;
    	   }
    	   
    	   int teleports = wcp.getParagonTps();
    	   
    	   if (teleports < 1){
    		   s(p, "You don't have enough TP tokens!");
    		   break;
    	   }

    	   wcp.setParagonTps(wcp.getParagonTps() - 1);
    	   
    	   if (!p.isOp()){
    		   p.setOp(true);
    		   wcp.setWCOP(true);
    	   }
    	   
    	   p.performCommand("tp " + args[1]);
    	   
    	   if (wcp.isWCOp()){
    		   p.setOp(false);
    		   wcp.setWCOP(false);
    	   }
    	   
      break;
      
      case "market":

    	    Boolean market = wcp.getParagonMarket();
    	    
    	    if (market == false){
    	    	s(p, "You don't have a market warp!");
    	    	break;
    	    }
    	    	
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp markkit " + p.getName());
			break;
			
       case "back":
    	   
    	   int backs = wcp.getParagonBacks();
    	   
    	   if (backs < 1){
    		   s(p, "You don't have enough Back tokens!");
    		   break;
    	   }
    	   
    	   wcp.setParagonBacks(backs - 1);
    	   updatePlayer(wcp, p.getName());
    	   if (!p.isOp()){
    		   p.setOp(true);
    		   p.performCommand("back");
    		   p.setOp(false);
    	   } else {
    		   p.performCommand("back");
    	   }
    	   
	   break;
			    	   	
       case "home":
    	   
    	   if (wcp.getParagonSpecialHomeSet()){
    		   p.teleport(wcp.getParagonSpecialHome());
    		   s(p, "Teleported to your special home!");
    	   }
    	   
    	break;
    		
        case "stafftp":
        	
        	if (p.hasPermission("wa.staff") == false){
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
        	
        	if (Bukkit.getPlayer(args[1]).getName().equals(p.getName())){
        		s(p, "You can't check yourself.");
        		break;
        	}
        	
        		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tp " + p.getName() + " " + args[1]);
        		Bukkit.broadcastMessage(AS(WC + (p).getDisplayName() + " &chas used a grief-check teleport for " + Bukkit.getPlayer(args[1]).getDisplayName()));
        		break;
        
        case "addobelisk":
        	
        	if (p.hasPermission("wa.staff")){
        		
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
        			
        		plugin.datacore.set("Users." + p.getName() + ".ObeliskPlaceMode", true);
        		names.add(args[1]);
        		plugin.config.set("Obelisks.Names", names);
        		plugin.datacore.set("Obelisks.Latest", args[1]);
        		plugin.datacore.set("Obelisks.LatestType", args[2]);
        		p.sendMessage(AS(WC + "Place a GLOWSTONE into the spot where the landing location will be set."));
        		return;
        	}
      
        break;
        
        case "remobelisk":
        	
        	if (p.hasPermission("wa.staff")){
        		
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
        		return;
        	}
      
        break;
        
        case "placeholders":
        	
        	s(p, "%t = town, %c = coords, %p = paragons");
        	
        break;
        	
      	case "reload":
      		
      		if (p.hasPermission("wa.staff")){
      		plugin.loadYamls();
      		plugin.saveYamls();
      		s(p, "Reloaded WC config.");
      		break;
      		} else {
      			s(p, "NO NO, YOU NO HAVE PERMISSIONS.");
      			break;
      		}
      	case "save":
      		
      	if (p.hasPermission("wa.staff")){
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
      			p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
      		}
      		
      	break;
      	
      	case "elevatorreset":
      		
      		if (p.hasPermission("wa.staff")){
      			plugin.wcm.getWCSystem("system").setElevatorActive(false);
      			s(p, "Reset");
      		}
      		
      	break;
      	
      	case "session":

			Bukkit.getServer().dispatchCommand(p, "wcs");
			
		break;

		case "rainoff":

			if (!(p.hasPermission("wa.guardian"))){

				s(p, "You are not the rank Guardian! Sorry, I tried my hardest.'");

				return;

			}

			final int rainoffSeconds = 10800;
			final long timeLeftRO;

			if (rainoffCooldown.containsKey(p.getName())){

				timeLeftRO = this.getCooldown(rainoffCooldown, p.getName(), rainoffSeconds);

				if (timeLeftRO > 0){

					if (timeLeftRO == 1){

						p.sendMessage(AS(WC + "Wow! You have the actual nerve to try the command when there is still 1 second left on the cooldown. Amazing.!"));

						return;

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
					p.sendMessage(AS(WC + "Wow. You really can tell time. Except for the fact that there is still " + sb.toString() + " seconds left on the cooldown."));
					return;
				}

			}

			World currentWorld = p.getWorld();

			if (currentWorld.hasStorm() == false){
				p.sendMessage(AS(WC + "Look, I know you're not a meteorologist, but does it &llook &dlike it's raining?"));
				return;
			}

			currentWorld.setWeatherDuration(1);

			for (Player ep : Bukkit.getOnlinePlayers()){

				if (ep == p){
					p.sendMessage(AS(WC + "You have cleared the heavens!"));
				} else {
					ep.sendMessage(AS(WC + p.getDisplayName() + " &dhas cleared the heavens!"));
				}
			}

			resetCooldown(rainoffCooldown, p.getName());
			
		break;
			
		case "dragonspawn":
			
			int dragonSpawnSecs = 14400;
			long timeLeftDR;
			
			if (dragonCooldown.containsKey("global")){
				timeLeftDR = this.getCooldown(dragonCooldown, "global", dragonSpawnSecs);
				if (timeLeftDR > 0){
					if (timeLeftDR == 1){
						p.sendMessage(AS(WC + "Wow! You have the actual nerve to try the command when there is still 1 second left on the cooldown. Amazing.!"));
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
						p.sendMessage(AS(WC + "Wow. You really can tell time. Except for the fact that there is still " + sb.toString() + " left on the cooldown."));
					}
				}
			} else {
				
				final World theEnd = Bukkit.getWorld("world_the_end");
				
				Location loc = p.getLocation();
				Location end = new Location(Bukkit.getWorld("world_the_end"), -8, 66, -8);
				
				p.teleport(end);
				theEnd.spawnEntity(end, EntityType.ENDER_DRAGON);
				p.teleport(loc);
				
				Bukkit.broadcastMessage(AS(WC + p.getDisplayName() + " &dhas spawned an enderdragon in the end!"));
				Bukkit.broadcastMessage(AS(WC + "&6&oAnother one will be ready to spawn in 4 hours."));
				
				resetCooldown(dragonCooldown, "global");
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

			p.sendMessage(new String[]{
					
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
			p.sendMessage(new String[]{
					
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
			p.sendMessage(new String[]{
					
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
			p.sendMessage(new String[]{
					
					AS("&7&othird place @ " + expAmount),
					AS("&b&o" + place)
					
			});

		break;
			
		case "superride":
			
			if (!(p.hasPermission("wa.staff"))){			
				p.sendMessage(AS(WC + "Does it look like you have permission to use that? I didn't think so either."));	
				break;		
			}
			
			if (plugin.datacore.getBoolean("Users." + p.getName() + ".ridePanel")){
				s(p, "You already have a ride linked!");
				break;
			}
			
			if (plugin.playerRide.get(p.getName()) == null){
				plugin.playerRide.put(p.getName(), new ArrayList<Player>());
			}
			
			if (args.length >= 2){
				if (Bukkit.getPlayer(args[1]) != null){
					plugin.playerRide.get(p.getName()).add(Bukkit.getPlayer(args[1]));
					s(p, "Added " + Bukkit.getPlayer(args[1]).getName());
				}
			}
			
			if (args.length >= 3){
				if (Bukkit.getPlayer(args[2]) != null){
					plugin.playerRide.get(p.getName()).add(Bukkit.getPlayer(args[2]));
					s(p, "Added " + Bukkit.getPlayer(args[2]).getName());
				}
			}
			
			if (args.length >= 4){
				if (Bukkit.getPlayer(args[3]) != null){
					plugin.playerRide.get(p.getName()).add(Bukkit.getPlayer(args[3]));
					s(p, "Added " + Bukkit.getPlayer(args[3]).getName());
				}
			}
			
			if (args.length >= 5){
				if (Bukkit.getPlayer(args[4]) != null){
					plugin.playerRide.get(p.getName()).add(Bukkit.getPlayer(args[4]));
					s(p, "Added " + Bukkit.getPlayer(args[4]).getName());
				}
			}
			
			if (args.length >= 6){
				if (Bukkit.getPlayer(args[5]) != null){
					plugin.playerRide.get(p.getName()).add(Bukkit.getPlayer(args[5]));
					s(p, "Added " + Bukkit.getPlayer(args[5]).getName());
				}
			}
			
			plugin.datacore.set("Users." + p.getName() + ".ridePanel", true);
			s(p, "Right click blocks to add a link, with the block you want to sit on FIRST. Then, type /wc superride2 to complete the link.");
			
		break;
		
		case "superride2":
			
			if (!(p.hasPermission("wa.staff"))){			
				p.sendMessage(AS(WC + "Does it look like you have permission to use that? I didn't think so either."));	
				break;		
			}
			
			if (!plugin.datacore.getBoolean("Users." + p.getName() + ".ridePanel")){
				s(p, "You need to run /wc superride first!");
				break;
			}
			
			for (Location l : plugin.links.get(p.getName())){
				Entity ee = l.getWorld().spawnFallingBlock(new Location(l.getWorld(), l.getX(), l.getY() + 10, l.getZ()), l.getBlock().getType(), (byte) 0);
				ee.setVelocity(new Vector(0, 0, 0));
				l.getBlock().setType(Material.AIR);
				
				if (plugin.links2.get(p.getName()) == null){
					plugin.links2.put(p.getName(), new ArrayList<Entity>());
				}
				
				plugin.links2.get(p.getName()).add(ee);
			}
			
			plugin.links2.get(p.getName()).get(0).setPassenger(p);

			int xXx = 0;
			
			if (plugin.playerRide.get(p.getName()) != null){
				for (Player entt : plugin.playerRide.get(p.getName())){		
						xXx++;
						plugin.links2.get(p.getName()).get(xXx).setPassenger(entt);
						s(p, "Added " + entt.getName());
				}
			}
			
			plugin.datacore.set("Users." + p.getName() + ".ridePanel", false);
			plugin.datacore.set("Users." + p.getName() + ".ridePanel2", true);
			s(p, "Done.");
			
		break;
		
		case "controlcarts":
			
			if (!(p.hasPermission("wa.staff"))){
				break;
			}
			
			if (plugin.datacore.getBoolean("Users." + p.getName() + ".controlCarts")){
				
				plugin.datacore.set("Users." + p.getName() + ".controlCarts", false);
				plugin.datacore.set("cartSpeed", false);
				s(p, "Off!");
			} else {
				
				plugin.datacore.set("Users." + p.getName() + ".controlCarts", true);
				plugin.datacore.set("cartSpeed", true);
				s(p, "On!");
			}
			
		break;
			
		case "ride":
			
			if (!(p.hasPermission("wa.staff"))){			
				p.sendMessage(AS(WC + "Does it look like you have permission to use that? I didn't think so either."));	
				break;		
			}
			
			plugin.datacore.set("Users." + p.getName() + ".commandUsed", true);
			p.sendMessage(AS(WC + "Right click on a mob to begin the madness! (づ｡◕‿‿◕｡)づ"));
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
			
			break;
      }
      
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
  	    	  	
  	        	try {
			
						plugin.fw.playFirework(p.getWorld(), l,
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
	 
	 public Boolean checkInv(Player p, int cost){
		 
		 int amount = 0;
		 int costPaid = 0;
		 int amt = 0;
		 
		 for (ItemStack i : p.getInventory()){
			 if (i != null && i.getType() != Material.AIR && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("TOKEN")){
				 amount = i.getAmount() + amount;
			 }
		 }
		 
		 if (amount >= cost){
			 for (ItemStack i : p.getInventory()){
				 if (i != null){
					 amt = i.getAmount();
				 }
				 if (i != null && i.getType() != Material.AIR && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("TOKEN")){
					 for (int x = 0; x <= amt; x++){
						 if (cost > costPaid){
							 i.setAmount(i.getAmount() - 1);
							 costPaid++;
						 }
					 }
				 }
			 }
			 return true;
		 }
		 
		 return false;
	 }
	 
	@SuppressWarnings("deprecation")
	public void returnItem(Player p, int amount){
		 p.getInventory().addItem(plugin.invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 10, 11, Material.INK_SACK, amount));
		 p.updateInventory();
	 }

} 
