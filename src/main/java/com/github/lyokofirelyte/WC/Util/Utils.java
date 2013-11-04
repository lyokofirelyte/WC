package com.github.lyokofirelyte.WC.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;


import com.github.lyokofirelyte.WC.WCMain;

public class Utils {
	
	WCMain plugin;
	public Utils(WCMain instance){
	    plugin = instance;
	}
	
	public static String WC = "§dWC §5// §d";
	
	public static void bc(String s){
		Bukkit.broadcastMessage(WC + Utils.AS(s));
	}
	
	public static Color getRandomColor(){
		
		final List<Color> colors = Arrays.asList(Color.RED, Color.WHITE, Color.BLUE, Color.ORANGE, Color.FUCHSIA, Color.AQUA, Color.PURPLE, Color.GREEN, Color.TEAL, Color.YELLOW);
	
		Random rand = new Random();
		int nextInt = rand.nextInt(10);
		
		return colors.get(nextInt);
	}
	
    public static List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }
    
    public static boolean isInteger(String s) {
        try {
          Integer.parseInt(s);
        } catch (NumberFormatException e) {
          return false;
        }

        return true;
      }

      public static String createString(String[] args, int x)
      {
        StringBuilder sb = new StringBuilder();
        for (int i = x; i < args.length; i++)
        {
          if ((i != x) && (i != args.length))
          {
            sb.append(" ");
          }
          sb.append(args[i]);
        }
        return sb.toString();
      }
      
 	 public static Entity getTarget(final Player player) {
		 
	        BlockIterator iterator = new BlockIterator(player.getWorld(), player
	                .getLocation().toVector(), player.getEyeLocation()
	                .getDirection(), 0, 100);
	        Entity target = null;
	        while (iterator.hasNext()) {
	            Block item = iterator.next();
	            for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
	                int acc = 2;
	                for (int x = -acc; x < acc; x++)
	                    for (int z = -acc; z < acc; z++)
	                        for (int y = -acc; y < acc; y++)
	                            if (entity.getLocation().getBlock()
	                                    .getRelative(x, y, z).equals(item)) {
	                                return target = entity;
	                            }
	            }
	        }
	        return target;
	    }

	public static String AS(String DecorativeToasterCozy){
		
		String FlutterShysShed = ChatColor.translateAlternateColorCodes('&', DecorativeToasterCozy);
		return FlutterShysShed;
		
	}
	
 	public static boolean hasPerms(String w, String p, String usepermission) {
    return WCVault.perms.playerHas(w, p, usepermission);
	}
 	
 	
}
