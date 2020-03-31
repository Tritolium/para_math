package main;

import coronatracker.CoronaTracker;

public class Main {

	public static void main(String[] args) {
		
		int[][] data = {{11,1},{12,17},{13,124}};
		
		CoronaTracker tracker = new CoronaTracker(data);
		
		System.out.println(tracker.calcNextWeek());
	}

}