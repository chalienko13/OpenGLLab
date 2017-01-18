package com.chalienko.effect;

import com.chalienko.Vector;

/**
 * Created by Dmitriy Chalienko on 10.01.2017.
 */
public class Light {

    public static double[] getPaintingLight(Vector normal, Vector lightDirection, Vector viewerPosition) {
        Vector light = new Vector();
        Vector reflect = new Vector();

        Vector ambient = new Vector();
        Vector diffuse = new Vector();
        Vector specular = new Vector();

        Vector planeAmbient = new Vector(0.7, 0.7, 0.2);
        Vector planeDiffuse = new Vector(0.2, 1, 0.3);
        Vector planeSpecular = new Vector(0.2, 0.1, 0.2);

        Vector sourceAmbient = new Vector(0.7, 0.7, 0.2);
        Vector sourceDiffuse = new Vector(0.2, 1, 0.3);
        Vector sourceSpecular = new Vector(0.2, 0.1, 0.2);

        ambient = Vector.multiply(planeAmbient, sourceAmbient);
        ambient = Vector.normalize(ambient);
        ambient = Vector.multiply(ambient, 0.5);

        diffuse.setX(Math.max(Vector.dotProduct(lightDirection, normal), 0) * planeDiffuse.getX() * sourceDiffuse.getX());
        diffuse.setY(Math.max(Vector.dotProduct(lightDirection, normal), 0) * planeDiffuse.getY() * sourceDiffuse.getY());
        diffuse.setZ(Math.max(Vector.dotProduct(lightDirection, normal), 0) * planeDiffuse.getZ() * sourceDiffuse.getZ());
        diffuse = Vector.multiply(diffuse, 0.1f);

        reflect = Vector.subtract(lightDirection, Vector.multiply(normal, 2 * Vector.dotProduct(normal, lightDirection)));
        reflect = Vector.normalize(reflect);

        specular.setX(Math.max(Vector.dotProduct(reflect, viewerPosition), 0) * planeSpecular.getX() * sourceSpecular.getX());
        specular.setY(Math.max(Vector.dotProduct(reflect, viewerPosition), 0) * planeSpecular.getY() * sourceSpecular.getY());
        specular.setZ(Math.max(Vector.dotProduct(reflect, viewerPosition), 0) * planeSpecular.getZ() * sourceSpecular.getZ());
        specular = Vector.multiply(specular, 0.4f);

        light = Vector.add(ambient, diffuse);
        light = Vector.add(light, specular);

        return new double[]{light.getX(), light.getY(), light.getZ()};
    }
}
