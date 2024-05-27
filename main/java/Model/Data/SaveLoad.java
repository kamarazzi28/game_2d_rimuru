package Model.Data;

import View.Main.GamePanel;

import java.io.*;

/**
 * Class to handle saving and loading game data.
 */
public class SaveLoad {
    private GamePanel gp;

    /**
     * Constructor for SaveLoad class.
     * @param gp The GamePanel instance.
     */
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Saves the game data to a file.
     */
    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            DataStorage ds = new DataStorage();
            ds.levelPassed = gp.player.getLevel();
            ds.maxLife = gp.player.getMaxLife();
            ds.life = gp.player.getLife();
            ds.exp = gp.player.getExp();
            ds.strength = gp.player.getStrength();
            ds.nextLevelExp = gp.player.getNextLevelExp();

            oos.writeObject(ds);
            oos.close(); // Close the ObjectOutputStream after writing

        } catch (IOException e) {
            System.out.println("Save exception: " + e.getMessage());
        }
    }

    /**
     * Loads the game data from a file.
     */
    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            DataStorage ds = (DataStorage) ois.readObject();

            gp.player.setLevel(ds.levelPassed);
            gp.player.setMaxLife(ds.maxLife);
            gp.player.setExp(ds.exp);
            gp.player.setLife(ds.life);
            gp.player.setNextLevelExp(ds.nextLevelExp);
            gp.player.setStrength(ds.strength);

            ois.close(); // Close the ObjectInputStream after reading

        } catch (IOException e) {
            System.out.println("Load exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
