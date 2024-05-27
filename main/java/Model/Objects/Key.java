package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a key object in the game.
 */
public class Key extends SuperObject {
    GamePanel gp;

    /**
     * Constructor for the Key class.
     * @param gp The GamePanel instance.
     */
    public Key(GamePanel gp) {
        this.gp = gp;
        name = "Key";
        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/key.png"));
            image = uTool.scaleImage(image,75, 75);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
