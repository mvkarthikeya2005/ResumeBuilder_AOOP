package util;

import java.awt.*;

/**
 * Constants for UI styling to maintain consistent design across all pages
 */
public class UIConstants {
    // Main colors
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);        // Blue
    public static final Color PRIMARY_DARK_COLOR = new Color(44, 62, 80);     // Dark blue/gray
    public static final Color SECONDARY_COLOR = new Color(46, 204, 113);      // Green
    public static final Color ACCENT_COLOR = new Color(230, 126, 34);         // Orange
    public static final Color DANGER_COLOR = new Color(231, 76, 60);          // Red
    
    // Background colors
    public static final Color BG_PANEL = new Color(255, 255, 255, 230);       // Semi-transparent white
    public static final Color BG_DARK = new Color(52, 73, 94);                // Dark background
    
    // Text colors
    public static final Color TEXT_DARK = new Color(44, 62, 80);              // Dark text
    public static final Color TEXT_MEDIUM = new Color(100, 100, 100);         // Medium text
    public static final Color TEXT_LIGHT = new Color(180, 180, 180);          // Light text
    public static final Color TEXT_WHITE = Color.WHITE;                       // White text
    
    // Fonts
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Animation durations (in milliseconds)
    public static final int ANIMATION_SHORT = 150;
    public static final int ANIMATION_MEDIUM = 300;
    public static final int ANIMATION_LONG = 500;
    
    // Spacing
    public static final int PADDING_SMALL = 5;
    public static final int PADDING_MEDIUM = 10;
    public static final int PADDING_LARGE = 20;
    
    // Border radii
    public static final int BORDER_RADIUS_SMALL = 5;
    public static final int BORDER_RADIUS_MEDIUM = 10;
    public static final int BORDER_RADIUS_LARGE = 20;
}
