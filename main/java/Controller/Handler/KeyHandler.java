package Controller.Handler;

import View.Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * The KeyHandler class handles keyboard inputs for the game.
 * It implements the KeyListener interface to capture key events.
 */
public class KeyHandler implements KeyListener {

    private static final int KEY_W = 87;
    private static final int KEY_A = 65;
    private static final int KEY_S = 83;
    private static final int KEY_D = 68;
    private static final int KEY_UP_ARROW = 38;
    private static final int KEY_LEFT_ARROW = 37;
    private static final int KEY_DOWN_ARROW = 40;
    private static final int KEY_RIGHT_ARROW = 39;
    private static final int KEY_ENTER = KeyEvent.VK_ENTER;
    private static final int KEY_P = KeyEvent.VK_P;
    private static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
    private static final int KEY_C = KeyEvent.VK_C;
    private static final int KEY_T = KeyEvent.VK_T;
    private static final int KEY_M = KeyEvent.VK_M;

    private final GamePanel gp;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    public boolean enterPressed;
    private boolean debugText = false;
    /**
     * Constructor for KeyHandler.
     *
     * @param gp the GamePanel instance
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.titleState){
            titleState(code);
        }
        else if (gp.gameState == gp.playState) {
            playState(code);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        } else if (gp.gameState == gp.characterState) {
            characterState(code);
        } else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        } else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        } else if (gp.gameState == gp.levelPassed) {
            levelPassed(code);
        }

    }
    /**
     * Handles key events for the game over state.
     *
     * @param code the key code of the pressed key
     */
    private void gameOverState(int code) {
        if (code == KEY_W || code == KEY_UP_ARROW) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if (code == KEY_S || code == KEY_DOWN_ARROW) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KEY_ENTER) {
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.resetGame(true);
            } else if (gp.ui.commandNum == 1){
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
                gp.resetGame(false);
            }
        }
    }
    /**
     * Handles key events for the level passed state.
     *
     * @param code the key code of the pressed key
     */
    private void levelPassed(int code){
        if (code == KEY_W || code == KEY_UP_ARROW) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if (code == KEY_S || code == KEY_DOWN_ARROW) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KEY_ENTER) {
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.resetGame(true);
            } else if (gp.ui.commandNum == 1){
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
                gp.resetGame(false);
            }
        }
    }
    /**
     * Handles key events for the title state.
     *
     * @param code the key code of the pressed key
     */
    private void titleState(int code){
        if(gp.ui.titleScreenState == 0){
            if (code == KEY_W || code == KEY_UP_ARROW) {
                gp.ui.commandNum --;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KEY_S || code == KEY_DOWN_ARROW) {
                gp.ui.commandNum ++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KEY_ENTER){
                if(gp.ui.commandNum == 0){
                    gp.ui.titleScreenState = 1;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;
                    gp.playMusic(1);

                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);
                }
            }
        }
        else if(gp.ui.titleScreenState == 1){
            if(code == KEY_ENTER){
                gp.stopMusic();
                gp.playMusic(1);
                gp.gameState = gp.playState;
            }
        }
    }
    /**
     * Handles key events for the play state.
     *
     * @param code the key code of the pressed key
     */
    private void playState(int code){
        if (code == KEY_W || code == KEY_UP_ARROW) {
            this.setUpPressed(true);
        }
        if (code == KEY_A || code == KEY_LEFT_ARROW) {
            this.setLeftPressed(true);
        }
        if (code == KEY_S || code == KEY_DOWN_ARROW) {
            this.setDownPressed(true);
        }
        if (code == KEY_D || code == KEY_RIGHT_ARROW) {
            this.setRightPressed(true);
        }
        // pause key
        if (code == KEY_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KEY_ESCAPE) {
            gp.gameState = gp.optionsState;
        }
        if (code == KEY_C) {
            gp.gameState = gp.characterState;
        }
        // enter key
        if (code == KEY_ENTER) {
            enterPressed = true;
        }
        // debug switcher
        if (code == KEY_T) {
            if (!isDebugText()) {
                setDebugText(true);}
            else if (isDebugText()) {
                setDebugText(false);}
        }
        if (code == KEY_M) {
            gp.tileManager.loadMap("resources/Maps/world_map.txt");
        }
    }
    /**
     * Handles key events for the pause state.
     *
     * @param code the key code of the pressed key
     */
    private void pauseState(int code){
        if (code == KEY_P) {
            gp.gameState = gp.playState;
        }
    }
    /**
     * Handles key events for the dialogue state.
     *
     * @param code the key code of the pressed key
     */
    private void dialogueState(int code){
        if (code == KEY_ENTER) {
            gp.keyH.enterPressed = true;
        }
    }
    /**
     * Handles key events for the character state.
     *
     * @param code the key code of the pressed key
     */
    private void characterState(int code){
        if (code == KEY_C){
            gp.gameState = gp.playState;
        }
        if(code == KEY_W || code == KEY_UP_ARROW){
            if(gp.ui.slotRow != 0){
            gp.ui.slotRow--;}
        }
        if(code == KEY_A || code == KEY_LEFT_ARROW){
            if(gp.ui.slotCol != 0){
            gp.ui.slotCol--;}
        }
        if(code == KEY_S || code == KEY_DOWN_ARROW){
            if(gp.ui.slotRow != 3){
            gp.ui.slotRow++;}
        }
        if(code == KEY_D || code == KEY_RIGHT_ARROW){
            if(gp.ui.slotCol != 1){
            gp.ui.slotCol++;}

        }
    }
    /**
     * Handles key events for the options state.
     *
     * @param code the key code of the pressed key
     */
    private void optionsState(int code) {
        if (code == KEY_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KEY_ENTER) {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0: maxCommandNum = 3; break;
            case 2: maxCommandNum = 1; break;
        }
        if (code == KEY_W || code == KEY_UP_ARROW) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KEY_S || code == KEY_DOWN_ARROW) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KEY_A || code == KEY_LEFT_ARROW) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 0 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
            }
        }
        if (code == KEY_D || code == KEY_RIGHT_ARROW) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 0 && gp.music.volumeScale < 4){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KEY_W || code == KEY_UP_ARROW) {
            this.setUpPressed(false);
        }
        if (code == KEY_A || code == KEY_LEFT_ARROW) {
            this.setLeftPressed(false);
        }
        if (code == KEY_S || code == KEY_DOWN_ARROW) {
            this.setDownPressed(false);
        }
        if (code == KEY_D || code == KEY_RIGHT_ARROW) {
            this.setRightPressed(false);
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isDebugText() {
        return debugText;
    }

    public void setDebugText(boolean debugText) {
        this.debugText = debugText;
    }
}