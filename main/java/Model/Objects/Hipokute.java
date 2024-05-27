package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a Hipokute object in the game.
 */
public class Hipokute extends SuperObject{
    GamePanel gp;

    /**
     * Constructor for the Hipokute class.
     * @param gp The GamePanel instance.
     */
    public Hipokute(GamePanel gp) {
        this.gp = gp;
        name = "Hipokute";
        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/hipokute.png"));
            uTool.scaleImage(image,60, 60);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
