package com.github.lyokofirelyte.WC.Extras;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.WCMain;

public class TraceFW implements CommandExecutor{
	
	WCMain pl;
	public TraceFW(WCMain instance){
	pl = instance;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
	    if (cmd.getName().equals("bday")){
	  	  
		  	  if (args.length == 1 && args[0].equals("clr") && sender.hasPermission("wa.admin")){
		  		  bday(Bukkit.getWorld("world2"), true);
		  		  return true;
		  	  }
		  	  
		  	  if (sender.hasPermission("wa.admin")){
		  		  bday(Bukkit.getWorld("world2"), false);
		  	  }
	    }
	
		return true;
	}
	
	public void bday(final World w, Boolean clr) {
		
		List<String> happy = pl.datacore.getStringList("TraceHAPPY");
		List<String> birthday = pl.datacore.getStringList("TraceBDAY");
		List<String> outline = pl.datacore.getStringList("TraceOUTLINE");
		List<String> TT = pl.datacore.getStringList("TraceT");
		List<String> RR = pl.datacore.getStringList("TraceR");
		List<String> AA = pl.datacore.getStringList("TraceA");
		List<String> CC = pl.datacore.getStringList("TraceC");
		List<String> EE = pl.datacore.getStringList("TraceE");
		
		List<Location> happyLIST = new ArrayList<Location>();
		List<Location> birthdayLIST = new ArrayList<Location>();
		List<Location> outlineLIST = new ArrayList<Location>();
		List<Location> TTLIST = new ArrayList<Location>();
		List<Location> RRLIST = new ArrayList<Location>();
		List<Location> AALIST = new ArrayList<Location>();
		List<Location> CCLIST = new ArrayList<Location>();
		List<Location> EELIST = new ArrayList<Location>();
		
		for (String now : happy){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			happyLIST.add(l);
		}
		
		for (String now : birthday){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			birthdayLIST.add(l);
		}
		
		for (String now : outline){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			outlineLIST.add(l);
		}

		for (String now : TT){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			TTLIST.add(l);
		}
		
		for (String now : RR){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			RRLIST.add(l);
		}
		
		for (String now : AA){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			AALIST.add(l);
		}
		
		for (String now : CC){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			CCLIST.add(l);
		}
		
		for (String now : EE){
			String[] s = now.split(",");
			double x = Double.parseDouble(s[0]);
			double y = Double.parseDouble(s[1]) + 1;
			double z = Double.parseDouble(s[2]);
			Location l = new Location(w, x, y, z);
			EELIST.add(l);
		}
		
		if (clr){
			
			for (Location l : happyLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : outlineLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : birthdayLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : TTLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : RRLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : AALIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : CCLIST){
				l.getBlock().setType(Material.AIR);
			}
			for (Location l : EELIST){
				l.getBlock().setType(Material.AIR);
			}
			    List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
			 	for (String taskC : tasks){
			 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
			 	}
	  			pl.datacore.set("PARTYTASKSLIST", null);
	  			pl.datacore.set("PartyDelayTwo", 0);
	  			pl.datacore.set("PartyX", 0);
	  			pl.datacore.set("PartyY", 0);
	  			pl.datacore.set("Overwatch", null);
	  			pl.saveYamls();
	  			return;
		}
		
		construct(w ,happyLIST, outlineLIST, birthdayLIST, TTLIST, RRLIST, AALIST, CCLIST, EELIST);
	}
	
