package com.github.lyokofirelyte.WC.Util;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCUtils;

public class Utils extends WCUtils {

<<<<<<< HEAD
	public Utils(WCAPI i) {
		super(i);
=======
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
	
	public static String[] AS(String[] DecorativeToasterCozyLaikSoTotesObvi){
		
		for (int i = 0; i < DecorativeToasterCozyLaikSoTotesObvi.length; i++){
			
			DecorativeToasterCozyLaikSoTotesObvi[i] = AS(DecorativeToasterCozyLaikSoTotesObvi[i]);
			
		}
		
		return DecorativeToasterCozyLaikSoTotesObvi;
		
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
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.PIG_ZOMBIE);
		entities.add(EntityType.SKELETON);
		entities.add(EntityType.SILVERFISH);
		entities.add(EntityType.SPIDER);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.ZOMBIE);
		entities.add(EntityType.SKELETON);
		Random rand = new Random();
		int sel = rand.nextInt(entities.size());
		return entities.get(sel);
	}
	
	public static void s(Player p, String[] s){
		
		p.sendMessage(AS(s));
		
	}
	
	public static void s2(Player p, String s){
		
		p.sendMessage(AS(s));
		
	}
	
	public static void b(String s){
		
		Bukkit.broadcastMessage(AS(WC + s));
		  
	}
	
	public static void b(String[] s){
		
		for (String ss : s){
			
			Bukkit.broadcastMessage(AS(ss));
			
		}
		
	}
	
	public static void blankB(String s){
		
		Bukkit.broadcastMessage(s);
		
	}
	
	public static void blankB(String[] s){
		
		for (String ss : s){
			  
			blankB(ss);
			
		}
		
	}
	  
	public static void b2(String s){
		
		Bukkit.broadcastMessage(AS(s));
		
	}
	
	public static void delay(Runnable run, long delay){
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, run, delay);
		
>>>>>>> FETCH_HEAD
	}
}
