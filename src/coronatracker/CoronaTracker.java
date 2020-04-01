package coronatracker;

import leastsquareapproximation.ExponentialApproximation;

public class CoronaTracker {
	
	private int[][] cases;
	
	public CoronaTracker(int[][] cases) {
		this.cases = cases;
	}
	
	public int calcNextWeek() {
		
		double[][] data = new double[cases.length][cases[0].length];
		for(int i = 0; i < cases.length; i++) {
			for(int j = 0; j < cases[0].length; j++) {
				data[i][j] = cases[i][j];
			}
		}
		
		ExponentialApproximation exponentialFunction = new ExponentialApproximation(data);		
		double[] exponential = exponentialFunction.calc();
		
		//find next data
		int max = -1;
		for(int i = 0; i < cases.length; i++) {
			if(max == -1) {
				max = cases[i][0];
			} else {
				if(cases[i][0] > max) {
					max = cases[i][0];
				}
			}
		}
		//prediction for next set of data
		double factor = exponential[0];
		double exponent = exponential[1];
		double prediction = factor * Math.exp(exponent * (max + 1));
		return (int) Math.round(prediction);
	}
}
