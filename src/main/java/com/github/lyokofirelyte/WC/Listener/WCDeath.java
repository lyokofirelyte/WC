package com.github.lyokofirelyte.WC.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.WCMobDrops;

public class WCDeath implements Listener{

	WCMain plugin;
	public WCDeath(WCMain instance){
	plugin = instance;
	}
	
	WCPlayer wcp;

	  @EventHandler(priority=EventPriority.NORMAL)
	  public void EDBEE(EntityDamageByEntityEvent e) {

		  if (e.getDamager() instanceof Player){

			  Player p = (Player) e.getDamager();

			  if (p.getItemInHand().hasItemMeta()){
					if (p.getItemInHand().getItemMeta().hasLore() && p.getItemInHand().getItemMeta().hasDisplayName()){
						if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains("HAMDRAX")){
							short dur = p.getItemInHand().getDurability();
							if (!p.getItemInHand().getType().equals(Material.DIAMOND_SWORD)){
								WCMobDrops.swapDrax(Material.DIAMOND_SWORD, p, dur, "Sword");
							}
						}
					}
			  }
		  }

	  }

	@EventHandler (priority = EventPriority.NORMAL)
	public void onKerSmashSplode(EntityDeathEvent e){

		Entity ent = e.getEntity();
		/*EntityDamageEvent ede = ent.getLastDamageCause();
		DamageCause dc = ede.getCause();*/

		if (ent instanceof Player){

			Player p = (Player) ent;
			Location l = p.getLocation();
			wcp = plugin.wcm.getWCPlayer(p.getName());
			wcp.setLastLocation(l);
			int deaths = wcp.getDeathCount();
			deaths++;
			wcp.setDeathCount(deaths);
			plugin.wcm.updatePlayerMap(p.getName(), wcp);
			Bukkit.broadcastMessage(Utils.AS("&6>> " + p.getDisplayName() + " &ehas died at " + Math.round(l.getX()) + "," + Math.round(l.getY()) + "," + Math.round(l.getZ()) + " &6<<"));
		}
	}

	/*public String tPD(Player p, DamageCause dc){

		Random rand = new Random();
		List<String> dML = plugin.config.getStringList("Core.DeathMessages." + dc.toString());
		int dMN = rand.nextInt(dML.size()-1);
		String message = dML.get(dMN);

		int deaths = plugin.datacore.getInt("Users." + p.getName() + ".DeathCount");
		StringBuilder sb = new StringBuilder(message);

		sb.append(" &7(" + deaths + ")");

		message = "&o" + sb.toString();
		message = Utils.AS(message.replace("%p", p.getDisplayName() + "&r&o"));

		return message;

	}

	public String tPD(Player p, DamageCause dc, EntityDamageByEntityEvent ede){

		Random rand = new Random();
		List<String> dML = plugin.config.getStringList("Core.DeathMessages." + dc.toString() + "." + ede.getDamager().getType().toString());

		if (dML == null){

			dML = plugin.config.getStringList("Core.DeathMessages." + dc.toString() + ".DEFAULT");

		}

		int dMN = rand.nextInt(dML.size()-1);
		String message = dML.get(dMN);

		int deaths = plugin.datacore.getInt("Users." + p.getName() + ".DeathCount");
		StringBuilder sb = new StringBuilder(message);

		sb.append(" &7(" + deaths + ")");

		message = "&o" + sb.toString();

		if (ede.getDamager() instanceof Player){

			String pDN = (((Player) ede.getDamager()).getDisplayName());
		    ItemStack weapon = p.getItemInHand();

			if (!weapon.hasItemMeta()){

				String wN = weapon.getType().name();
				message = Utils.AS(message.replace("%p", p.getDisplayName() + "&r&o").replace("%a", "&6" + pDN + "&r&o").replace("%i", "&6" + wN + "&r&o"));

			} else {
				String wN = weapon.getItemMeta().getDisplayName();
				message = Utils.AS(message.replace("%p", p.getDisplayName() + "&r&o").replace("%a", "&6" + pDN + "&r&o").replace("%i", "&6" + wN + "&r&o"));
			}

		} else {

			String aTT = ede.getDamager().getType().name().toLowerCase();
			message = Utils.AS(message.replace("%p", p.getDisplayName() + "&r&o").replace("%a", "&6" + aTT + "&r&o"));

		}

		return message;

	}*/

}