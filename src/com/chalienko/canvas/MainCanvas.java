package com.chalienko.canvas;

import com.chalienko.Vector;
import com.chalienko.effect.Light;
import com.chalienko.effect.Shadow;
import com.chalienko.listener.CustomShapeRotateListener;
import com.chalienko.listener.CustomTranslateListener;
import com.chalienko.listener.CustomZoomListener;
import com.chalienko.shape.SnailSurface;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.sun.prism.impl.BufferUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES1.GL_MODULATE;
import static com.jogamp.opengl.GL2ES1.GL_TEXTURE_ENV;
import static com.jogamp.opengl.GL2ES1.GL_TEXTURE_ENV_MODE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by Dmitriy Chalienko on 10.01.2017.
 */
public class MainCanvas extends GLCanvas implements GLEventListener {

    private static MainCanvas instance;

    private GLU glu;
    private static final float DELTA_ZOOM = 1f;

    private CustomZoomListener zoomListener;
    private CustomShapeRotateListener customShapeRotateListener;
    private CustomTranslateListener customTranslateListener;
    private int textureId;
    private int[] textureSizes = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 256, 512};

    int[] textures = new int[1];

    private MainCanvas() {
        this.addGLEventListener(this);
    }

    public static MainCanvas getInstance() {
        if (instance == null) {
            instance = new MainCanvas();
        }
        return instance;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        zoomListener = new CustomZoomListener(DELTA_ZOOM).setZoom(-14f);
        customShapeRotateListener = new CustomShapeRotateListener();
        customTranslateListener = new CustomTranslateListener();
        this.addMouseMotionListener(customTranslateListener);
        if (instance != null) {
            instance.addMouseMotionListener(customShapeRotateListener);
            instance.addMouseListener(customTranslateListener);
            instance.addMouseListener(customShapeRotateListener);
            instance.addMouseWheelListener(zoomListener);
        } else {
            throw new RuntimeException("Instance of MainCanvas is null");
        }
        GL2 gl = drawable.getGL().getGL2();

        gl.glEnable(GL_TEXTURE_2D);

        gl.glGenTextures(1, textures, 0);


        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

        BufferedImage image ;
        URL imgPath = getClass().getClassLoader().getResource("com/chalienko/resource/img.png");
        if (imgPath == null) {
            throw new RuntimeException("Image or path to image not found");
        }
        try {
            image = ImageIO.read(imgPath);
            DataBufferByte dbb = (DataBufferByte) image.getRaster().getDataBuffer();
            byte[] data = dbb.getData();
            ByteBuffer pixels = BufferUtil.newByteBuffer(data.length);
            pixels.put(data);
            pixels.flip();
            gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        if (height == 0) height = 1;
        float aspect = (float) width / height;
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, aspect, 1, 100.0);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        gl.glLoadIdentity();
        gl.glTranslated(customTranslateListener.getXPosition(), customTranslateListener.getYPosition(), zoomListener.getZoom());
        gl.glRotatef((float) customShapeRotateListener.getAngleX(), 0f, 1f, 0f);

        gl.glRotatef((float) customShapeRotateListener.getAngleY(), 1f, 0f, 0f);


        gl.glColor3d(0.0f, 0.7f, 0.8f);

        gl.glBindTexture(GL_TEXTURE_2D, textures[0]);
        gl.glBegin(GL2.GL_QUADS);
        int length = 25;
        gl.glTexCoord3d(-length, -length, -7);
        gl.glVertex3d(-length, -length, -7);

        gl.glTexCoord3d(-length, length, -7);
        gl.glVertex3d(-length, length, -7);

        gl.glTexCoord3d(length, length, -7);
        gl.glVertex3d(length, length, -7);

        gl.glTexCoord3d(length, -length, -7);
        gl.glVertex3d(length, -length, -7);

        gl.glEnd();

        double[][] shadowMatrix = Shadow.getShadowMatrix(new double[]{0, 0, 1, 7}, new double[]{10, 20, 20, 1});
        double[] shadowMas = new double[16];
        int k = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                shadowMas[k] = shadowMatrix[i][j];
                k++;
            }
        gl.glDisable(GL2.GL_DEPTH_TEST);
        gl.glPushMatrix();
        gl.glMultMatrixd(shadowMas, 0);
        drawSurface(gl, true);
        gl.glPopMatrix();

        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glShadeModel(GL_SMOOTH);
        drawSurface(gl, false);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    private Vector initializePosition(double[] matrix) {
        return new Vector(-(matrix[0] * matrix[12] + matrix[1] * matrix[13] + matrix[2] * matrix[14]),
                -(matrix[4] * matrix[12] + matrix[5] * matrix[13] + matrix[6] * matrix[14]),
                -(matrix[8] * matrix[12] + matrix[9] * matrix[13] + matrix[10] * matrix[14]));
    }

    private void drawSurface(GL2 gl, boolean shadow) {
        double[] matrix = new double[16];
        Vector lightPosition = new Vector(10, 0, 0);
        Vector viewerPosition = initializePosition(matrix);

        double v = -Math.PI;
        double vMax = Math.PI;
        double deltaV = 0.08F;
        double u = 0;
        double uMax = 2 * Math.PI;
        double deltaU = 0.08F;

        gl.glEnable(GL_TEXTURE_2D);
        gl.glBindTexture(GL_TEXTURE_2D, textures[0]);


        for (double tempU = u; tempU <= uMax - deltaU + 0.1; tempU += deltaU) {
            for (double tempV = v; tempV <= vMax - deltaV + 0.1; tempV += deltaV) {

                gl.glBegin(GL2.GL_POLYGON);

                double x = SnailSurface.getX(tempU, tempV);
                double y = SnailSurface.getY(tempU, tempV);
                double z = SnailSurface.getZ(tempU, tempV);
                double[] colors;
                if (shadow) {
                    gl.glColor3d(0.15, 0.15, 0.15);
                } else {
                    colors = Light.getPaintingLight(SnailSurface.getNormal(tempV, tempU),
                            new Vector(x - lightPosition.getX(), y - lightPosition.getY(), z - lightPosition.getZ()),
                            viewerPosition);
                    gl.glColor3d(colors[0], colors[1], colors[2]);
                }
                gl.glTexCoord3d(x, y,z);
                gl.glVertex3d(x, y, z);

                x = SnailSurface.getX(tempU, tempV + deltaV);
                y = SnailSurface.getY(tempU, tempV + deltaV);
                z = SnailSurface.getZ(tempU, tempV + deltaV);
                if (shadow) {
                    gl.glColor3d(0.15, 0.15, 0.15);
                } else {
                    colors = Light.getPaintingLight(SnailSurface.getNormal(tempV + deltaV, tempU),
                            new Vector(x - lightPosition.getX(), y - lightPosition.getY(), z - lightPosition.getZ()),
                            viewerPosition);
                    gl.glColor3d(colors[0], colors[1], colors[2]);
                }
                gl.glTexCoord3d(x, y,z);
                gl.glVertex3d(x, y, z);

                x = SnailSurface.getX(tempU + deltaU, tempV + deltaV);
                y = SnailSurface.getY(tempU + deltaU, tempV + deltaV);
                z = SnailSurface.getZ(tempU + deltaU, tempV + deltaV);
                if (shadow) {
                    gl.glColor3d(0.15, 0.15, 0.15);
                } else {
                    colors = Light.getPaintingLight(SnailSurface.getNormal(tempV + deltaV, tempU),
                            new Vector(x - lightPosition.getX(), y - lightPosition.getY(), z - lightPosition.getZ()),
                            viewerPosition);
                    gl.glColor3d(colors[0], colors[1], colors[2]);
                }
                gl.glTexCoord3d(x, y,z);
                gl.glVertex3d(x, y, z);

                x = SnailSurface.getX(tempU + deltaU, tempV);
                y = SnailSurface.getY(tempU + deltaU, tempV);
                z = SnailSurface.getZ(tempU + deltaU, tempV);
                if (shadow) {
                    gl.glColor3d(0.15, 0.15, 0.15);
                } else {
                    colors = Light.getPaintingLight(SnailSurface.getNormal(tempV + deltaV, tempU),
                            new Vector(x - lightPosition.getX(), y - lightPosition.getY(), z - lightPosition.getZ()),
                            viewerPosition);
                    gl.glColor3d(colors[0], colors[1], colors[2]);
                }
                gl.glTexCoord3d(x, y,z);
                gl.glVertex3d(x, y, z);
                gl.glEnd();
            }
        }
    }
}