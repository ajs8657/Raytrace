package me.alexjs.raytrace.math;

import java.util.Arrays;

/**
 * Immutable 3x3 Matrix
 */
public final class Matrix {

    private final double[] values;

    /**
     * Construct a Matrix with a list of values
     * There must be exactly 9 values given
     *
     * @param values the values representing this Matrix filled from left to right, top to bottom
     */
    public Matrix(double... values) {
        if (values.length != 9) {
            throw new RuntimeException("Invalid matrix size. 3x3 matrices only");
        }
        this.values = values;
    }

    /**
     * Multiply this Matrix by another 3x3 Matrix
     *
     * @param other the other Matrix
     * @return the multiplication result Matrix
     */
    public Matrix multiply(Matrix other) {
        double[] resultValues = new double[9];
        Arrays.fill(resultValues, 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    resultValues[3 * i + j] += values[3 * i + k] * other.values[3 * k + j];
                }
            }
        }
        return new Matrix(resultValues);
    }

    /**
     * Multiply this Matrix by a column Vector
     *
     * @param x the column Vector x value
     * @param y the column Vector y value
     * @param z the column Vector z value
     * @return the multiplication result Vector
     */
    public Vector multiplyVector(double x, double y, double z) {
        Vector col0 = getColumn(0).scale(x);
        Vector col1 = getColumn(1).scale(y);
        Vector col2 = getColumn(2).scale(z);
        return col0.add(col1).add(col2);
    }

    /**
     * Get a row of this Matrix
     *
     * @param rowIndex the row index [0, 2]
     * @return the row of this Matrix as a Vector
     */
    public Vector getRow(int rowIndex) {
        double x = values[3 * rowIndex];
        double y = values[3 * rowIndex + 1];
        double z = values[3 * rowIndex + 2];
        return new Vector(x, y, z);
    }

    /**
     * Get a column of this Matrix
     *
     * @param columnIndex the column index [0, 2]
     * @return the column of this Matrix as a Vector
     */
    public Vector getColumn(int columnIndex) {
        double x = values[columnIndex];
        double y = values[columnIndex + 3];
        double z = values[columnIndex + 6];
        return new Vector(x, y, z);
    }

}
