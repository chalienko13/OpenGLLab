package com.chalienko.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by Chalienko on 14.10.2016.
 */
public class CustomTranslateListener implements MouseMotionListener, MouseListener {

    private double xPosition;
    private double yPosition;
    private double oldXCoord;
    private double oldYCoord;


    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            xPosition += (x - oldXCoord) * 0.02;
            yPosition += (y - oldYCoord) * -0.02;
            oldXCoord = x;
            oldYCoord = y;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
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
}
