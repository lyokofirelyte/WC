package com.github.lyokofirelyte.WC;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WCLiftFloor {

	String name;
	
	public WCLiftFloor(String n){
		name = n;
	}

	public Map<String, Map<Float, Float>> players = new HashMap<String, Map<Float, Float>>();
	public Map<Location, Material> floors = new HashMap<Location, Material>();
	public Map<Integer, Location> floorSigns = new HashMap<Integer, Location>();
	public Map<Location, ItemStack[]> chests = new HashMap<Location, ItemStack[]>();
	public double elevatorSpeed = 0;
	public int floorAmount = 0;
	public int dest = 0;
	public String start;
	
	public void setChests(Map<Location, ItemStack[]> a){
		chests = a;
	}
	
	public void setFloorSigns(Map<Integer, Location> a){
		floorSigns = a;
	}
	
	public void setStart(String a){
		start = a;
	}
	
	public void setDest(int a){
		dest = a;
	}
	
	public void setFloors(Map<Location, Material> a){
		floors = a;
	}
	
	public void setElevatorSpeed(double a){
		elevatorSpeed = a;
	}
	
	public void setFloorAmount(int a){
		floorAmount = a;
	}
	
	public void addFloor(){
		floorAmount++;
	}
	
	public void setPlayers(Map<String, Map<Float, Float>> a){
		players = a;
	}
	
	public Map<String, Map<Float, Float>> getPlayers(){
		return players;
	}
	
	public Map<Location, ItemStack[]> getChests(){
		return chests;
	}
	
	public Map<Location, Material> getFloors(){
		return floors;
	}
	
	public Map<Integer, Location> getFloorSigns(){
		return floorSigns;
	}
	
	public double getElevatorSpeed(){
		return elevatorSpeed;
	}
	
	public int getFloorAmount(){
		return floorAmount;
	}
	
	public int getDest(){
		return dest;
	}
	
	public String getStart(){
		return start;
	}
}
