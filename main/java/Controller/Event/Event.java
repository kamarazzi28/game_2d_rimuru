package Controller.Event;

import View.Main.GamePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Event class is responsible for managing in-game events, such as triggering specific actions
 * when the player intersects certain areas in the game world.
 */
public class Event {

    private static final Logger logger = LoggerFactory.getLogger(Event.class);

    private GamePanel gp;
    private EventRect[][] eventRect;

    private int prevEventX, prevEventY;
    private boolean canTouchEvent = true;

    /**
     * Constructs an Event object and initializes event rectangles for the game panel.
     *
     * @param gp The GamePanel object which contains game state and information.
     */
    public Event(GamePanel gp) {
        this.gp = gp;
        logger.info("Initializing Event with GamePanel");

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow]; // Adjusted to correct size
        int col = 0;
        int row = 0;

        // Initialize event rectangles for each tile in the game world
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 25;
            eventRect[col][row].y = 25;
            eventRect[col][row].width = 30;
            eventRect[col][row].height = 30;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Checks for events that should be triggered based on the player's current position.
     */
    public void checkEvent() {
        logger.debug("Checking events at player position ({}, {})", gp.player.worldX, gp.player.worldY);
        int xDistance = Math.abs(gp.player.worldX - prevEventX);
        int yDistance = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > 40) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            checkSaltEvent(13, 13);
            checkSaltEvent(15, 15);
            checkSaltEvent(23, 22);
            checkSaltEvent(8, 14);
            checkSaltEvent(4, 18);
            checkSaltEvent(9, 23);
            checkSaltEvent(12, 23);
            checkSaltEvent(3, 27);
            checkSaltEvent(19, 26);
            checkSaltEvent(12, 8);
            checkSaltEvent(26, 26);
        }
    }

    /**
     * Checks and triggers the salt event at the specified grid location.
     *
     * @param col The column index of the grid location.
     * @param row The row index of the grid location.
     */
    private void checkSaltEvent(int col, int row) {
        if (hitSalt(col, row, "any")) {
            logger.info("Salt event triggered at ({}, {})", col, row);
            damageSalt(col, row, gp.dialogueState);
        }
    }

    /**
     * Determines if the player has hit a salt event rectangle.
     *
     * @param col           The column index of the grid location.
     * @param row           The row index of the grid location.
     * @param reqDirection  The required direction for the event to be triggered.
     * @return              True if the player has hit the salt event rectangle, false otherwise.
     */
    public boolean hitSalt(int col, int row, String reqDirection) {
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.equals(reqDirection) || reqDirection.equals("any")) {
                hit = true;
                prevEventX = gp.player.worldX;
                prevEventY = gp.player.worldY;
                logger.debug("Player hit salt event at ({}, {})", col, row);
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
        return hit;
    }

    /**
     * Handles the damage caused by hitting a salt event.
     *
     * @param col       The column index of the grid location.
     * @param row       The row index of the grid location.
     * @param gameState The game state to be set after the event.
     */
    public void damageSalt(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "SALT!";
        gp.ui.dialogueSource = "event";  // Set the source of the dialogue
        gp.player.setLife(gp.player.getLife() - 1);
        eventRect[col][row].eventDone = true; // Mark event as done
        canTouchEvent = false;
    }
}
