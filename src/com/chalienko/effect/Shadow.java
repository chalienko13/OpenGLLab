package com.chalienko.effect;

import com.chalienko.Vector;

/**
 * Created by Dmitriy Chalienko on 10.01.2017.
 */
public class Shadow {

    private Shadow(){}

    public static double[][] getShadowMatrix(double[] plane, double[] light) {
        double[][] shadowMatrix = new double[4][4];
        double dot = Vector.dotProduct(plane, light);

        shadowMatrix[0][0] = dot - light[0] * plane[0];
        shadowMatrix[1][0] = -light[0] * plane[1];
        shadowMatrix[2][0] = -light[0] * plane[2];
        shadowMatrix[3][0] = -light[0] * plane[3];

        shadowMatrix[0][1] = -light[1] * plane[0];
        shadowMatrix[1][1] = dot - light[1] * plane[1];
        shadowMatrix[2][1] = -light[1] * plane[2];
        shadowMatrix[3][1] = -light[1] * plane[3];

        shadowMatrix[0][2] = -light[2] * plane[0];
        shadowMatrix[1][2] = -light[2] * plane[1];
        shadowMatrix[2][2] = dot - light[2] * plane[2];
        shadowMatrix[3][2] = -light[2] * plane[3];

        shadowMatrix[0][3] = -light[3] * plane[0];
        shadowMatrix[1][3] = -light[3] * plane[1];
        shadowMatrix[2][3] = -light[3] * plane[2];
        shadowMatrix[3][3] = dot - light[3] * plane[3];

        return shadowMatrix;
    }


}
