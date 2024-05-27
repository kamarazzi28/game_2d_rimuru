package Model.Tile;

import java.awt.image.BufferedImage;

/**
 * The Tile class represents a single tile in the game world.
 * Each tile can have an image and can have collision properties.
 */
public class Tile {

    /** The image representing the tile. */
    public BufferedImage image;

    /** Indicates whether the tile has collision properties. */
    public boolean collision = false;
}
