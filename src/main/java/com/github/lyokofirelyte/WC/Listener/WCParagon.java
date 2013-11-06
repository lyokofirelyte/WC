package com.github.lyokofirelyte.WC.Listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.FireworkShenans;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ParagonFindEvent;

public class WCParagon implements Listener {
	
	WCMain plugin;
	public WCParagon(WCMain instance){
	plugin = instance;
	}
	
	WCPlayer wcp;
	List<String> lore;
	ItemStack paragon;
	Location loc;
	ItemMeta paragonMeta;
	Player p;
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.NORMAL)
	public void onParagon(final ParagonFindEvent e){
		
		wcp = plugin.wcm.getWCPlayer(e.getPlayer().getName());
		p = e.getPlayer();
		
		if (!e.getPlayer().getWorld().getName().equals("world")){
			e.setCancelled(true);
			return;
		}

        paragon = new ItemStack(Material.STAINED_CLAY, 1, (short) e.getItemColor());
        paragonMeta = paragon.getItemMeta();
        
        lore = new ArrayList<String>();
        
        lore.add("§7§oI should return this");
        lore.add("§7§oto the shrine near spawn.");
        paragonMeta.setLore(lore);
        
        paragonMeta.setDisplayName(Utils.AS(e.getParagon().toUpperCase() + " PARAGON"));
        paragonMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        paragon.setItemMeta(paragonMeta);
        
        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found a(n) " +  e.getParagon() + " &dparagon from harvesting " + e.getMat().toString().toLowerCase() + " &6" + e.getBlocksMined() + " &dtimes."));
        
        wcp.setBlocksMined(0);
        
        if (p.getInventory().firstEmpty() == -1){
        	
        	loc = p.getLocation();
        	loc.getWorld().dropItemNaturally(loc, paragon);
        	
        	} else {
        		
        	p.getInventory().addItem(paragon);
        	p.updateInventory();
        }
        	
        if (wcp.getFireworks()){ 
        	
        	List<Location> circleblocks = Utils.circle(e.getPlayer(), e.getPlayer().getLocation(), 5, 1, true, false, 1);
	        long delay =  0L;
	        	for (final Location l : circleblocks){
	        		delay = delay + 2L;
	        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
	        	      public void run() {
	        	        	FireworkShenans fplayer = new FireworkShenans();
	        	        	try {
								fplayer.playFirework(e.getPlayer().getWorld(), l,
								FireworkEffect.builder().with(Type.BURST).withColor(e.getfwColor()).build());
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}        	      }
	        	    }
	        	    , delay);
	        	}
        }
	}

}
