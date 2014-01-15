package com.github.lyokofirelyte.WC.WCMMO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;

import com.github.lyokofirelyte.WC.WCMain;
import com.github.lyokofirelyte.WCAPI.Manager.SkillType;

public class ExpSources {
	
	public WCMain pl;
	
	public ExpSources(WCMain i){
		pl = i;
	}

	public Map<Material, Integer> expAssignments = new HashMap<Material, Integer>();
	public Map<Material, SkillType> skillAssignments = new HashMap<Material, SkillType>();
	public List<Material> skillTemp = new ArrayList<Material>();
	public List<Location> invalidLocs = new ArrayList<Location>();
	
	public void init(){
		
		if (expAssignments.size() <= 0){
			
			/* MINING & SMELTING */
			expAssignments.put(Material.COBBLESTONE, 1);
			expAssignments.put(Material.ENDER_STONE, 1);
			expAssignments.put(Material.HARD_CLAY, 1);
			expAssignments.put(Material.ICE, 1);
			expAssignments.put(Material.PACKED_ICE, 1);
			expAssignments.put(Material.NETHERRACK, 1);
			expAssignments.put(Material.STONE, 1);
			expAssignments.put(Material.QUARTZ_ORE, 5);
			expAssignments.put(Material.NETHER_BRICK, 5);
			expAssignments.put(Material.MOSSY_COBBLESTONE, 5);
			expAssignments.put(Material.COAL_ORE, 15);
			expAssignments.put(Material.GLOWSTONE, 15);
			expAssignments.put(Material.IRON_ORE, 20);
			expAssignments.put(Material.GLOWING_REDSTONE_ORE, 20);
			expAssignments.put(Material.LAPIS_ORE, 20);
			expAssignments.put(Material.GOLD_ORE, 25);
			expAssignments.put(Material.OBSIDIAN, 30);
			expAssignments.put(Material.DIAMOND_ORE, 35);
			expAssignments.put(Material.EMERALD_ORE, 45);
			
			for (Material m : expAssignments.keySet()){
				skillAssignments.put(m, SkillType.MINING);
				skillTemp.add(m);
			}
			
			/* WOODCUTTING */
			expAssignments.put(Material.LOG, 10);
			expAssignments.put(Material.LOG_2, 10);
			expAssignments.put(Material.LEAVES, 5);
			expAssignments.put(Material.LEAVES_2, 5);
			
			for (Material m : expAssignments.keySet()){
				if (!skillTemp.contains(m)){
					skillAssignments.put(m, SkillType.WOODCUTTING);
					skillTemp.add(m);
				}
			}
			
			/* DIGGING */
			expAssignments.put(Material.CLAY, 1);
			expAssignments.put(Material.SAND, 1);
			expAssignments.put(Material.GRAVEL, 1);
			expAssignments.put(Material.MYCEL, 5);
			expAssignments.put(Material.DIRT, 5);
			expAssignments.put(Material.SNOW_BLOCK, 5);
			expAssignments.put(Material.SNOW, 5);
			expAssignments.put(Material.GRASS, 7);
			
			for (Material m : expAssignments.keySet()){
				if (!skillTemp.contains(m)){
					skillAssignments.put(m, SkillType.DIGGING);
					skillTemp.add(m);
				}
			}
			
			/* FARMING */
			expAssignments.put(Material.LONG_GRASS, 1);
			expAssignments.put(Material.DEAD_BUSH, 1);
			expAssignments.put(Material.VINE, 1);
			expAssignments.put(Material.SEEDS, 1);
			expAssignments.put(Material.CARROT, 5);
			expAssignments.put(Material.CACTUS, 5);
			expAssignments.put(Material.COCOA, 5);
			expAssignments.put(Material.SUGAR_CANE_BLOCK, 5);
			expAssignments.put(Material.CROPS, 5);
			expAssignments.put(Material.WHEAT, 5);
			expAssignments.put(Material.PUMPKIN, 10);
			expAssignments.put(Material.MELON_BLOCK, 10);
			expAssignments.put(Material.NETHER_WARTS, 10);
			expAssignments.put(Material.HUGE_MUSHROOM_1, 10);
			expAssignments.put(Material.HUGE_MUSHROOM_2, 10);
			expAssignments.put(Material.YELLOW_FLOWER, 20);
			expAssignments.put(Material.RED_ROSE, 20);
			
			for (Material m : expAssignments.keySet()){
				if (!skillTemp.contains(m)){
					skillAssignments.put(m, SkillType.FARMING);
					skillTemp.add(m);
				}
			}
			
			/* FISHING */
			expAssignments.put(Material.RAW_FISH, 50);
			
			/* WITCHCRAFT */
			expAssignments.put(Material.POTION, 10);
		}
	}
	
	public int getExp(Material m){
		return expAssignments.get(m);
	}
	
	public Set<Material> getValidMats(){
		return expAssignments.keySet();
	}
	
	public List<Location> getInvalidLocs(){
		return invalidLocs;
	}
	
	public SkillType getSkill(Material m){
		return skillAssignments.get(m);
	}
}