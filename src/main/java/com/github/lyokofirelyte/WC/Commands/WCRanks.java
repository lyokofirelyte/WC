package com.github.lyokofirelyte.WC.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WC.Util.WCVault;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.ScoreboardUpdateEvent;
import com.github.lyokofirelyte.WC.WCMain;

import static com.github.lyokofirelyte.WC.WCMain.s;
import static com.github.lyokofirelyte.WC.WCMain.s2;

public class WCRanks{

	WCMain pl;
	public WCRanks(WCMain instance){
	pl = instance;
	}
	
	WCPlayer wcp;
	
	@WCCommand(aliases = {"rankup"}, help = "Upgrade your rank!", max = 0)
	public void onRankup(Player sender, String[] args){
			
			Player p = ((Player)sender);
			wcp = pl.wcm.getWCPlayer(p.getName());
			
			switch (WCVault.chat.getPlayerPrefix(p)){
			
				default: case "&f": wcp.setRank("Guest"); break;
				case "": wcp.setRank("Member"); break;
				case "&0S": wcp.setRank("Serf"); break;
				case "&1D": wcp.setRank("Dweller"); break;
				case "&9S": wcp.setRank("Settler"); break;
				case "&3V": wcp.setRank("Villager"); break;
				case "&bT": wcp.setRank("Townsman"); break;
				case "&2C": wcp.setRank("Citizen"); break;
				case "&aM": wcp.setRank("Metropolitan"); break;
				case "&eS": wcp.setRank("Shirian"); break;
				case "&6D": wcp.setRank("Districtman"); break;
				case "&1S": wcp.setRank("Statesman"); break;
				case "&4R": wcp.setRank("Regional"); break;
				case "&cN": wcp.setRank("National"); break;
				case "&dC": wcp.setRank("Continental"); break;
				case "&eG": wcp.setRank("Guardian"); break;
				case "&5E": wcp.setRank("Emperor"); break;
				case "&3D": wcp.setRank("Divine"); break;
				case "&aE": wcp.setRank("Eternal"); break;
				case "&4-I-": wcp.setRank("Immortal"); break;
				case "&a>C<": wcp.setRank("Celestial"); break;
				case "&3>-E-<": wcp.setRank("Elysian"); break;
			}
			
			pl.wcm.updatePlayerMap(p.getName(), wcp);		
		
			List <String> ranks = pl.datacore.getStringList("Ranks");
			int y = 0;
			
			if (wcp.getRank().equals("Immortal")){
				if (wcp.getBalance() < 600000 || wcp.getExp() < 825 || wcp.getPatrolLevel() < 5 || p.getLocation().getY() < 200 || p.getItemInHand() == null || !p.getItemInHand().getType().equals(Material.GOLDEN_CARROT) || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName() || !p.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("rainbow dash")){
					s(p, "You are missing one or more of the following requirements:");
					s2(p, "&d| Patrol Level 5");
					s2(p, "&d| Y coord higher than 199");
					s2(p, "&d| Hand holding golden carrot renamed to 'rainbow dash'");
					s2(p, "&d| 825 exp in /wc exp");
					s2(p, "&d| 600,000 shinies");
					return;
				}
				p.setItemInHand(new ItemStack(Material.AIR, 1));
				wcp.setExp(wcp.getExp() - 825);
				pl.wcm.updatePlayerMap(p.getName(), wcp);
			}
			
			if (wcp.getRank().equals("Celestial")){
				if (wcp.getBalance() < 1000000 || wcp.getExp() < 2475 || wcp.getPatrolLevel() < 15 || p.getLocation().getY() < 250 || p.getItemInHand() == null || !p.getItemInHand().getType().equals(Material.COBBLESTONE) || !p.getItemInHand().hasItemMeta() || !p.getItemInHand().getItemMeta().hasDisplayName() || !p.getItemInHand().getItemMeta().getDisplayName().toLowerCase().contains("supercobble")){
					s(p, "You are missing one or more of the following requirements:");
					s2(p, "&d| Patrol Level 15");
					s2(p, "&d| Y coord higher than 250");
					s2(p, "&d| Hand holding cobblestone renamed to 'supercobble'");
					s2(p, "&d| 2475 exp in /wc exp");
					s2(p, "&d| 1,000,000 shinies");
					return;
				}
				p.setItemInHand(new ItemStack(Material.AIR, 1));
				wcp.setExp(wcp.getExp() - 2475);
				pl.wcm.updatePlayerMap(p.getName(), wcp);
			}
			
			for (String r : ranks){
						
					if (y > ranks.size() - 1){
						WCMain.s(p, "You are already the highest rank!");
						return;
					}
						
					if (wcp.getRank().toLowerCase().equals(r.substring(2).toLowerCase())){
						checkCash(p, ranks.get(y+1).substring(2), wcp.getRank(), ranks.get(y+1));
						return;
					}
					y++;
			} 
	
		return;
	}

	private void checkCash(Player p, String newGroup, String oldGroup, String newGroupFancy) {
	
		wcp = pl.wcm.getWCPlayer(p.getName());
		
		if (newGroup == null){
			WCMain.s(p, "You are already the highest rank!");
			return;
		}
		
		int cost = pl.datacore.getInt("RankCosts." + newGroup);
			if (wcp.getBalance() < cost){
				WCMain.s(p, "You need &6" + cost + " &dshinies for that rank.");
				return;
			}
			
		wcp.setBalance(wcp.getBalance() - cost);
		wcp.setRank(newGroup);
		pl.wcm.updatePlayerMap(p.getName(), wcp);
		WCVault.perms.playerAddGroup(p, newGroup);
		WCVault.perms.playerRemoveGroup(p, oldGroup);
		Bukkit.broadcastMessage(Utils.AS(WCMail.WC + p.getDisplayName() + " &dhas been promoted to " + newGroupFancy + "&d."));
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		Bukkit.getServer().getPluginManager().callEvent(new ScoreboardUpdateEvent(p));
		fireworks(p);
	}
	
	private void fireworks(final Player p){
    	List<Location> circleblocks = Utils.circle(p.getLocation(), 5, 1, true, false, 1);
        long delay =  0L;
        	for (final Location l : circleblocks){
        		delay = delay + 2L;
        		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.pl, new Runnable() {
        	      public void run() {
        	        	try {
							pl.fw.playFirework(p.getWorld(), l,
							FireworkEffect.builder().with(Type.BURST).withColor(Utils.getRandomColor()).build());
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
