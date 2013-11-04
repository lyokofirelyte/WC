package com.github.lyokofirelyte.WCAPI.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.github.lyokofirelyte.WCAPI.WCEvent;

public class PlayerMoneyTransferEvent extends WCEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player sentFrom;
    private Player sendTo;
    private int sentMoney;

    public PlayerMoneyTransferEvent(Player sentFrom, Player sendTo, int sentMoney) {
        this.sentMoney = sentMoney;
        this.sentFrom = sentFrom;
        this.sendTo = sendTo;
    }
 
    public boolean isCancelled() {
        return cancelled;
    }
 
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    public Player getSentFrom(){
    	return sentFrom;
    }
    
    public Player getSendTo(){
    	return sendTo;
    }
    
    public int getSentMoney(){
    	return sentMoney;
    }
    
    public void setSentMoney(int a){
    	sentMoney = a;
    }
    
    public void setSentFrom(Player p){
    	sentFrom = p;
    }
    
    public void setSendTo(Player p){
    	sendTo = p;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
