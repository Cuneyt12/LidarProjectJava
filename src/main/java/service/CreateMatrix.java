package service;

import model.GetLidarInfo;

public class CreateMatrix {
    public boolean matrisCreated = false;
    Object[][] matrix = new Object[3][3];


    private void createMatrix(double angle, double distance) {
        if (!matrisCreated) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (i == 1 && j == 1) {
                        matrix[i][j] = "RPL";
                    } else {
                        matrix[i][j] = 0;
                    }
                }
                System.out.println();
            }
            matrisCreated = true;
        }
        if ((angle >= 350 && angle < 360) || (angle >= 0 && angle < 10)) {
            if (distance <= 50)
                matrix[0][1] = 1;
            else
                matrix[0][1] = 0;
        } else if (angle >= 290 && angle < 350) {
            if (distance <= 50)
                matrix[0][0] = 1;
            else
                matrix[0][0] = 0;
        } else if (angle >= 250 && angle < 290) {
            if (distance <= 50)
                matrix[1][0] = 1;
            else
                matrix[1][0] = 0;
        } else if (angle >= 190 && angle < 250) {
            if (distance <= 50)
                matrix[2][0] = 1;
            else
                matrix[2][0] = 0;
        } else if (angle >= 170 && angle < 190) {
            if (distance <= 50)
                matrix[2][1] = 1;
            else
                matrix[2][1] = 0;
        } else if (angle >= 110 && angle < 170) {
            if (distance <= 50)
                matrix[2][2] = 1;
            else
                matrix[2][2] = 0;
        } else if (angle >= 70 && angle < 110) {
            if (distance <= 50)
                matrix[1][2] = 1;
            else
                matrix[1][2] = 0;
        } else if (angle >= 10 && angle < 70) {
            if (distance <= 50)
                matrix[0][2] = 1;
            else
                matrix[0][2] = 0;
        }

        if (angle >= 179.9 && angle <= 180.0005) {
            if (distance <= 50)
                matrix[2][1] = 1;
            else
                matrix[2][1] = 0;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + "\t\t");
            }
            System.out.println();
        }
        System.out.println("\n\n*****************************\n\n");
    }


}
