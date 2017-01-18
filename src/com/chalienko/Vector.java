package com.chalienko;

/**
 * Created by Dmitriy-PC on 31.10.2016.
 */
public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double X, double Y, double Z)
    {
        this.x = X;
        this.y = Y;
        this.z = Z;
    }

    public Vector() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public static Vector normalize(Vector v1)
    {
        double length = 1.0 / Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);

        return new Vector(v1.x *= length, v1.y *= length, v1.z *= length);
    }

    public static Vector cross(Vector v1, Vector v2)
    {
        return new Vector(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
    }

    public static Vector multiply(Vector v1, Vector v2)
    {
        return new Vector(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
    }

    public static Vector multiply(Vector v1, double sc)
    {
        return new Vector(v1.x * sc, v1.y * sc, v1.z * sc);
    }

    public static double dotProduct(Vector v1, Vector v2)
    {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public static double dotProduct(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static Vector subtract(Vector v1, Vector v2)
    {
        return new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public static Vector add(Vector v1, Vector v2)
    {
        return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

}
