package com.github.lyokofirelyte.WC.Commands;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WC.Util.Utils;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatExtra;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatHoverEventType;
import com.github.lyokofirelyte.WCAPI.JSON.JSONChatMessage;
import static com.github.lyokofirelyte.WCAPI.WCUtils.AS;

public class WCThis {
	
	WCMain pl;
	
	public WCThis(WCMain i){
		pl = i;
	}

	@WCCommand(aliases = {"this"}, help = "/this", desc = "Link your held item!")
	public void onThis(Player p, String[] args){
		
		JSONChatMessage msg = new JSONChatMessage(Utils.AS("&8>> " + p.getDisplayName() + " "), null, null);
		JSONChatExtra extra = null;
		
		if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasDisplayName()){
			extra = new JSONChatExtra(p.getItemInHand().getItemMeta().getDisplayName(), null, null);
		} else {
			extra = new JSONChatExtra(p.getItemInHand().getType().name().toString().toLowerCase(), null, null);
		}
		
		if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasEnchants()){
			
			StringBuilder sb = new StringBuilder();
			
			for (Enchantment e : p.getItemInHand().getEnchantments().keySet()){
				sb.append("&c" + e.getName() + " " + p.getItemInHand().getEnchantments().get(e) + "\n");
			}
			
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&5" + p.getItemInHand().getType().name() + "\n" + sb.toString().trim() + "\n&6Damage:&f " +
			p.getItemInHand().getDurability() + "\n&6Amount:&f " + p.getItemInHand().getAmount()));
			
		} else {	
			extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, AS("&5" + p.getItemInHand().getType().name() + "\n&6Damage:&f " +
			p.getItemInHand().getDurability() + "\n&6Amount:&f " + p.getItemInHand().getAmount()));
		}

		msg.addExtra(extra);
		
		for (Player pp : Bukkit.getOnlinePlayers()){
			msg.sendToPlayer(pp);
		}
	}
}