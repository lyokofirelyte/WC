package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WC.Commands.WCMail;
import com.github.lyokofirelyte.WC.Util.Utils;

public class WCFAQ {

	WCMain plugin;
	
	 public WCFAQ(WCMain instance){
	    this.plugin = instance;
	  }
	  
	public List<String> faqList = new ArrayList<String>();
	 
	 
	public void faq(String s, Player p){
		
		String message[] = s.split(" ");
		String m = message[1];
		faqList = plugin.help.getStringList("FAQ.List");
		int x = 0;
		
			for (String faqCheck : faqList){
				if (faqCheck.toLowerCase().contains(m.toLowerCase())){
					faqOut(p, faqCheck);
					x++;
					return;
				}
			}
			
			if (x == 0){
				p.sendMessage(WCMail.WC + "No entry found! See !faq list.");
				return;
			}
	}

	
	public void faqOut(Player p, String s){
		
		Random rand = new Random();
		int randomNumber = rand.nextInt(12) + 1;

			switch (randomNumber){
			
			case 1:
				p.sendMessage(Utils.AS("&5Twilight Sparkle &fsays: &5" + s));
				break;
			case 2:
				p.sendMessage(Utils.AS("&aR&5a&bi&cn&db&3o&4w &9D&5a&es&6h &fsays: &a" + s));
				break;
			case 3:
				p.sendMessage(Utils.AS("&dPinkie Pie &fsays: &d" + s));
				break;
			case 4:
				p.sendMessage(Utils.AS("&6Apple Jack &fsays: &6" + s));
				break;
			case 5:
				p.sendMessage(Utils.AS("&1Princess Luna &fsays: &1" + s));
				break;
			case 6:
				p.sendMessage(Utils.AS("&dPrincess Celestia &fsays: &d" + s));
				break;
			case 7:
				p.sendMessage(Utils.AS("&bRarity &fsays: &b" + s));
				break;
			case 8:
				p.sendMessage(Utils.AS("&3Spike &fsays: &3" + s));
				break;
			case 9:
				p.sendMessage(Utils.AS("&4Discord &fsays: &4" + s));
				break;
			case 10:
				p.sendMessage(Utils.AS("&cScootaloo &fsays: &c" + s));
				break;
			case 11:
				p.sendMessage(Utils.AS("&2Shining Armor &fsays: &2" + s));
				break;
			case 12:
				p.sendMessage(Utils.AS("&7Trixie &fsays: &7" + s));
				break;		
			default: 
				p.sendMessage(Utils.AS("&aR&5a&bi&cn&db&3o&4w &9D&5a&es&6h &fsays: &a" + s));
				break;
			}
	}
}
