package matrix;

public class Matrix {
	private int rows;
	private int columns;
	private double[][] data;

	public Matrix(int columns, int rows) {
		this.setColumns(columns);
		this.setRows(rows);
		this.setData(new double[rows][columns]);
	}
	
	public Matrix(int[][] d) {
		this.setColumns(d[0].length);
		this.setRows(d.length);
		double[][] da = new double[d.length][d[0].length];
		for(int i = 0; i < d.length; i++) {
			for(int j = 0; j < d[0].length; j++) {
				da[i][j] = d[i][j];
			}
		}
		this.setData(da);
	}

	public Matrix(double[][] data) {
		this.setColumns(data[0].length);
		this.setRows(data.length);
		this.setData(data);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	/**
	 * Casting the data to a new array, so it does not return the pointer of the original
	 * @return a copy of the data
	 */
	public double[][] getData() {
		double[][] ret = new double[data.length][data[0].length];
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[0].length; j++) {
				ret[i][j] = data[i][j];
			}
		}
		return ret;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public void set(int row, int column, double value) {
		this.data[row][column] = value;
	}

	public double get(int row, int column) {
		return this.data[row][column];
	}

	public Matrix getSubmatrix(int row, int column) {
		Matrix submatrix = new Matrix(this.columns - 1, this.rows - 1);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (i < row && j < column) {
					submatrix.set(i, j, this.get(i, j));
				} else if (i < row && j > column) {
					submatrix.set(i, j - 1, this.get(i, j));
				} else if (i > row && j < column) {
					submatrix.set(i - 1, j, this.get(i, j));
				} else if (i > row && j > column) {
					submatrix.set(i - 1, j - 1, this.get(i, j));
				}
			}
		}
		return submatrix;
	}

	public double getDeterminant() throws NonSquareMatrixException {
		if (this.rows == this.columns) {
			double determinant = 0.0;
			if (this.rows == 1) {
				determinant = this.get(0, 0);
			} else if (this.rows == 2) {
				determinant = (this.get(0, 0) * this.get(1, 1)) - (this.get(0, 1) * this.get(1, 0));
			} else {
				for (int i = 0; i < this.rows; i++) {
					if (i % 2 == 0) {
						determinant += this.get(i, 0) * this.getSubmatrix(i, 0).getDeterminant();
					} else {
						determinant -= this.get(i, 0) * this.getSubmatrix(i, 0).getDeterminant();
					}
				}
			}
			return determinant;
		} else {
			throw new NonSquareMatrixException();
		}
	}

	public Matrix getTransposedMatrix() {
		Matrix transposed = new Matrix(this.getRows(), this.getColumns());

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				transposed.set(j, i, this.get(i, j));
			}
		}

		return transposed;
	}

	public Matrix getAdjunctMatrix() {
		Matrix adjunct = new Matrix(this.getColumns(), this.getRows());
		try {
			for (int i = 0; i < this.columns; i++) {
				for (int j = 0; j < this.rows; j++) {
					if ((i + j) % 2 == 0) {
						adjunct.set(i, j, this.getSubmatrix(i, j).getDeterminant());
					} else {
						adjunct.set(i, j, -1 * this.getSubmatrix(i, j).getDeterminant());
					}
				}
			}
		} catch (NonSquareMatrixException e) {
			e.printStackTrace();
		}

		adjunct = adjunct.getTransposedMatrix();

		return adjunct;
	}

	public Matrix getInverseMatrix() throws DeterminantIsZeroException {
		try {
			if (this.getDeterminant() != 0) {
				Matrix inverse = new Matrix(this.getColumns(), this.getRows());

				inverse = this.getAdjunctMatrix();
				try {
					inverse.times(1.0 / this.getDeterminant());
				} catch (NonSquareMatrixException e) {
					e.printStackTrace();
				}

				return inverse;
			} else {
				throw new DeterminantIsZeroException();
			}
		} catch (NonSquareMatrixException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void times(double factor) {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.set(i, j, this.get(i, j) * factor);
			}
		}
	}

	public void print() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				System.out.print(this.get(i, j) + " ");
			}
			System.out.println();
		}
	}

	public static Matrix times(Matrix a, Matrix b) {
		Matrix product = new Matrix(b.getColumns(), a.getRows());
		double value;

		if (a.getColumns() == b.getRows()) {
			for (int i = 0; i < product.getRows(); i++) {
				for (int k = 0; k < product.getColumns(); k++) {
					value = 0.0;
					for (int j = 0; j < a.getColumns(); j++) {
						value += a.get(i, j) * b.get(j, k);
					}
					product.set(i, k, value);
				}
			}
		}
		return product;
	}
}
