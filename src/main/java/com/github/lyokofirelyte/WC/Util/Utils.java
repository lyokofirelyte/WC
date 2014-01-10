package com.github.lyokofirelyte.WC.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.lyokofirelyte.WCAPI.WCUtils;

public class Utils extends WCUtils {
	
	public static void changeFinalStatic(Field field, Object value) throws Exception {
		
		Field mod = Field.class.getDeclaredField("modifiers");
		
		field.setAccessible(true);
		mod.setAccessible(true);
		mod.setInt(field, field.getModifiers() & ~ Modifier.FINAL);
		
		field.set(null, value);
		
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
	
}
