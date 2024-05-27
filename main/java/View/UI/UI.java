package View.UI;

import Model.Objects.Heart;
import Model.Objects.SuperObject;
import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class handles the user interface (UI) components such as drawing player life,
 * messages, and various game screens (e.g., title screen, game over screen).
 */
public class UI {
    private static final Logger logger = Logger.getLogger(UI.class.getName());

    private GamePanel gp;
    private Graphics2D g2;
    private Font maruMonica;
    private BufferedImage heart_full, heart_half, heart_blank, menu_image;
    public String currentDialogue = "";
    public String dialogueSource = ""; // Add this to track the source of the dialogue
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: first screen, 1: second
    public int slotCol = 0;
    public int slotRow = 0;
    public int subState = 0;
    public int levelPassed;

    public List<String> message = new ArrayList<>();
    private List<Integer> messageCounter = new ArrayList<>();

    /**
     * Constructs a UI object for managing the game's user interface.
     *
     * @param gp the game panel
     */
    public UI(GamePanel gp) {
        this.gp = gp;
        InputStream is = null;
        try {
            is = new FileInputStream("resources/Font/maruMonica.ttf");
        } catch (FileNotFoundException e) {
            logger.severe("Font file not found: " + e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            logger.severe("Font format exception: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.severe("IO exception: " + e.getMessage());
            e.printStackTrace(System.out);
        }
        // LIFE HEARTS IMAGES
        SuperObject heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        // MENU IMAGE SET
        try {
            menu_image = ImageIO.read(new FileInputStream("resources/Images/Other/icon_image.png"));
        } catch (IOException e) {
            logger.severe("Error loading menu image: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a message to be displayed on the screen.
     *
     * @param text the message text
     */
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    /**
     * Draws the appropriate UI elements based on the current game state.
     *
     * @param g2 the graphics context
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
        g2.setColor(Color.WHITE);

        // STATES:
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
            logger.info("Game over screen drawn");
        }
        if (gp.gameState == gp.levelPassed) {
            drawLevelPassedScreen();
            logger.info("Level " + levelPassed + " passed");
        }
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }
    }

    /**
     * Draws the player's life as hearts on the screen.
     */
    public void drawPlayerLife() {
        int x = 20;
        int y = 20;
        int i = 0;
        // DRAW MAX LIFE
        while (i < gp.player.getMaxLife() / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += 60;
        }
        // reset
        x = 20;
        y = 20;
        i = 0;
        // DRAW CURRENT LIFE
        while (i < gp.player.getLife()) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.getLife()) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += 60;
        }
    }

    /**
     * Draws messages on the screen.
     */
    public void drawMessage() {
        int messageX = 520;
        int messageY = 60;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35f));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    /**
     * Draws the title screen.
     */
    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setColor(new Color(207, 240, 243));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
            String text = "  Reincarnation into slime:  \nThe Adventures of Rimuru";
            String[] lines = text.split("\n");

            int y = 80;
            int lineHeight = g2.getFontMetrics().getHeight();
            // MENU TEXT PARAMETERS
            for (String line : lines) {
                int x = getXForCenterText(line);
                g2.setColor(new Color(147, 181, 184));
                g2.drawString(line, x - 3, y - 2); // Draw shadow slightly offset
                y += lineHeight;
            }
            y = gp.tileSize; // Reset y position to start point for main text
            // MENU TEXT
            for (String line : lines) {
                int x = getXForCenterText(line);
                g2.setColor(Color.BLACK);
                g2.drawString(line, x, y);
                y += lineHeight; // Increment y for next line
            }
            // IMAGE
            int x = 0;
            y = gp.screenHeight / 2;
            g2.drawImage(menu_image, x, y, 300, y, null);

            // MENU TEXT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
            y = gp.tileSize;

            text = "new game";
            x = 400;
            y += 220;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "load game";
            x = 400;
            y += 50;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "quit";
            x = 450;
            y += 50;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
        }
        // INTRO MENU
        else if (titleScreenState == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 25F));

