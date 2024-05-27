package Model.Objects;

import View.Main.GamePanel;
import Controller.Utility.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents a superclass for various objects in the game.
 */
public class SuperObject {

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 80, 80);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool uTool = new UtilityTool();


    /**
     * Draws the object on the game panel.
     * @param g2 The graphics context.
     * @param gp The GamePanel instance.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.screenWidth / 2;
        int screenY = worldY - gp.player.worldY + gp.screenHeight / 2;

        if (worldX + gp.tileSize * 2 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 2 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**
     * Defines interaction behavior for the object.
     */
    public void interact() {}
}
