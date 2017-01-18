import com.chalienko.Vector;
import com.chalienko.canvas.MainCanvas;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;


public class MainApp {
    private static final int CANVAS_WIDTH = 1024;
    private static final int CANVAS_HEIGHT = 780;
    private static final int FPS = 60;

    public static void main(String[] args) {
        GLCanvas canvas = MainCanvas.getInstance();

        SwingUtilities.invokeLater(() -> {

            canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

            final JFrame frame = new JFrame();
            frame.getContentPane().add(canvas);
            frame.pack();
            frame.setVisible(true);
            animator.start();
        });
    }
}