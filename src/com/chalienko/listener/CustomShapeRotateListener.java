package com.chalienko.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class CustomShapeRotateListener implements MouseMotionListener, MouseListener {

    private double oldAngleX;
    private double oldAngleY;
    private double oldXCoord;
    private double oldYCoord;

    public double getAngleX() {
        return oldAngleX;
    }

    public double getAngleY() {
        return oldAngleY;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            oldAngleX += (oldXCoord - e.getX()) * 0.4;
            oldAngleY += (oldYCoord - e.getY()) * -0.4;
            oldXCoord = e.getX();
            oldYCoord = e.getY();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            oldXCoord = e.getXOnScreen();
            oldYCoord = e.getYOnScreen();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
