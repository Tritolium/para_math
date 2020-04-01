package approximation;

import matrix.Matrix;

public abstract class Approximation {
	
	private Matrix data;
	
	public Approximation(double[][] data) {
		this.data = new Matrix(data);
	}
	
	public double[][] getData(){
		return data.getData();
	}
	
	public void setData(double[][] data) {
		this.data.setData(data);
	}
	
	public abstract double[] calc();
}