            String text = "\"It's simple. I want to create a world where life is as convenient \nas I wish it to be." +
                    " A comfortable world where everyone can \nlaugh and enjoy their lives as much as possible. \n" +
                    "That's what I truly and honestly want.\n" +
                    "\n" +
                    "Ideals without power are nonsense, and power without ideals \nis empty, right?" +
                    " I'm a greedy guy, but I don't have an interest \nin seeking power just " +
                    "for the sake of power, \n without the ambition to do something with it.\"";

            String[] lines = text.split("\n");

            int y = 100;
            int lineHeight = g2.getFontMetrics().getHeight();

            // Draw each line of the main text
            for (String line : lines) {
                int x = getXForCenterText(line);
                g2.drawString(line, x, y);
                y += lineHeight; // Move y down for the next line
            }

            // Additional text below the main text
            text = " - Rimuru.";
            int x = 500;
            y += lineHeight++; // Space before the next section
            g2.drawString(text, x, y);

            // Instruction text
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
            text = "Press Enter to continue";
            x = getXForCenterText(text);
            y += 120;
            g2.drawString(text, x, y);
        }
    }

    /**
     * Draws the dialogue screen.
     */
    public void drawDialogueScreen() {
        // DIALOGUE WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize * 5;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);

        // TEXT DIALOGUE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25));
        x += 20;
        y += 35;

        // Display the current dialogue
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }

        // Handle dialogue progression
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            if ("npc".equals(dialogueSource)) {
                gp.npc[0].dialogueIndex++;
                if (gp.npc[0].dialogueIndex >= gp.npc[0].dialogue.get(gp.npc[0].dialogueSet).size()) {
                    gp.npc[0].dialogueIndex = 0;
                    gp.gameState = gp.playState;
                } else {
                    gp.ui.currentDialogue = gp.npc[0].dialogue.get(gp.npc[0].dialogueSet).get(gp.npc[0].dialogueIndex);
                }
            } else {
                gp.gameState = gp.playState;
            }
        }
    }


    /**
     * Draws the character screen.
     */
    private void drawCharacterScreen() {
        // FRAME
        final int frameX = 40;
        final int frameY = 40;
        final int frameWidth = 300;
        final int frameHeight = 280;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        // TEXT
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(35f));

        int textX = frameX + 30;
        int textY = frameY + 60;
        final int lineHeight = 45;
        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Experience", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // reset
        textY = frameY + 60;
        String value;

        value = String.valueOf(gp.player.getLevel());
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getLife() + "/" + gp.player.getMaxLife());
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getStrength());
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getExp());
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.getNextLevelExp());
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
    }

    /**
     * Draws the level passed screen.
     */
    private void drawLevelPassedScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));

        text = "LEVEL " + levelPassed + " PASSED";
        g2.setColor(Color.BLACK);
        x = getXForCenterText(text);
        y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // NEXT LEVEL
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
        text = "Next Level";
        x = getXForCenterText(text);
        y += gp.screenHeight / 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
            if (gp.keyH.enterPressed) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
                gp.resetGame(false);
            }
        }
        // MENU
        text = "Menu";
        x = getXForCenterText(text);
        y += 50;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
            if (gp.keyH.enterPressed) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
                gp.resetGame(true);
            }
        }
    }

    /**
     * Draws the game over screen.
     */
    private void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));

        text = "GAME OVER";
        g2.setColor(Color.BLACK);
        x = getXForCenterText(text);
        y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
        text = "Retry";
        x = getXForCenterText(text);
        y += gp.screenHeight / 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
            if (gp.keyH.enterPressed) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
            }
        }

        // MENU
        text = "Menu";
        x = getXForCenterText(text);
        y += 50;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
            if (gp.keyH.enterPressed) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
            }
        }
    }

    /**
     * Draws the options screen.
     */
    private void drawOptionsScreen() {
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(35f));

        // FRAME
        final int frameX = gp.tileSize * 3;
        final int frameY = gp.tileSize;
        final int frameWidth = 316;
        final int frameHeight = 400;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                optionsTop(frameX, frameY);
                break;
            case 1:
                optionsControl(frameX, frameY);
                break;
            case 2:
                optionEnd(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;
    }

    /**
     * Handles drawing the exit game confirmation in the options screen.
     *
     * @param frameX the x-coordinate of the frame
     * @param frameY the y-coordinate of the frame
     */
    private void optionEnd(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;

        currentDialogue = "Are you sure?";
        g2.drawString(currentDialogue, textX, textY);

        String text = "Yes";
        textX = getXForCenterText(text) + 10;
        textY = frameY + 180;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed) {
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
                gp.resetGame(true);
                subState = 0;
                commandNum = 0;
            }
        }

        text = "No";
        textX = getXForCenterText(text) + 10;
        textY = frameY + 260;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 1;
            }
        }
    }

    /**
     * Handles drawing the top-level options in the options screen.
     *
     * @param frameX the x-coordinate of the frame
     * @param frameY the y-coordinate of the frame
     */
    private void optionsTop(int frameX, int frameY) {
        int textX;
        int textY;

        // NAME
        String text = "Options";
        textX = getXForCenterText(text) + 10;
        textY = frameY + 60;
        g2.drawString(text, textX, textY);

        // VOLUME
        textX = frameX + 50;
        textY += 60;
        g2.drawString("Volume", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 20, textY);
        }
        // CONTROL
        textY += 60;
        g2.drawString("Control", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed) {
                subState = 1;
                commandNum = 0;
            }
        }
        // END GAME
        textY += 60;
        g2.drawString("Exit game", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }
        // BACK
        textY += 100;
        g2.drawString("Back", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
            }
        }
        // CHECK BOXES SOUND
        textX = frameX + 190;
        textY = frameY + gp.tileSize + 20;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 96, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    }

    /**
     * Handles drawing the controls in the options screen.
     *
     * @param frameX the x-coordinate of the frame
     * @param frameY the y-coordinate of the frame
     */
    private void optionsControl(int frameX, int frameY) {
        int textX;
        int textY;

        // NAME
        String text = "Control";
        textX = getXForCenterText(text) + 10;
        textY = frameY + 60;
        g2.drawString(text, textX, textY);

        // NAMES OF KEYS
        textX = frameX + 20;
        textY += 10;
        g2.drawString("Move 1", textX, textY += 40);
        g2.drawString("Move 2", textX, textY += 40);
        g2.drawString("Action", textX, textY += 40);
        g2.drawString("Stats", textX, textY += 40);
        g2.drawString("Pause", textX, textY += 40);
        g2.drawString("Options", textX, textY += 40);
        // KEYS
        textX = frameX + 170;
        textY = frameY + 70;
        g2.drawString("W A S D", textX, textY += 40);
        g2.drawString("↑ ↓ ← →", textX, textY += 40);
        g2.drawString("Enter", textX, textY += 40);
        g2.drawString("C", textX, textY += 40);
        g2.drawString("P", textX, textY += 40);
        g2.drawString("O", textX, textY += 40);
        // BACK TO SUBSTATE 1
        textX = frameX + 40;
        textY = frameY + 370;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 20, textY);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 2;
            }
        }
    }

    /**
     * Draws the inventory screen.
     */
    private void drawInventory() {
        // FRAME
        final int frameX = gp.tileSize * 6;
        final int frameY = gp.tileSize / 2;
        final int frameWidth = 215;
        final int frameHeight = 380;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        // SLOT
        final int slotXstart = frameX + 30;
        final int slotYstart = frameY + 30;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize;
        // DRAW ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            g2.drawImage(gp.player.inventory.get(i).image, slotX, slotY, null);
            slotX += gp.tileSize;
            if (i == 1 || i == 3 || i == 5 || i == 7) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize - 5;
        int cursorHeight = gp.tileSize;
        // DRAW CURSOR
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    /**
     * Draws a sub-window on the screen.
     *
     * @param x      the x-coordinate of the window
     * @param y      the y-coordinate of the window
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(255, 255, 255, 200);

        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(0, 0, 0, 200);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 35, 35);
    }
    /**
     * Draws the pause screen.
     */
    public void drawPauseScreen() {
        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
        int x = getXForCenterText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
        gp.stopMusic();
    }

    /**
     * Calculates the x-coordinate for centering text.
     *
     * @param text the text to be centered
     * @return the x-coordinate
     */
    public int getXForCenterText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    /**
     * Calculates the x-coordinate for right-aligned text.
     *
     * @param text  the text to be aligned
     * @param tailX the right edge x-coordinate
     * @return the x-coordinate
     */
    public int getXAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
