package com.github.lyokofirelyte.WC.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
	
	public static Sound getRandomNote(){
		
		final List<Sound> sounds = Arrays.asList(Sound.NOTE_BASS, Sound.NOTE_BASS_DRUM, Sound.NOTE_BASS_GUITAR, Sound.NOTE_PIANO, Sound.NOTE_PLING, Sound.NOTE_SNARE_DRUM, Sound.NOTE_STICKS);
		
		Random rand = new Random();
		int nextInt = rand.nextInt(sounds.size()-1);
		return sounds.get(nextInt);
	}
	
	public static String getRandomChatColor(){
		
		final List<String> colors = Arrays.asList("&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f");
		
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}
	
	public static Color getRandomColor(){
		
		final List<Color> colors = Arrays.asList(Color.SILVER, Color.RED, Color.WHITE, Color.BLUE, Color.ORANGE, Color.FUCHSIA, Color.AQUA, Color.PURPLE, Color.GREEN, Color.TEAL, Color.YELLOW);
	
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}
	
	public static DyeColor getRandomDyeColor(){
		
		final List<DyeColor> colors = Arrays.asList(DyeColor.RED, DyeColor.WHITE, DyeColor.BLUE, DyeColor.ORANGE, DyeColor.GREEN, DyeColor.BLACK, DyeColor.PURPLE, DyeColor.SILVER, DyeColor.YELLOW);
	
		Random rand = new Random();
		int nextInt = rand.nextInt(colors.size()-1);
		return colors.get(nextInt);
	}

	public static String getTime() {
	  	Calendar cal = Calendar.getInstance();
	  	cal.getTime();
	  	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	  	return ( sdf.format(cal.getTime()) );
	  }
	
    public static List<Location> circle (Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
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

      public static String createString(String[] args, int x) {
    	  
        StringBuilder sb = new StringBuilder();
        
        	for (int i = x; i < args.length; i++){
        		if ((i != x) && (i != args.length)){
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
 	
	public static void effects(Location ll){
		
		List<Location> circleblocks = Utils.circle(ll, 3, 1, true, false, 0);
		List<Location> circleblocks2 = Utils.circle(ll, 3, 1, true, false, 1);
	
		for (Location l : circleblocks){
			ll.getWorld().playEffect(l, Effect.SMOKE, 0);
			ll.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			ll.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
			
		for (Location l : circleblocks2){
			ll.getWorld().playEffect(l, Effect.SMOKE, 0);
			ll.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			ll.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}
 	
	public static void effects(Player q){
		
		List<Location> circleblocks = Utils.circle(q.getLocation(), 3, 1, true, false, 0);
		List<Location> circleblocks2 = Utils.circle(q.getLocation(), 3, 1, true, false, 1);
	
		for (Location l : circleblocks){
			q.getWorld().playEffect(l, Effect.SMOKE, 0);
			q.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			q.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
			
		for (Location l : circleblocks2){
			q.getWorld().playEffect(l, Effect.SMOKE, 0);
			q.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
			q.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
		}
	}
	
	public static Boolean dispName(ItemStack i, String s){
		if (i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().substring(2).equals(s) && i.getItemMeta().hasLore()){
			return true;
		}
		return false;
	}
	
	public static EntityType getRandomEntity(){
		List<EntityType> entities = new ArrayList<EntityType>();
		entities.add(EntityType.CREEPER);
		entities.add(EntityType.CAVE_SPIDER);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.MAGMA_CUBE);
		entities.add(EntityType.SLIME);
		entities.add(EntityType.PIG_ZOMBIE);
		entities.add(EntityType.SKELETON);
		entities.add(EntityType.CAVE_SPIDER);
		entities.add(EntityType.SILVERFISH);
		entities.add(EntityType.BLAZE);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.GHAST);
		entities.add(EntityType.WITCH);
		Random rand = new Random();
		int sel = rand.nextInt(entities.size());
		return entities.get(sel);
	}
}
