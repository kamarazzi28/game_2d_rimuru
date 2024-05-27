package Model.Enemy;

import Model.Entity.Entity;
import View.Main.GamePanel;

import java.util.Random;

/**
 * Class representing a Red Slime enemy.
 */
public class RedSlime extends Entity {
    private final GamePanel gp;

    /**
     * Constructor for RedSlime class.
     * @param gp The GamePanel instance.
     */
    public RedSlime(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Red Slime";
        direction = "left";
        setType(2);
        speed = 2;
        setMaxLife(4);
        setLife(getMaxLife());
        setExp(10);

        solidArea.x = 5;
        solidArea.y = 27;
        solidArea.width = 70;
        solidArea.height = 53;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    /**
     * Loads images for the Red Slime.
     */
    public void getImage(){
        tu = setup("resources/Images/Enemy/redslime_down_1", gp.tileSize, gp.tileSize);
        td = setup("resources/Images/Enemy/redslime_down_2", gp.tileSize, gp.tileSize);
        tl = setup("resources/Images/Enemy/redslime_down_1", gp.tileSize, gp.tileSize);
        tr = setup("resources/Images/Enemy/redslime_down_2", gp.tileSize, gp.tileSize);
        gu = setup("resources/Images/Enemy/redslime_down_1", gp.tileSize, gp.tileSize);
        gd = setup("resources/Images/Enemy/redslime_down_2", gp.tileSize, gp.tileSize);
        gl = setup("resources/Images/Enemy/redslime_down_1", gp.tileSize, gp.tileSize);
        gr = setup("resources/Images/Enemy/redslime_down_2", gp.tileSize, gp.tileSize);
    }

    /**
     * Sets the action for the Red Slime.
     */
    public void setAction(){
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
     * Reacts to damage inflicted on the Red Slime.
     */
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    @Override
    public void speak() {

    }
}
