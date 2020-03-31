package coronatracker;

import leastsquareapproximation.LeastSquareApproximation;
import matrix.Matrix;

public class CoronaTracker {
	
	private Matrix cases;
	private Matrix ln_cases;
	
	public CoronaTracker(int[][] cases) {
		this.cases = new Matrix(cases);
	}
	
	public int calcNextWeek() {
		//calculate logarithms
		ln_cases = new Matrix(cases.getData());
		for(int i = 0; i < cases.getRows(); i++) {
			ln_cases.set(i, 1, Math.log(cases.get(i, 1)));
		}
		//calculate linear regression line
		LeastSquareApproximation approx = new LeastSquareApproximation(ln_cases.getRows(), 1);
		approx.setValues(ln_cases.getData());
		approx.setup();
		
		Matrix regressionline = approx.calc();
		//calculate exponential function
		double factor = Math.exp(regressionline.get(1, 0));
		double exponent = (regressionline.get(0, 0));
		//find next data
		double max = -1;
		for(int i = 0; i < cases.getRows(); i++) {
			if(max == -1) {
				max = cases.get(i, 0);
			} else {
				if(cases.get(i, 0) > max) {
					max = cases.get(i, 0);
				}
			}
		}
		//prediction for next set of data
		double prediction = factor * Math.exp(exponent * (max + 1));
		return (int) Math.round(prediction);
	}
}