	public void construct(final World w,
			final List<Location> happyLIST, final List<Location> outlineLIST, final List<Location> birthdayLIST, final List<Location> TTLIST, final List<Location> RRLIST,
			final List<Location> AALIST, final List<Location> CCLIST, final List<Location> EELIST){
		
	long DELAY = 0L;
	int y = 0;
	int z = 0;
	int a = 0;
	int b = 0;
	int c = 0;
	int e = 0;
	int d = 0;
	int f = 0;
	
	for (final Location l : happyLIST){
		
		 DELAY = DELAY + 2L;	 
		 y++;
		 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
			 
  		   public void run() {
  			   
	  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
						
						public void run(){

				  			   try {
				  				   pl.fw.playFirework(w, l,
				  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build());
				  			   } catch (IllegalArgumentException e) {
				  				   e.printStackTrace();
				  			   } catch (Exception e) {
				  				   e.printStackTrace();
					    			   } 
						}
					}, 5, 20L);
	  			
	  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
	  			tasks.add(happyTask + "");
	  			pl.datacore.set("PARTYTASKSLIST", tasks);
  			 
  			   try {
  				   pl.fw.playFirework(w, l,
  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
  			   } catch (IllegalArgumentException e) {
  				   e.printStackTrace();
  			   } catch (Exception e) {
  				   e.printStackTrace();
	    			   }  
  			   
				   l.getBlock().setType(Material.REDSTONE_LAMP_ON);
  		   }
		 }
  		   , DELAY);
		   }

			DELAY = DELAY + 2L;

				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					 
			  		   public void run() {
			  			   
			  			 List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
			  			 	for (String taskC : tasks){
			  			 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
			  			 	}
				  			pl.datacore.set("PARTYTASKSLIST", null);
				  			pl.saveYamls();
			  		   	}
				 }
			     , DELAY);
				 
				 
			
			
	while (y >= happyLIST.size()){
		
			y = 0;
			
			for (final Location l : birthdayLIST){
				
			     z++;
				 DELAY = DELAY + 2L;
				 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
					 
		  		   public void run() {
		  			   
			  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
								
								public void run(){
		
						  			   try {
						  				   pl.fw.playFirework(w, l,
						  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build());
						  			   } catch (IllegalArgumentException e) {
						  				   e.printStackTrace();
						  			   } catch (Exception e) {
						  				   e.printStackTrace();
							    			   } 
								}
							}, 5L, 20L);
			  			
			  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
			  			tasks.add(happyTask + "");
			  			pl.datacore.set("PARTYTASKSLIST", tasks);

		  			   try {
		  				   pl.fw.playFirework(w, l,
		  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
		  			   } catch (IllegalArgumentException e) {
		  				   e.printStackTrace();
		  			   } catch (Exception e) {
		  				   e.printStackTrace();
			    			   }  
		  			   
						   l.getBlock().setType(Material.REDSTONE_LAMP_ON);
		  		   }
				 }
		  		   , DELAY);
				   }

					DELAY = DELAY + 5L;

						 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
							 
					  		   public void run() {
					  			   
					  			 List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
					  			 	for (String taskC : tasks){
					  			 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
					  			 	}
						  			pl.datacore.set("PARTYTASKSLIST", null);
						  			pl.saveYamls();
					  		   	}
						 }
					     , DELAY);
					}
	
	
	
	
	while (z >= birthdayLIST.size()){
		
		z = 0;
		
		for (final Location l : outlineLIST){
			
			 DELAY = DELAY + 2L;
			 a++;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.ORANGE).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);
		  			
	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.SNOW_BLOCK);
	  		   }
			 }
	  		   , DELAY);
			   }

				DELAY = DELAY + 5L;

					 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
						 
				  		   public void run() {
				  			   
				  			 List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
				  			 	for (String taskC : tasks){
				  			 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
				  			 	}
					  			pl.datacore.set("PARTYTASKSLIST", null);
					  			pl.saveYamls();
				  		   	}
					 }
				     , DELAY);
				}
	
	
	
	while (a >= outlineLIST.size()){
		
		a = 0;
		
		for (final Location l : TTLIST){
			
			 DELAY = DELAY + 2L;
			 b++;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   @SuppressWarnings("deprecation")
			public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);

	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.WHITE).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.WOOL);
					   l.getBlock().setData((byte) 14);
	  		   }
			 }
	  		   , DELAY);
			   }
			}
	
	
	while (b >= TTLIST.size()){
		
		b = 0;
		
		for (final Location l : RRLIST){
			
			 DELAY = DELAY + 2L;
			 c++;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   @SuppressWarnings("deprecation")
			public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.FUCHSIA).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);
	  			 

	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.PURPLE).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.WOOL);
					   l.getBlock().setData((byte) 9);
	  		   }
			 }
	  		   , DELAY);
			   }
			}
	
	
	while (c >= RRLIST.size()){
		
		c = 0;
		
		for (final Location l : AALIST){
			
			 DELAY = DELAY + 2L;
			 d++;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   @SuppressWarnings("deprecation")
			public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.TEAL).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);
	  			 
	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.AQUA).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.WOOL);
					   l.getBlock().setData((byte) 10);
	  		   }
			 }
	  		   , DELAY);
			   }
			}
	
	while (d >= AALIST.size()){
		
		d = 0;
		
		for (final Location l : CCLIST){
			
			 DELAY = DELAY + 2L;
			 e++;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   @SuppressWarnings("deprecation")
			public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.LIME).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);

	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.GREEN).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.WOOL);
					   l.getBlock().setData((byte) 5);
	  		   }
			 }
	  		   , DELAY);
			   }
			}
	
	while (e >= CCLIST.size()){
		
		e = 0;
		
		for (final Location l : EELIST){
			
			 f++;
			 DELAY = DELAY + 2L;
			 Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				 
	  		   @SuppressWarnings("deprecation")
			public void run() {
	  			   
		  			int happyTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable(){
							
							public void run(){
	
					  			   try {
					  				   pl.fw.playFirework(w, l,
					  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.PURPLE).build());
					  			   } catch (IllegalArgumentException e) {
					  				   e.printStackTrace();
					  			   } catch (Exception e) {
					  				   e.printStackTrace();
						    			   } 
							}
						}, 5L, 20L);
		  			
		  			List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		  			tasks.add(happyTask + "");
		  			pl.datacore.set("PARTYTASKSLIST", tasks);

	  			   try {
	  				   pl.fw.playFirework(w, l,
	  						   FireworkEffect.builder().with(Type.BURST).withColor(Color.BLUE).build());
	  			   } catch (IllegalArgumentException e) {
	  				   e.printStackTrace();
	  			   } catch (Exception e) {
	  				   e.printStackTrace();
		    			   }  
	  			   
					   l.getBlock().setType(Material.WOOL);
					   l.getBlock().setData((byte) 6);
	  		   }
			 }
	  		   , DELAY);
			   }
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			 
	  		   public void run() {
	  			   
	  			 List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
	  			 	for (String taskC : tasks){
	  			 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
	  			 	}
		  			pl.datacore.set("PARTYTASKSLIST", null);
		  			pl.saveYamls();
	  		   	}
		 }
	     , DELAY);
			}
	
	while (f >= EELIST.size()){
		
		f = 0;
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			 public void run() {
		
		 List<String> tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		 	for (String taskC : tasks){
		 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
		 	}
			pl.datacore.set("PARTYTASKSLIST", null);
			pl.saveYamls();
			
		for (Location l : happyLIST){
			
			Random rand = new Random();
			int nextInt = rand.nextInt(7);
			
			List<Color> colors = new ArrayList<Color>();
			colors.add(Color.RED);
			colors.add(Color.WHITE);
			colors.add(Color.BLUE);
			colors.add(Color.ORANGE);
			colors.add(Color.FUCHSIA);
			colors.add(Color.AQUA);
			colors.add(Color.PURPLE);
			colors.add(Color.GREEN);
			colors.add(Color.TEAL);

			try {
				   pl.fw.playFirework(w, l,
						   FireworkEffect.builder().with(Type.STAR).withColor(colors.get(nextInt)).build());
			   } catch (IllegalArgumentException e2) {
			   } catch (Exception e2) {
	    	 } 
			
		}
		
		int cake = 0;
		
		for (Location l : birthdayLIST){
			
			if (cake >= 20){
			} else {
			    ArrayList<String> lore;
			    ItemStack token = new ItemStack(Material.CAKE, 1);
		        ItemMeta name = token.getItemMeta();
		        lore = new ArrayList<String>();
		        name.addEnchant(Enchantment.DURABILITY, 10, true);
		        name.setDisplayName("�a�lBIRTHDAY CAKE");
		        lore.add("�7�oTrace's BDAY 2013");
		        name.setLore(lore);
		        token.setItemMeta((ItemMeta)name);
		        w.dropItemNaturally(l, token);
			}
			
		}
		
		for (Location l : outlineLIST){
			
			Random rand = new Random();
			int nextInt = rand.nextInt(7);
			
			List<Color> colors = new ArrayList<Color>();
			colors.add(Color.RED);
			colors.add(Color.WHITE);
			colors.add(Color.BLUE);
			colors.add(Color.ORANGE);
			colors.add(Color.FUCHSIA);
			colors.add(Color.AQUA);
			colors.add(Color.PURPLE);
			colors.add(Color.GREEN);
			colors.add(Color.TEAL);

			try {
				   pl.fw.playFirework(w, l,
						   FireworkEffect.builder().with(Type.STAR).withColor(colors.get(nextInt)).build());
			   } catch (IllegalArgumentException e2) {
			   } catch (Exception e2) {
	    	 } 
			
		}
		
		tasks = pl.datacore.getStringList("PARTYTASKSLIST");
		 	for (String taskC : tasks){
		 		Bukkit.getServer().getScheduler().cancelTask(Integer.parseInt(taskC));
		 	}
			pl.datacore.set("PARTYTASKSLIST", null);
			pl.saveYamls();
	}
		}, DELAY);
	}
	
	}
}