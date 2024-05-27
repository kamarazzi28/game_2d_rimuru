package Model.Entity;

import View.Main.GamePanel;
import Controller.Handler.KeyHandler;
import Model.Objects.Key;
import Model.Objects.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Player class represents the player character in the game.
 * It extends the Entity class and handles player-specific actions and attributes.
 */
public class Player extends Entity {

    private static final Logger logger = Logger.getLogger(Player.class.getName());

    private KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasHipokute = 0;

    public List<SuperObject> inventory = new ArrayList<>();
    public int maxInventorySize = 4;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize) / 2;
        screenY = gp.screenHeight / 2 - (gp.tileSize) / 2;

        solidArea = new Rectangle(-10, 10, 20, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 60;
        attackArea.height = 60;

        setDefaultVales();
        logger.log(Level.INFO, "Player initialized at default values.");
    }

    /**
     * Sets the default values for the player.
     */
    public void setDefaultVales() {
        worldX = gp.tileSize * 11;
        worldY = gp.tileSize * 11;
        speed = 5;
        direction = "down";

        // Default state
        setMaxLife(6);
        setLife(getMaxLife());
        setLevel(1);
        setExp(0);
        setStrength(1);
        setNextLevelExp(20);

        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    /**
     * Sets the default positions for the player.
     */
    public void setDefaultPositions() {
        worldX = gp.tileSize * 11;
        worldY = gp.tileSize * 11;
        direction = "down";
    }

    /**
     * Restores the player's life to the maximum value.
     */
    public void restoreLife() {
        setLife(getMaxLife());
        invincible = false;
    }

    /**
     * Loads the player's images.
     */
    public void getPlayerImage() {
        tu = setup("resources/Images/Player/rimuru_to_the_up", gp.tileSize, gp.tileSize);
        td = setup("resources/Images/Player/rimuru_to_the_down", gp.tileSize, gp.tileSize);
        tl = setup("resources/Images/Player/rimuru_to_the_left", gp.tileSize, gp.tileSize);
        tr = setup("resources/Images/Player/rimuru_to_the_right", gp.tileSize, gp.tileSize);
        gu = setup("resources/Images/Player/rimuru_goes_up", gp.tileSize, gp.tileSize);
        gd = setup("resources/Images/Player/rimuru_goes_down", gp.tileSize, gp.tileSize);
        gl = setup("resources/Images/Player/rimuru_goes_to_the_left", gp.tileSize, gp.tileSize);
        gr = setup("resources/Images/Player/rimuru_goes_to_the_right", gp.tileSize, gp.tileSize);
    }

    /**
     * Loads the player's attack images.
     */
    public void getPlayerAttackImage() {
        attackTu = setup("resources/Images/Player/attack_tu", gp.tileSize, gp.tileSize * 2);
        attackGu = setup("resources/Images/Player/attack_gu", gp.tileSize, gp.tileSize * 2);
        attackTd = setup("resources/Images/Player/attack_td", gp.tileSize, gp.tileSize * 2);
        attackGd = setup("resources/Images/Player/attack_gd", gp.tileSize, gp.tileSize * 2);
        attackTr = setup("resources/Images/Player/attack_tr", gp.tileSize * 2, gp.tileSize);
        attackGr = setup("resources/Images/Player/attack_gr", gp.tileSize * 2, gp.tileSize);
        attackTl = setup("resources/Images/Player/attack_tl", gp.tileSize * 2, gp.tileSize);
        attackGl = setup("resources/Images/Player/attack_gl", gp.tileSize * 2, gp.tileSize);
    }

    @Override
    public void setAction() {
        // Define player-specific actions
    }

    @Override
    public void damageReaction() {
        // Define how the player reacts to damage
    }

    @Override
    public void speak() {
        // Define player-specific dialogue behavior
    }

    /**
     * Updates the player's state.
     */
    public void update() {
        if (attacking) {
            attacking();
        } else if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed() || keyH.enterPressed) {
            handleMovement();
            handleCollisions();
            handleSpriteAnimation();
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (getLife() <= 0) {
            handlePlayerDeath();
        }
    }

    private void handleMovement() {
        if (keyH.isUpPressed()) {
            direction = "up";
        } else if (keyH.isDownPressed()) {
            direction = "down";
        } else if (keyH.isLeftPressed()) {
            direction = "left";
        } else if (keyH.isRightPressed()) {
            direction = "right";
        }
    }

    private void handleCollisions() {
        collision = false;
        gp.cChecker.checkTile(this);
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);
        meetsEnemy(enemyIndex);

        gp.event.checkEvent();

        if (!collision && !keyH.enterPressed) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        gp.keyH.enterPressed = false;
    }

    private void handleSpriteAnimation() {
        spriteCounter++;
        if (spriteCounter > 11) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void handlePlayerDeath() {
        gp.gameState = gp.gameOverState;
        logger.log(Level.INFO, "Player died. Switching to gameOverState.");
        gp.ui.commandNum = -1;
        gp.stopMusic();
        gp.playSE(7);
    }

    /**
     * Handles the player's attack state.
     */
    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        } else if (spriteCounter <= 25) {
            spriteNum = 2;
            handleAttackCollision();
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void handleAttackCollision() {
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;
        int enemyIndex = gp.cChecker.checkEntity(this, gp.enemy);

        switch (direction) {
            case "up":
                worldY -= attackArea.height;
                break;
            case "down":
                worldY += attackArea.height;
                break;
            case "left":
                worldX -= attackArea.width;
                break;
            case "right":
                worldX += attackArea.width;
                break;
        }

        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        damageEnemy(enemyIndex);

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    /**
     * Handles picking up objects.
     *
     * @param index the index of the object
     */
    public void pickUpObject(int index) {
        if (index != 999) {
            String objectName = gp.object[index].name;
            switch (objectName) {
                case "Hipokute":
                    hasHipokute++;
                    if (gp.player.getLife() != gp.player.getMaxLife()) {
                        gp.player.setLife(gp.player.getLife() + 1);
                    }
                    gp.ui.addMessage("1x " + objectName);
                    gp.playSE(5);
                    gp.object[index] = null;
                    break;
                case "Key":
                    if (inventory.size() != maxInventorySize) {
                        inventory.add(gp.object[index]);
                        gp.ui.addMessage("1x " + objectName);
                        gp.playSE(5);
                        gp.object[index] = null;
                    } else {
                        gp.ui.addMessage("Inventory is full");
                    }
                    break;
                case "Door":
                    gp.object[index].interact();
                    break;
                case "Chest":
                    gp.object[index].interact();
                    gp.ui.addMessage("Game saved");
                    break;
            }
        }
    }

    /**
     * Sets the default items for the player.
     */
    public void setItems() {
        inventory.clear();
        inventory.add(new Key(gp));
        inventory.add(new Key(gp));
    }

    /**
     * Interacts with NPCs.
     *
     * @param i the index of the NPC
     */
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
                gp.keyH.enterPressed = false; // Reset key press
            } else {
                attacking = true;
                gp.playSE(3);
            }
        }
    }

    /**
     * Handles meeting enemies.
     *
     * @param i the index of the enemy
     */
    public void meetsEnemy(int i) {
        if (i != 999) {
            if (!invincible && !gp.enemy[i].dying) {
                gp.playSE(2);
                setLife(getLife() - 1);
                invincible = true;
            }
        }
    }

    /**
     * Damages the enemy.
     *
     * @param i the index of the enemy
     */
    public void damageEnemy(int i) {
        if (i != 999) {
            if (!gp.enemy[i].invincible) {
                gp.playSE(3);
                gp.enemy[i].setLife(gp.enemy[i].getLife() - 1);
                gp.enemy[i].invincible = true;
                gp.enemy[i].damageReaction();
                if (gp.enemy[i].getLife() <= 0) {
                    gp.enemy[i].dying = true;
                    gp.ui.addMessage("1x " + gp.enemy[i].name);
                    setExp(getExp() + gp.enemy[i].getExp());
                    gp.ui.addMessage(gp.enemy[i].getExp() + "x Exp");
                    checkStrengthUp();
                }
            }
        }
    }

    /**
     * Checks if the player should level up.
     */
    private void checkStrengthUp() {
        if (getExp() >= getNextLevelExp()) {
            setStrength(getStrength() + 1);
            setNextLevelExp(getNextLevelExp() * 2 + 5);
            gp.playSE(4);

            // Ensure there's a list at index 0 and add the dialogue
            gp.ui.addMessage("Strength lvl " + getStrength() + "!");
        }
    }

    /**
     * Draws the player on the screen.
     *
     * @param g2 the Graphics2D object
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    image = (spriteNum == 1) ? tu : gu;
                } else {
                    tempScreenY -= gp.tileSize;
                    image = (spriteNum == 1) ? attackTu : attackGu;
                }
                break;
            case "down":
                if (!attacking) {
                    image = (spriteNum == 1) ? td : gd;
                } else {
                    image = (spriteNum == 1) ? attackTd : attackGd;
                }
                break;
            case "left":
                if (!attacking) {
                    image = (spriteNum == 1) ? tl : gl;
                } else {
                    tempScreenX -= gp.tileSize;
                    image = (spriteNum == 1) ? attackTl : attackGl;
                }
                break;
            case "right":
                if (!attacking) {
                    image = (spriteNum == 1) ? tr : gr;
                } else {
                    image = (spriteNum == 1) ? attackTr : attackGr;
                }
                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
