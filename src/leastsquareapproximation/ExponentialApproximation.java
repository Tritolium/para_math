package leastsquareapproximation;

import matrix.Matrix;

public class ExponentialApproximation extends Approximation{
	
	private Matrix data;
	
	public ExponentialApproximation(double[][] data) {
		super(data);
	}

	public double[] calc() {
		
		double[] exp = new double[2];
		
		//calculate logarithms
		Matrix ln_data;
		ln_data = new Matrix(data.getData());
		for(int i = 0; i < data.getRows(); i++) {
			ln_data.set(i, 1, Math.log(data.get(i, 1)));
		}
		
		//calculate linear regression line
		LeastSquareApproximation approx = new LeastSquareApproximation(ln_data.getRows(), 1);
		approx.setValues(ln_data.getData());
		approx.setup();
		
		Matrix regressionline = approx.calc();
		
		//calculate exponential function
		exp[0] = Math.exp(regressionline.get(1, 0));
		exp[1] = regressionline.get(0, 0);
		
		return exp;				
	}
	
}
