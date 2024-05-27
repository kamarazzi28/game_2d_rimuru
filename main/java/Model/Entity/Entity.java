package Model.Entity;

import View.Main.GamePanel;
import Controller.Utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing an entity in the game.
 */
public abstract class Entity {
    protected GamePanel gp;
    protected String name;
    public int worldX;
    public int worldY;
    public int speed;

    protected BufferedImage tu, gu, td, gd, tr, gr, tl, gl;
    protected BufferedImage attackTu, attackGu, attackTd, attackGd, attackTr, attackGr, attackTl, attackGl;
    public String direction;

    // SOLID AREA (COLLISION)
    protected int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 80, 80);
    protected Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collision = false;

    // COUNTERS
    protected int actionLockCounter = 0;
    protected int invincibleCounter = 0;
    protected int spriteCounter = 0;
    protected int dyingCounter = 0;
    protected int hpBarCounter = 0;

    // DIALOGUE
    public List<List<String>> dialogue = new ArrayList<>();
    public int dialogueSet = 0;
    public int dialogueIndex = 0;

    // BOOLEANS
    protected boolean invincible = false;
    protected boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    protected boolean hpBarOn = false;

    // CHARACTERISTICS
    private int maxLife;
    private int life;
    private int type;  // 0 = player, 1 = npc, 2 = enemy
    private int level;
    private int exp;
    private int nextLevelExp;
    private int strength;

    public Entity(GamePanel gp) {
        this.gp = gp;
        // Initialize the dialogue list with empty lists
        for (int i = 0; i < 20; i++) {
            dialogue.add(new ArrayList<>());
        }
    }

    /**
     * Loads and scales an image.
     * @param imageName The name of the image file.
     * @param width The desired width of the image.
     * @param height The desired height of the image.
     * @return The loaded and scaled image.
     */
    protected BufferedImage setup(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(imageName + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return image;
    }

    /**
     * Sets the action for the entity.
     */
    protected abstract void setAction();

    /**
     * Defines the reaction when the entity takes damage.
     */
    protected abstract void damageReaction();

    /**
     * Handles the dialogue when the entity speaks.
     */
    protected abstract void speak();

    protected void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = entity.dialogue.get(setNum).get(entity.dialogueIndex);
    }

    protected void facePlayer() {
        switch (gp.player.direction) {
            case "left":
                direction = "left";
                break;
            case "right":
                direction = "right";
                break;
        }
    }

    public void update() {
        setAction();
        collision = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.enemy);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.getType() == 2 && contactPlayer) {
            if (!gp.player.invincible) {
                gp.playSE(2);
                gp.player.setLife(gp.player.getLife() - 1);
                gp.player.invincible = true;
            }
        }
        if (!collision) {
            switch (direction) {
                case "up":
                    this.worldY -= this.speed;
                    break;
                case "down":
                    this.worldY += this.speed;
                    break;
                case "right":
                    this.worldX += this.speed;
                    break;
                case "left":
                    this.worldX -= this.speed;
                    break;
            }
        }
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    protected void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;
        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    protected void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.screenWidth / 2;
        int screenY = worldY - gp.player.worldY + gp.screenHeight / 2;

        if (worldX + gp.tileSize * 2 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 2 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = tu;
                    }
                    if (spriteNum == 2) {
                        image = gu;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = td;
                    }
                    if (spriteNum == 2) {
                        image = gd;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = tl;
                    }
                    if (spriteNum == 2) {
                        image = gl;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = tr;
                    }
                    if (spriteNum == 2) {
                        image = gr;
                    }
                    break;
            }
            if (getType() == 2 && hpBarOn) {
                double oneScale = (double) gp.tileSize / getMaxLife();
                double hpBarValue = oneScale * getLife();

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                g2.setColor(new Color(173, 23, 23));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
                hpBarCounter++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if (dying) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1f);
        }
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
