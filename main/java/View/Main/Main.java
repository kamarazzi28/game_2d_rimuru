package View.Main;

import javax.swing.*;

/**
 * The entry point for the game.
 */
public class Main {

    /**
     * Main method, starts the game.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Set the icon for the window
        ImageIcon image = new ImageIcon("resources/Images/Other/icon_image.png");

        // Create the main window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        window.setResizable(false); // Disable window resizing
        window.setIconImage(image.getImage()); // Set the window icon
        window.setTitle("Reincarnation into slime: The Adventures of Rimuru"); // Set the window title

        // Create the game panel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // Add the game panel to the window
        window.pack(); // Resize the window to fit the preferred size of its subcomponents
        window.setLocationRelativeTo(null); // Center the window on the screen

        window.setVisible(true); // Make the window visible
        gamePanel.setupGame(); // Set up the game
        gamePanel.startGameThread(); // Start the game thread
    }
}
