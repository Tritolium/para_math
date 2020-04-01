package approximation;

import matrix.DeterminantIsZeroException;
import matrix.Matrix;

public class LeastSquareApproximation {
	private Matrix a;
	private Matrix x;
	private Matrix y;
	private int counter = 0;
	private int values;
	private int degree;
	private double error;

	public LeastSquareApproximation(int values, int degree) {
		this.values = values;
		this.degree = degree;
		x = new Matrix(1, degree + 1);
		a = new Matrix(degree + 1, values);
		y = new Matrix(1, values);
	}

	public double getError() {
		return error;
	}

	public void setValues(double x_value, double y_value) {
		for (int i = 0; i <= degree; i++) {
			a.set(counter, i, exponential(x_value, degree - i));
		}
		y.set(counter, 0, y_value);
		counter++;
	}

	public void setValues(double[][] data) {
		for (int i = 0; i < data.length; i++) {
			this.setValues(data[i][0], data[i][1]);
		}
	}

	public void setup() {
		if (counter == values) {
			for (int i = 0; i < values; i++) {
				a.set(i, degree, 1);
			}
		}
	}

	public Matrix calc() {
		error = 0;
		try {
			x = a.getTransposedMatrix();
			x = Matrix.times(x, a);
			x = x.getInverseMatrix();
			x = Matrix.times(x, a.getTransposedMatrix());
			x = Matrix.times(x, y);
		} catch (DeterminantIsZeroException e) {
			e.printStackTrace();
		}
		Matrix y_approx = Matrix.times(a, x);
		for (int i = 0; i < values; i++) {
			error += exponential(y_approx.get(i, 0) - y.get(i, 0), 2);
		}

		return x;
	}

	private double exponential(double value, int exponent) {
		double result = 1.0;
		for (int i = 0; i < exponent; i++) {
			result *= value;
		}
		return result;
	}
}
