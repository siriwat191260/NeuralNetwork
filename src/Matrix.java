import java.util.Random;

public class Matrix {
    double[][] data;
    int row, column;

    public Matrix(int row, int column,boolean condition) {
        data = new double[row][column];
        this.row = row;
        this.column = column;
        Random random = new Random();
        Double rand;
        if(condition) {
            for (int j = 0; j < row; j++) {
                for (int i = 0; i < column; i++) {
                    rand = random.nextDouble() * 2 - 1;
                    while (rand == 0) {
                        rand = random.nextDouble() * 2 - 1;
                    }
                    data[j][i] = rand;
                }
            }
        }
    }

}