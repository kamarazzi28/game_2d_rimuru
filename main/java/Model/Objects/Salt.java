package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a salt object in the game.
 */
public class Salt extends SuperObject {
    GamePanel gp;

    /**
     * Constructor for the Salt class.
     * @param gp The GamePanel instance.
     */
    public Salt(GamePanel gp) {
        this.gp = gp;
        name = "Salt";

        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/salt.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
