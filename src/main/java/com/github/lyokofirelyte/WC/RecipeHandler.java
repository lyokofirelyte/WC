package com.github.lyokofirelyte.WC;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeHandler {
	
	private ShapedRecipe recipe;
	
	public RecipeHandler(ShapedRecipe shape){
		
		this.recipe = shape;
		
	}
	
	public RecipeHandler setIngredient(char key, ItemStack item){
		
		Validate.isTrue(getMap().containsKey(key), "Symbol does not appear in the shape: ", key);
		putToMap(key, item);
		
		return this;
		
	}
	
	public void putToMap(Character c, ItemStack i){
		
		try {
			
			Field field = recipe.getClass().getDeclaredField("ingredients");
			field.setAccessible(true);
			
			Map<Character, ItemStack> ingr = (Map) field.get(recipe);
			ingr.put(c, i);
			
			field.set(recipe, ingr);
			
		} catch (Exception thisiswhattoudoyouretard){
			
			Bukkit.getLogger().severe("You made an oopsie!");
			thisiswhattoudoyouretard.printStackTrace();
			
		}
		
	}
	
	public Map getMap(){
		
		try {
			
			Field field = recipe.getClass().getDeclaredField("ingredients");
			field.setAccessible(true);
			
			Map<Character, ItemStack> ingr = (Map) field.get(recipe);
			
			return ingr;
			
		} catch (Exception thisiswhattoudoyouretard){
			
			Bukkit.getLogger().severe("You made an oopsie!");
			thisiswhattoudoyouretard.printStackTrace();
			
			return null;
			
		}
		
	}
	
	public ShapedRecipe getRecipe(){
		
		return recipe;
		
	}
	
}
