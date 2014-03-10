package com.github.lyokofirelyte.WC.Util;

public class LagUtils implements Runnable {
	
	private static int TICK_COUNT = 0;
	private static long[] TICKS = new long[600];
	
	public static double getTps(){
		
		return getTps(100);
		
	}
	
	public static double getRoundedTps(){
		
		return Math.round(getTps());
		
	}
	
	public static double getTps(int ticks){
		
		if (TICK_COUNT < ticks){
			
			return 20.0;
			
		} else {
			
			int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
			long elapsed = System.currentTimeMillis() - TICKS[target];
			
			return ticks / (elapsed / 1000.0);
			
		}
		
	}
	
	public static int getLagPercent(){
		
		return (int) Math.round((1.0 - getTps() / 20.0) * 100.0);
		
	}
	
	public static long getElapsed(int id){
		
		long time = TICKS[(id % TICKS.length)];
		
		return System.currentTimeMillis() - time;
		
	}
	
	public void run(){
		
		TICKS[(TICK_COUNT % TICKS.length)] = System.currentTimeMillis();
		TICK_COUNT++;
		
	}
	
}
