package com.chalienko.listener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by Chalienko on 13.10.2016.
 */
public class CustomZoomListener implements MouseWheelListener {
    private float zoom;
    private float deltaZoom;

    public CustomZoomListener(float deltaZoom) {
        this.deltaZoom = deltaZoom;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double notches = e.getWheelRotation();
        if (notches < 0) {
            zoom -= deltaZoom;
        } else {
            zoom += deltaZoom;
        }
    }

    public float getDeltaZoom() {
        return deltaZoom;
    }

    public float getZoom() {
        return zoom;
    }

    public CustomZoomListener setZoom(float zoom) {
        this.zoom = zoom;
        return this;
    }
}
