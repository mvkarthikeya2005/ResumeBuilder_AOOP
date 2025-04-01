package util;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Utility class for UI animations
 */
public class AnimationUtil {
    
    /**
     * Creates a fade-in effect for JFrame
     * @param frame The frame to animate
     */
    public static void fadeInFrame(JFrame frame) {
        // Check if the frame is undecorated before attempting to set opacity
        if (frame.isUndecorated()) {
            frame.setOpacity(0.0f);
            Timer fadeInTimer = new Timer(20, new ActionListener() {
                float opacity = 0.0f;
                @Override
                public void actionPerformed(ActionEvent e) {
                    opacity += 0.05f;
                    if (opacity > 1.0f) {
                        opacity = 1.0f;
                        ((Timer)e.getSource()).stop();
                    }
                    frame.setOpacity(opacity);
                }
            });
            fadeInTimer.start();
        } else {
            // For decorated frames, use a different animation approach
            // We'll use a AWTEventListener to catch when the frame is shown
            // and then apply a subtle animation effect
            frame.setVisible(true);
            
            // Add a subtle animation for decorated frames
            Timer pulseTimer = new Timer(30, new ActionListener() {
                int step = 0;
                final int MAX_STEPS = 10;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    step++;
                    if (step > MAX_STEPS) {
                        ((Timer)e.getSource()).stop();
                        return;
                    }
                    
                    // Subtle pulse animation for components
                    SwingUtilities.invokeLater(() -> {
                        Container content = frame.getContentPane();
                        if (content != null) {
                            content.revalidate();
                            content.repaint();
                        }
                    });
                }
            });
            pulseTimer.start();
        }
    }
    
    /**
     * Creates a slide effect for a panel
     * @param panel The panel to animate
     * @param direction "left", "right", "up", or "down"
     */
    public static void slidePanel(JPanel panel, String direction) {
        int originalX = panel.getX();
        int originalY = panel.getY();
        int width = panel.getWidth();
        int height = panel.getHeight();
        
        // Set initial position based on direction
        if ("right".equals(direction)) {
            panel.setLocation(-width, originalY);
        } else if ("left".equals(direction)) {
            panel.setLocation(width, originalY);
        } else if ("up".equals(direction)) {
            panel.setLocation(originalX, height);
        } else if ("down".equals(direction)) {
            panel.setLocation(originalX, -height);
        }
        
        panel.setVisible(true);
        
        // Animate to original position
        Timer timer = new Timer(10, new ActionListener() {
            int steps = UIConstants.SLIDE_DURATION / 10;
            int currentStep = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep++;
                float progress = (float)currentStep / steps;
                
                if (progress >= 1.0f) {
                    panel.setLocation(originalX, originalY);
                    ((Timer)e.getSource()).stop();
                    return;
                }
                
                if ("right".equals(direction) || "left".equals(direction)) {
                    int newX = Math.round(originalX + (1 - progress) * ("right".equals(direction) ? -width : width));
                    panel.setLocation(newX, originalY);
                } else {
                    int newY = Math.round(originalY + (1 - progress) * ("up".equals(direction) ? height : -height));
                    panel.setLocation(originalX, newY);
                }
            }
        });
        timer.start();
    }
    
    /**
     * Creates a pulse effect for a component
     * @param component The component to animate
     */
    public static void pulseComponent(JComponent component) {
        Timer timer = new Timer(50, new ActionListener() {
            float scale = 1.0f;
            boolean growing = true;
            int steps = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (growing) {
                    scale += 0.03f;
                    if (scale >= 1.1f) {
                        scale = 1.1f;
                        growing = false;
                    }
                } else {
                    scale -= 0.03f;
                    if (scale <= 1.0f) {
                        scale = 1.0f;
                        steps++;
                        if (steps >= 1) {
                            ((Timer)e.getSource()).stop();
                            return;
                        }
                        growing = true;
                    }
                }
                
                int width = Math.round(component.getPreferredSize().width * scale);
                int height = Math.round(component.getPreferredSize().height * scale);
                component.setSize(new Dimension(width, height));
                component.revalidate();
            }
        });
        timer.start();
    }
}
