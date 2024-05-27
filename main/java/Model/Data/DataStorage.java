package Model.Data;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Class to store player data.
 */
public class DataStorage implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DataStorage.class.getName());

    // PLAYER STATS
    /** The level passed by the player. */
    int levelPassed;

    /** The maximum life of the player. */
    int maxLife;

    /** The current life of the player. */
    int life;

    /** The experience points of the player. */
    int exp;

    /** The strength of the player. */
    int strength;

    /** The experience points required for the next level. */
    int nextLevelExp;
}
