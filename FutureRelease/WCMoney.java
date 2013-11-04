package com.github.lyokofirelyte.WC.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.github.lyokofirelyte.WC.WCMain;
import static com.github.lyokofirelyte.WC.WCMain.s;
import com.github.lyokofirelyte.WCAPI.WCPlayer;
import com.github.lyokofirelyte.WCAPI.Events.PlayerMoneyTransferEvent;

public class WCMoney implements Listener {

	WCMain plugin;
	public WCMoney(WCMain instance){
	plugin = instance;
    }
	
	WCPlayer wcp;
	WCPlayer wcp2;
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onMoney(PlayerMoneyTransferEvent e){
		
		if (e.isCancelled()){
			return;
		}
		
		wcp = plugin.wcm.getWCPlayer(e.getSentFrom().getName());
		wcp2 = plugin.wcm.getWCPlayer(e.getSendTo().getName());
		
		int balance = wcp.getBalance();
		
		if (balance < e.getSentMoney()){
			s(e.getSentFrom(), "You don't have enough money!");
			e.setCancelled(true);
			return;
		}
		
		s(e.getSentFrom(), "Sent!");
		s(e.getSendTo(), "You've recieved " + e.getSentMoney() + " &dfrom " + e.getSentFrom().getDisplayName() + "&d!");
		
		wcp.setBalance(wcp.getBalance() - e.getSentMoney());
		wcp2.setBalance(wcp2.getBalance() + e.getSentMoney());
		
		plugin.wcm.updatePlayerMap(e.getSentFrom().getName(), wcp);
		plugin.wcm.updatePlayerMap(e.getSendTo().getName(), wcp2);
	}

} 
