package com.github.lyokofirelyte.WC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;

public class WCLiftFloor {

	String name;
	
	public WCLiftFloor(String name){
		this.name = name;
	}

	public List<String> players = new ArrayList<String>();
	public Map<Location, Material> floors = new HashMap<Location, Material>();
	public float walkSpeed;
	public float flySpeed;
	public int elevatorSpeed;
	public int floorAmount;
	
	public void setFloors(Map<Location, Material> a){
		floors = a;
	}
	
	public void setWalkSpeed(float a){
		walkSpeed = a;
	}
	
	public void setFlySpeed(float a){
		flySpeed = a;
	}
	
	public void setElevatorSpeed(int a){
		elevatorSpeed = a;
	}
	
	public void setFloorAmount(int a){
		floorAmount = a;
	}
}
