package Model.Objects;

import View.Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a door object in the game.
 */
public class Door extends SuperObject {
    GamePanel gp;

    /**
     * Constructor for the Door class.
     * @param gp The GamePanel instance.
     */
    public Door(GamePanel gp) {
        this.gp = gp;
        name = "Door";
        collision = true;
        try {
            image = ImageIO.read(new FileInputStream("resources/Images/Objects/door_iron.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Handles interaction with the door object.
     */
    @Override
    public void interact() {
        int keyCount = 0;
        for (SuperObject item : gp.player.inventory) {
            if (item != null && item.name.equals("Key")) {
                keyCount++;
            }
        }

        if (keyCount >= 3) {
            // Remove 3 keys from inventory
            int keysToRemove = 3;
            for (int i = 0; i < gp.player.inventory.size() && keysToRemove > 0; i++) {
                if (gp.player.inventory.get(i).name.equals("Key")) {
                    gp.player.inventory.remove(i);
                    i--;
                    keysToRemove--;
                }
            }
            // Find this door in the game panel's object array and set it to null
            for (int i = 0; i < gp.object.length; i++) {
                if (gp.object[i] == this) {
                    gp.object[i] = null;
                    break;
                }
            }
            gp.playSE(5);
        } else {
            int keysNeeded = 3 - keyCount;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You need " + keysNeeded + " more key" + (keysNeeded > 1 ? "s" : "") + "!";
            gp.ui.dialogueSource = "door";  // Set the source of the dialogue
            gp.keyH.enterPressed = false; // Reset enter key pressed state to avoid immediate skip
        }
    }
}
