package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * Panel with rounded corners for consistent UI
 */
public class RoundPanel extends JPanel {
    private int cornerRadius;
    private Color shadowColor = new Color(0, 0, 0, 50);
    private int shadowSize = 5;
    private boolean drawShadow = true;
    private Color borderColor;
    private int borderThickness = 0;
    private boolean drawBorder = false;

    public RoundPanel() {
        setOpaque(false);
    }

    public RoundPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setOpaque(false);
    }

    public RoundPanel(int radius, boolean shadow) {
        super();
        this.cornerRadius = radius;
        this.drawShadow = shadow;
        setOpaque(false);
    }

    public RoundPanel(int radius, Color background) {
        this.cornerRadius = radius;
        setOpaque(false);
        setBackground(background);
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        this.drawBorder = true;
        repaint();
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        this.drawBorder = thickness > 0;
        repaint();
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }

    public void setShadowColor(Color color) {
        this.shadowColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Draw shadow if enabled
        if (drawShadow) {
            g2.setColor(shadowColor);
            g2.fill(new RoundRectangle2D.Float(
                shadowSize, shadowSize, 
                w - 1 - shadowSize, 
                h - 1 - shadowSize, 
                cornerRadius, cornerRadius));
        }

        // Draw panel background
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(
            0, 0, w - 1 - (drawShadow ? shadowSize : 0), 
            h - 1 - (drawShadow ? shadowSize : 0), 
            cornerRadius, cornerRadius));

        // Draw border if needed
        if (drawBorder) {
            g2.setColor(borderColor != null ? borderColor : getForeground());
            g2.setStroke(new BasicStroke(borderThickness));
            g2.draw(new RoundRectangle2D.Float(
                borderThickness / 2, borderThickness / 2, 
                w - borderThickness, h - borderThickness, 
                cornerRadius, cornerRadius));
        }

        g2.dispose();

        super.paintComponent(g);
    }
}
