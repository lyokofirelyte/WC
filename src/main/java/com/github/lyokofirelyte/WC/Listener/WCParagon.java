package com.github.lyokofirelyte.WC.Listener;

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

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s2;
import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ParagonFindEvent;

public class WCParagon implements Listener {
	
	WCMain pl;
	public WCParagon(WCMain instance){
	pl = instance;
	}
	
	WCPlayer wcp;
	Location loc;
	Player p;
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onParagon(final ParagonFindEvent e){
		
		wcp = pl.wcm.getWCPlayer(e.getPlayer().getName());
		p = e.getPlayer();
		
		if (!e.getPlayer().getWorld().getName().equals("world")){
			e.setCancelled(true);
			return;
		}
		
		if (e.getParagon().toLowerCase().contains("death")){
	        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found a(n) " +  e.getParagon() + " &dparagon from killing mobs."));
		} else {
	        Bukkit.broadcastMessage(Utils.AS(WCMail.WC + e.getPlayer().getDisplayName() + " &dhas found a(n) " +  e.getParagon() + " &dparagon from harvesting " + e.getMat().toString().toLowerCase() + " &6" + e.getBlocksMined() + " &dtimes."));
		}

		ItemStack token = pl.invManager.makeItem("§e§o§lPARAGON TOKEN", "§7§oIt's currency!", true, Enchantment.DURABILITY, 10, 11, Material.INK_SACK, 1);
        

        
        wcp.setBlocksMined(0);
        
        if (p.getInventory().firstEmpty() == -1){
        	p.getWorld().dropItemNaturally(p.getLocation(), token);
            s2(p, "&6&oYour tokens were dropped due to full inventory.");
        } else {		
        	p.getInventory().addItem(token);
            s2(p, "&6&oYour tokens were added to your inventory.");
        }

        pl.wcm.updatePlayerMap(p.getName(), wcp);

        if (wcp.getFireworks()){ 
        	
        	List<Location> circleblocks = Utils.circle(e.getPlayer().getLocation(), 5, 1, true, false, 1);
	        long delay =  0L;
	        	for (final Location l : circleblocks){
	        		delay = delay + 2L;
	        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
	        	      public void run() {
	        	        	try {
								pl.fw.playFirework(e.getPlayer().getWorld(), l,
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
