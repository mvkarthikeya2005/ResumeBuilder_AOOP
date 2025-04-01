package components;

import util.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom rounded button for consistent UI
 */
public class RoundButton extends JButton {
    private int radius = 15;
    private Color hoverBackground;
    private Color pressedBackground;
    private Color originalBackground;
    private boolean isHover = false;
    private boolean isPressed = false;

    public RoundButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(UIConstants.BOLD_FONT);
        setForeground(UIConstants.TEXT_WHITE);
        setBackground(UIConstants.PRIMARY_COLOR);
        originalBackground = UIConstants.PRIMARY_COLOR;
        
        // Calculate hover and pressed colors (slightly darker/lighter)
        hoverBackground = darkenColor(getBackground(), 0.1f);
        pressedBackground = darkenColor(getBackground(), 0.2f);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHover = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHover = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine current background color based on state
        Color currentBg;
        if (isPressed) {
            currentBg = pressedBackground;
        } else if (isHover) {
            currentBg = hoverBackground;
        } else {
            currentBg = getBackground();
        }
        
        g2.setColor(currentBg);
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
        
        // Reset original color for potential background painting
        g2.setColor(getBackground());
        
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        originalBackground = bg;
        hoverBackground = darkenColor(bg, 0.1f);
        pressedBackground = darkenColor(bg, 0.2f);
    }
    
    private Color darkenColor(Color color, float factor) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(hsb[0], hsb[1], Math.max(0f, hsb[2] - factor));
    }
}
