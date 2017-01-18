package com.chalienko.shape;

import com.chalienko.Vector;

public class SnailSurface {

    private static final double DELTA = 0.000001;

    public static double getX(double u, double v) {
        return (u * Math.cos(v) * Math.sin(u));
    }

    public static double getY(double u, double v) {
        return u * Math.cos(u) * Math.cos(v);
    }

    public static double getZ(double u, double v) {
        return -u * Math.sin(v);
    }

    public static Vector getNormal(double v, double u) {
        Vector vectorv = new Vector();
        Vector vectoru = new Vector();
        Vector res;

        vectoru.setX((SnailSurface.getX(u + DELTA, v) - SnailSurface.getX(u, v)) / DELTA);
        vectoru.setY((SnailSurface.getY(u + DELTA, v) - SnailSurface.getY(u, v)) / DELTA);
        vectoru.setZ((SnailSurface.getZ(u + DELTA, v) - SnailSurface.getZ(u, v)) / DELTA);

        vectorv.setX((SnailSurface.getX(u, v + DELTA) - SnailSurface.getX(u, v)) / DELTA);
        vectorv.setY((SnailSurface.getY(u, v + DELTA) - SnailSurface.getY(u, v)) / DELTA);
        vectorv.setZ((SnailSurface.getZ(u, v + DELTA) - SnailSurface.getZ(u, v)) / DELTA);

        res = Vector.cross(vectorv, vectoru);
        res = Vector.normalize(res);

        return res;
    }
}
