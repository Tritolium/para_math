package main;

import leastsquareapproximation.LeastSquareApproximation;

public class Main {

	public static void main(String[] args) {
		double[][] data = { { 11, 0 }, { 12, 2.833213344 }, { 13, 4.820281566 } };
		LeastSquareApproximation approx;
		for (int i = 1; i < data.length; i++) {

			approx = new LeastSquareApproximation(data.length, i);
			approx.setValues(data);

			approx.setup();
			approx.calc().print();
			System.out.println(approx.getError());
			System.out.println();
		}
	}

}
