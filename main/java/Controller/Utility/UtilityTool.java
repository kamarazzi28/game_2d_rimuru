package Controller.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The UtilityTool class provides various utility functions used throughout the game.
 */
public class UtilityTool {

    private static final Logger logger = Logger.getLogger(UtilityTool.class.getName());

    /**
     * Scales a BufferedImage to the specified width and height.
     *
     * @param original the original BufferedImage to be scaled
     * @param width the desired width of the scaled image
     * @param height the desired height of the scaled image
     * @return the scaled BufferedImage
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // Create a new BufferedImage with the specified width and height
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());

        // Get the Graphics2D context of the scaled image
        Graphics2D g2 = scaledImage.createGraphics();

        // Draw the original image scaled to the new dimensions
        g2.drawImage(original, 0, 0, width, height, null);

        // Dispose of the graphics context to release resources
        g2.dispose();

        // Log the successful scaling of the image
        logger.log(Level.INFO, "Image scaled to width: {0}, height: {1}", new Object[]{width, height});

        return scaledImage;
    }
}
