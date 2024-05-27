package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a chest object in the game.
 */
public class Chest extends SuperObject {
    GamePanel gp;

    /**
     * Constructor for the Chest class.
     * @param gp The GamePanel instance.
     */
    public Chest(GamePanel gp) {

        this.gp = gp;
        name = "Chest";
        collision = true;
        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/chest.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);


        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Performs interaction with the chest object.
     */
    @Override
    public void interact() {
        for (int i = 0; i < gp.object.length; i++) {
            if (gp.object[i] == this) {
                gp.object[i] = null;
                gp.playSE(8);
                int currentLevel = gp.player.getLevel(); // Store the current level
                gp.player.setLevel(gp.player.getLevel() + 1);
                gp.ui.levelPassed = currentLevel;
                if(gp.player.getLife() != gp.player.getMaxLife()){
                    gp.player.setLife(gp.player.getMaxLife());
                }
                gp.gameState = gp.levelPassed;
                gp.saveLoad.save();
                break;
            }
        }
    }
}
