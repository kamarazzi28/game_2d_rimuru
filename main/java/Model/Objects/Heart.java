package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a heart object in the game.
 */
public class Heart extends SuperObject {
    GamePanel gp;

    /**
     * Constructor for the Heart class.
     * @param gp The GamePanel instance.
     */
    public Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";
        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/heart_full.png"));
            image2 = ImageIO.read(new FileInputStream("resources/Images/Objects/heart_half.png"));
            image3 = ImageIO.read(new FileInputStream("resources/Images/Objects/heart_blank.png"));

            image = uTool.scaleImage(image,60, 60);
            image2 = uTool.scaleImage(image2,60,60);
            image3 = uTool.scaleImage(image3,60,60);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
