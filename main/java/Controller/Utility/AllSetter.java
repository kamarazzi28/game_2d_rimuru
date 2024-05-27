package Controller.Utility;

import Model.Enemy.GreenSlime;
import Model.Enemy.Orc;
import Model.Enemy.RedSlime;
import Model.Entity.Veldora_NPC;
import Model.Objects.*;
import View.Main.GamePanel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AllSetter is responsible for setting various objects, NPCs, and enemies in the game world.
 */
public class AllSetter {

    private static final Logger logger = Logger.getLogger(AllSetter.class.getName());
    private static AllSetter instance;
    private GamePanel gp;

    /**
     * Private constructor to prevent instantiation from outside the class.
     *
     * @param gp the GamePanel to associate with this AllSetter
     */
    private AllSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Returns the single instance of AllSetter.
     *
     * @param gp the GamePanel to associate with this AllSetter
     * @return the single instance of AllSetter
     */
    public static AllSetter getInstance(GamePanel gp) {
        if (instance == null) {
            instance = new AllSetter(gp);
        }
        return instance;
    }

    /**
     * Sets all the objects in the game world at their initial positions.
     */
    public void setObject() {
        int i = 0;

        // Place Salt objects in the game world
        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 13 * gp.tileSize;
        gp.object[i].worldY = 13 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 23 * gp.tileSize;
        gp.object[i].worldY = 22 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 15 * gp.tileSize;
        gp.object[i].worldY = 15 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 8 * gp.tileSize;
        gp.object[i].worldY = 14 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 4 * gp.tileSize;
        gp.object[i].worldY = 18 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 9 * gp.tileSize;
        gp.object[i].worldY = 23 * gp.tileSize;
        logger.log(Level.INFO, "Salt placed at ({0}, {1})", new Object[]{gp.object[i].worldX, gp.object[i].worldY});
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 12 * gp.tileSize;
        gp.object[i].worldY = 23 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 3 * gp.tileSize;
        gp.object[i].worldY = 27 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 19 * gp.tileSize;
        gp.object[i].worldY = 26 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 12 * gp.tileSize;
        gp.object[i].worldY = 8 * gp.tileSize;
        i++;

        gp.object[i] = new Salt(gp);
        gp.object[i].worldX = 26 * gp.tileSize;
        gp.object[i].worldY = 26 * gp.tileSize;
        i++;

        // Place Hipokute objects in the game world
        gp.object[i] = new Hipokute(gp);
        gp.object[i].worldX = 18 * gp.tileSize;
        gp.object[i].worldY = 5 * gp.tileSize;
        i++;

        gp.object[i] = new Hipokute(gp);
        gp.object[i].worldX = 7 * gp.tileSize;
        gp.object[i].worldY = 24 * gp.tileSize;
        i++;

        gp.object[i] = new Hipokute(gp);
        gp.object[i].worldX = 3 * gp.tileSize;
        gp.object[i].worldY = 13 * gp.tileSize;
        i++;

        gp.object[i] = new Hipokute(gp);
        gp.object[i].worldX = 13 * gp.tileSize;
        gp.object[i].worldY = 6 * gp.tileSize;
        i++;

        gp.object[i] = new Hipokute(gp);
        gp.object[i].worldX = 8 * gp.tileSize;
        gp.object[i].worldY = 15 * gp.tileSize;
        i++;

        // Place Key objects in the game world
        gp.object[i] = new Key(gp);
        gp.object[i].worldX = 8 * gp.tileSize;
        gp.object[i].worldY = 3 * gp.tileSize;
        i++;

        gp.object[i] = new Key(gp);
        gp.object[i].worldX = 26 * gp.tileSize;
        gp.object[i].worldY = 15 * gp.tileSize;
        i++;

        gp.object[i] = new Key(gp);
        gp.object[i].worldX = 5 * gp.tileSize;
        gp.object[i].worldY = 27 * gp.tileSize;
        i++;

        // Place Door objects in the game world
        gp.object[i] = new Door(gp);
        gp.object[i].worldX = 20 * gp.tileSize;
        gp.object[i].worldY = 24 * gp.tileSize;
        i++;

        // Place Chest objects in the game world
        gp.object[i] = new Chest(gp);
        gp.object[i].worldX = 20 * gp.tileSize;
        gp.object[i].worldY = 19 * gp.tileSize;
        i++;
    }

    /**
     * Sets all the NPCs in the game world at their initial positions.
     */
    public void setNPC() {
        gp.npc[0] = new Veldora_NPC(gp);
        gp.npc[0].worldX = gp.tileSize * 9;
        gp.npc[0].worldY = gp.tileSize * 10;
        logger.log(Level.INFO, "NPC Veldora_NPC placed at ({0}, {1})", new Object[]{gp.npc[0].worldX, gp.npc[0].worldY});
    }

    /**
     * Sets all the enemies in the game world at their initial positions.
     */
    public void setEnemy() {
        int i = 0;

        // Place GreenSlime enemies in the game world
        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 3;
        gp.enemy[i].worldY = gp.tileSize * 6;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 21;
        gp.enemy[i].worldY = gp.tileSize * 10;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 3;
        gp.enemy[i].worldY = gp.tileSize * 17;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 4;
        gp.enemy[i].worldY = gp.tileSize * 26;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 26;
        gp.enemy[i].worldY = gp.tileSize * 14;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new GreenSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 20;
        gp.enemy[i].worldY = gp.tileSize * 5;
        logger.log(Level.INFO, "GreenSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        // Place RedSlime enemies in the game world
        gp.enemy[i] = new RedSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 2;
        gp.enemy[i].worldY = gp.tileSize * 26;
        logger.log(Level.INFO, "RedSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new RedSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 20;
        gp.enemy[i].worldY = gp.tileSize * 21;
        logger.log(Level.INFO, "RedSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        gp.enemy[i] = new RedSlime(gp);
        gp.enemy[i].worldX = gp.tileSize * 15;
        gp.enemy[i].worldY = gp.tileSize * 19;
        logger.log(Level.INFO, "RedSlime placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;

        // Place Orc enemies in the game world
        gp.enemy[i] = new Orc(gp);
        gp.enemy[i].worldX = gp.tileSize * 18;
        gp.enemy[i].worldY = gp.tileSize * 20;
        logger.log(Level.INFO, "Orc placed at ({0}, {1})", new Object[]{gp.enemy[i].worldX, gp.enemy[i].worldY});
        i++;
    }
}
