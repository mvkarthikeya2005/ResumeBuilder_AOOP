package components;

import java.awt.*;
import javax.swing.*;

/**
 * Panel with gradient background for consistent UI
 */
public class GradientPanel extends JPanel {
    private Color startColor = new Color(41, 128, 185);
    private Color endColor = new Color(44, 62, 80);
    private GradientType gradientType = GradientType.DIAGONAL;
    
    public enum GradientType {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        RADIAL
    }
    
    public GradientPanel() {
        setOpaque(false);
    }
    
    public GradientPanel(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        setOpaque(false);
    }
    
    public GradientPanel(Color startColor, Color endColor, GradientType type) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.gradientType = type;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        // Create gradient based on type
        Paint paint;
        switch (gradientType) {
            case HORIZONTAL:
                paint = new GradientPaint(0, 0, startColor, w, 0, endColor);
                break;
            case VERTICAL:
                paint = new GradientPaint(0, 0, startColor, 0, h, endColor);
                break;
            case RADIAL:
                paint = new RadialGradientPaint(
                    new Point(w/2, h/2),
                    w > h ? w : h,
                    new float[]{0.0f, 1.0f},
                    new Color[]{startColor, endColor}
                );
                break;
            case DIAGONAL:
            default:
                paint = new GradientPaint(0, 0, startColor, w, h, endColor);
                break;
        }
        
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
        
        super.paintComponent(g);
    }
    
    public void setStartColor(Color color) {
        this.startColor = color;
        repaint();
    }
    
    public void setEndColor(Color color) {
        this.endColor = color;
        repaint();
    }
    
    public void setGradientType(GradientType type) {
        this.gradientType = type;
        repaint();
    }
}
