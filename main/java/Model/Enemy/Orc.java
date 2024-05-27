package Model.Enemy;

import Model.Entity.Entity;
import View.Main.GamePanel;

import java.util.Random;

/**
 * Class representing an Orc enemy.
 */
public class Orc extends Entity {
    private final GamePanel gp;

    /**
     * Constructor for Orc class.
     * @param gp The GamePanel instance.
     */
    public Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Red Slime";
        direction = "left";
        setType(2);
        speed = 1;
        setMaxLife(10);
        setLife(getMaxLife());
        setExp(20);

        solidArea.x = 5;
        solidArea.y = 27;
        solidArea.width = 70;
        solidArea.height = 53;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    /**
     * Loads images for the Orc.
     */
    public void getImage() {
        tu = setup("resources/Images/Enemy/orc_up_1", gp.tileSize, gp.tileSize);
        td = setup("resources/Images/Enemy/orc_down_1", gp.tileSize, gp.tileSize);
        tl = setup("resources/Images/Enemy/orc_left_1", gp.tileSize, gp.tileSize);
        tr = setup("resources/Images/Enemy/orc_right_1", gp.tileSize, gp.tileSize);
        gu = setup("resources/Images/Enemy/orc_up_2", gp.tileSize, gp.tileSize);
        gd = setup("resources/Images/Enemy/orc_down_2", gp.tileSize, gp.tileSize);
        gl = setup("resources/Images/Enemy/orc_left_2", gp.tileSize, gp.tileSize);
        gr = setup("resources/Images/Enemy/orc_right_2", gp.tileSize, gp.tileSize);
    }

    /**
     * Sets the action for the Orc.
     */
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 240) {
            Random random = new Random();
            int i = random.nextInt(40) + 1; //pick up
            if (i <= 10) {direction = "left";}
            if (i > 10 && i <= 20) {direction = "right";}
            if (i > 20 && i <= 30) {direction = "up";}
            if (i > 30) {direction = "down";}
            actionLockCounter = 0;
        }
    }

    /**
     * Reacts to damage inflicted on the Orc.
     */
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    @Override
    public void speak() {

    }
}
