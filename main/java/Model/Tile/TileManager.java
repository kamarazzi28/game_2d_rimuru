package Model.Tile;

import View.Main.GamePanel;
import Controller.Utility.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * Manages the tiles in the game, including loading tile images and drawing tiles.
 */
public class TileManager {

    private static final Logger logger = Logger.getLogger(TileManager.class.getName());

    /** The game panel. */
    public GamePanel gp;

    /** Array of tiles. */
    public Tile[] tile;

    /** 2D array representing the map with tile numbers. */
    public int[][] mapTileNum;

    /**
     * Constructs a TileManager for managing the tiles in the game.
     *
     * @param gp the game panel
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[40];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("resources/Maps/world_map.txt");
    }

    /**
     * Loads the tile images and initializes their collision properties.
     */
    public void getTileImage() {
        // Placeholder tile setups
        setup(0, "road_0", false);
        setup(1, "road_vertical_1", false);
        setup(2, "road_horizontal_2", false);
        setup(3, "road_upC_3", false);
        setup(4, "road_leftC_4", false);
        setup(5, "road_downC_5", false);
        setup(6, "road_rightC_6", false);
        setup(7, "grass_7", false);
        setup(8, "water_8", false);
        setup(9, "wall_9", false);

        setup(10, "road_10", false);
        setup(11, "road_vertical_11", false);
        setup(12, "road_horizontal_12", false);
        setup(13, "road_upC_13", false);
        setup(14, "road_leftC_14", false);
        setup(15, "road_downC_15", false);
        setup(16, "road_rightC_16", false);
        setup(17, "grass_17", false);
        setup(18, "water_18", true);
        setup(19, "wall_19", true);
        setup(20, "tree_20", true);
        setup(21, "cave_21", false);
        setup(22, "cave_vertical_22", false);
        setup(23, "cave_horizontal_23", false);
        setup(24, "cave_leftC_24", false);
        setup(25, "cave_rigthC_25", false);
        setup(26, "cave_upC_26", false);
        setup(27, "cave_downC_27", false);
        setup(28, "hole_28", false);
        setup(29, "wall_29", true);
    }

    /**
     * Sets up a tile with the specified parameters.
     *
     * @param i the index of the tile
     * @param imagePath the path to the image file
     * @param collision whether the tile has collision properties
     */
    public void setup(int i, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[i] = new Tile();
            tile[i].image = ImageIO.read(new FileInputStream("resources/Images/Tiles/" + imagePath + ".png"));
            tile[i].image = uTool.scaleImage(tile[i].image, gp.tileSize, gp.tileSize);
            tile[i].collision = collision;
        } catch (IOException e) {
            logger.severe("Error loading tile image: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    /**
     * Loads the map from a text file.
     *
     * @param filePath the path to the map file
     */
    public void loadMap(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
            logger.info("Map loaded successfully from " + filePath);
        } catch (Exception e) {
            logger.severe("Error loading map: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    /**
     * Draws the tiles on the screen based on the player's position.
     *
     * @param g2 the graphics context
     */
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.screenWidth / 2;
            int screenY = worldY - gp.player.worldY + gp.screenHeight / 2;

            if (worldX + gp.tileSize * 2 > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize * 2 > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
