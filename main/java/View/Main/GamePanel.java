package View.Main;

import Controller.Event.Event;
import Controller.Handler.KeyHandler;
import Controller.Utility.AllSetter;
import Controller.Utility.CollisionChecker;
import Controller.Utility.Sound;
import Model.Data.SaveLoad;
import Model.Entity.Entity;
import Model.Entity.Player;
import Model.Objects.SuperObject;
import Model.Tile.TileManager;
import View.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());
    final int originalTileSize = 16;
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;
    public final int screenWidth = 768;
    public final int screenHeight = 576;

    // World settings
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 30;
    private final AllSetter allSetter;

    // FPS settings
    int FPS = 60;

    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound music = new Sound();
    Sound se = new Sound();
    public UI ui = new UI(this);
    public Event event = new Event(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    Thread gameThread;

    // Entities and objects
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);
    public SuperObject[] object = new SuperObject[100];
    public Entity[] npc = new Entity[10];
    public Entity[] enemy = new Entity[50];

    // Game states
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int levelPassed = 7;

    /**
     * Constructor for GamePanel. Sets the preferred size, background color, and key listener.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.keyH);
        this.setFocusable(true);
        this.allSetter = AllSetter.getInstance(this); // Initialize AllSetter using the Singleton pattern
        logger.info("GamePanel initialized");
    }

    /**
     * Sets up the game by initializing objects, NPCs, and enemies.
     */
    public void setupGame() {
        allSetter.setObject();
        allSetter.setNPC();
        allSetter.setEnemy();
        gameState = titleState;
        logger.info("Game setup complete");
    }

    /**
     * Resets the game to the default state. If retryOrNextLevel is true, resets player position and life.
     *
     * @param retryOrNextLevel If true, resets player position and life; if false, resets game to default values.
     */
    public void resetGame(boolean retryOrNextLevel) {
        stopMusic();
        if (retryOrNextLevel) {
            player.setDefaultPositions();
            player.restoreLife();
            allSetter.setEnemy();
            allSetter.setNPC();
        } else { // menu
            player.setDefaultVales();
            allSetter.setObject();
        }
        playMusic(1);
        logger.info("Game reset: " + (retryOrNextLevel ? "retry/next level" : "menu"));
    }

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
        logger.info("Game thread started");
    }

    /**
     * Runs the game loop, updating and repainting the game at the specified FPS.
     */
    public void run() {
        double drawInterval = 1.0E9 / (double) this.FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long timer = 0L;
        int drawCount = 0;

        while (this.gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (double) (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1.0) {
                this.update();
                this.repaint();
                --delta;
                ++drawCount;
            }
            if (timer >= 1000000000L) {
                logger.info("FPS:" + drawCount);
                drawCount = 0;
                timer = 0L;
            }
        }
    }

    /**
     * Updates the game state, player, NPCs, and enemies based on the current game state.
     */
    public void update() {
        if (gameState == playState) {
            player.update();
            for (Entity value : npc) {
                if (value != null) {
                    value.update();
                }
            }
            for (int i = 0; i < enemy.length; i++) {
                if (enemy[i] != null) {
                    if (enemy[i].alive && !enemy[i].dying) {
                        enemy[i].update();
                    }
                    if (!enemy[i].alive) {
                        enemy[i] = null;
                    }
                }
            }
        }
        if (gameState == pauseState) {
            logger.info("Game is paused");
        }
    }

    /**
     * Paints the components of the game, including the player, NPCs, enemies, and UI elements.
     *
     * @param g The graphics context to use for painting.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.isDebugText()) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileManager.draw(g2);
            for (SuperObject superObject : object) {
                if (superObject != null) {
                    superObject.draw(g2, this);
                }
            }
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            for (Entity entity : enemy) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            player.draw(g2);
            ui.draw(g2);
        }

        if (keyH.isDebugText()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.CYAN);
            int x = 20;
            int y = 400;
            int lineHeight = 30;

            g2.drawString("WorldX: " + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY: " + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Draw Time: " + passed + " ns", x, y);
        }

        g2.dispose();
    }

    /**
     * Plays the specified music file.
     *
     * @param i The index of the music file to play.
     */
    public void playMusic(int i) {
        music.stop();
        music.setFile(i);
        music.play();
        music.loop();
        logger.info("Playing music file: " + i);
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic() {
        music.stop();
        logger.info("Music stopped");
    }

    /**
     * Plays the specified sound effect file.
     *
     * @param i The index of the sound effect file to play.
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
        logger.info("Playing sound effect file: " + i);
    }
}
