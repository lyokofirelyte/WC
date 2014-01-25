package com.github.lyokofirelyte.WC;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.Validate;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeHandler {
	
	private ShapedRecipe recipe;
	private ShapelessRecipe shapeless;
	
	public RecipeHandler(ShapedRecipe shape){
		
		this.recipe = shape;
		this.shapeless = null;
		
	}
	
	public RecipeHandler(ShapelessRecipe shape){
		
		this.recipe = null;
		this.shapeless = shape;
		
	}
	
	// shaped
	public RecipeHandler setIngredient(char key, ItemStack item){
		
		Validate.isTrue(getMap().containsKey(key), "Symbol does not appear in the shape: ", key);
		putToMap(key, item, true);
		
		return this;
		
	}
	
	// shapeless
	public RecipeHandler addIngredient(ItemStack item){
		
		Validate.isTrue(getList().size() + 1 <= 9, "Shapeless recipes cannot have more than 9 ingredients");
		putToMap('0', item, false);
		
		return this;
		
	}
	
	public void putToMap(Character c, ItemStack i, boolean shaped){
		
		try {
			
			if (shaped){
				
				Field field = recipe.getClass().getDeclaredField("ingredients");
				field.setAccessible(true);
				
				Map<Character, ItemStack> ingr = (Map) field.get(recipe);
				ingr.put(c, i);
				
				field.set(recipe, ingr);
				
			} else {
				
				Field field = shapeless.getClass().getDeclaredField("ingredients");
				field.setAccessible(true);
				
				List<ItemStack> ingr = (List) field.get(shapeless);
				ingr.add(i);
				
				field.set(shapeless, ingr);
				
			}
			
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
	
	public List<ItemStack> getList(){
		
		try {
			
			Field field = shapeless.getClass().getDeclaredField("ingredients");
			field.setAccessible(true);
			
			List<ItemStack> ingr = (List) field.get(shapeless);
			
			return ingr;
			
		} catch (Exception thisiswhattoudoyouretard){
			
			Bukkit.getLogger().severe("You made an oopsie!");
			thisiswhattoudoyouretard.printStackTrace();
			
			return null;
			
		}
		
	}
	
	public ShapedRecipe getShapedRecipe(){
		
		return recipe;
		
	}
	
	public ShapelessRecipe getShapelessRecipe(){
		
		return shapeless;
		
	}
	
}
