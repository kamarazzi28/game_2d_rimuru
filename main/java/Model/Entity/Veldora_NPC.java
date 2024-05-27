package Model.Entity;

import View.Main.GamePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing the Veldora NPC entity.
 */
public class Veldora_NPC extends Entity {

    /**
     * Constructor for the Veldora_NPC class.
     * @param gp The GamePanel instance.
     */
    public Veldora_NPC(GamePanel gp) {
        super(gp);

        direction = "left";
        speed = 0;
        getVeldorImage();
        setDialogue();
    }

    /**
     * Loads images for Veldora NPC.
     */
    public void getVeldorImage() {
        tl = setup("resources/Images/NPC/veldor_left", 100, 100);
        tr = setup("resources/Images/NPC/veldor_right", 100, 100);
        gl = setup("resources/Images/NPC/veldor_left_2", 100, 100);
        gr = setup("resources/Images/NPC/veldor_right_2", 100, 100);
    }

    /**
     * Sets the dialogue for Veldora NPC.
     */
    public void setDialogue() {
        // Ensure the dialogue list is initialized
        for (int i = 0; i < 20; i++) {
            dialogue.add(new ArrayList<>());
        }

        // Add dialogues to the list
        dialogue.get(0).add("Veldora: \n*smiling* Ah, Rimuru! \nReady to play hide and seek with some keys?");
        dialogue.get(0).add("Rimuru: \nKeys? For what?");
        dialogue.get(0).add("Veldora: \nThere’s a fancy crystal in a castle that’s calling your \nname." +
                " But you’ll need three keys to even peek inside..");
        dialogue.get(0).add("Rimuru: \nAnd where might these keys be?");
        dialogue.get(0).add("Veldora: \nOh, just scattered around this lovely, \nnot-at-all-dangerous forest.");
        dialogue.get(0).add("Veldora: \nNow that you're off on your quest, remember, \nthe forest is full of surprises!");
        dialogue.get(0).add("Rimuru: \nWhat kind of surprises?");
        dialogue.get(0).add("Veldora: \nYou’ll encounter wild slimes—less friendly cousins of \nyours " +
                "and a formidable orc guarding the path. \nThey aren’t keen on visitors");
        dialogue.get(0).add("Rimuru: \nGot any tips for dealing with them?");
        dialogue.get(0).add("Veldora: \nCertainly! Keep Hipokute herbs handy; they'll patch you up" +
                "\nAnd watch out for salt—it weakens your magical essence.");
        dialogue.get(0).add("Rimuru: \nThanks, Veldora. I’ll be on my toes.");
        dialogue.get(0).add("Veldora: \nOff you go then! \nFind that crystal and try not to vanish too suddenly!");
    }

    /**
     * Sets the action for Veldora NPC.
     */
    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 240) {
            Random random = new Random();
            int i = random.nextInt(10) + 1; //pick up

            if (i <= 5) {
                direction = "left";
            }
            if (i > 5) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {

    }

    /**
     * Initiates dialogue with the player.
     */

    @Override
    public void speak() {
        facePlayer();
        if (dialogue.get(dialogueSet).size() > dialogueIndex && dialogue.get(dialogueSet).get(dialogueIndex) != null) {
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = dialogue.get(dialogueSet).get(dialogueIndex);
            gp.ui.dialogueSource = "npc";
        } else {
            dialogueSet++;
            if (dialogueSet >= dialogue.size()) {
                dialogueSet = 0;
            }
            dialogueIndex = 0;
            if (dialogue.get(dialogueSet).size() > dialogueIndex && dialogue.get(dialogueSet).get(dialogueIndex) != null) {
                gp.ui.currentDialogue = dialogue.get(dialogueSet).get(dialogueIndex);
            }
        }
    }
}
