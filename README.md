## Reincarnation into Slime: The Adventures of Rimuru

### Overview
"Reincarnation into Slime: The Adventures of Rimuru" is a 2D adventure game where the player controls Rimuru, navigating through various levels, interacting with NPCs, battling enemies, and collecting items to achieve the ultimate objective. The game is built using Java, leveraging Java Swing for the UI and game graphics, and includes features like collision detection, sound management, and game state handling.

### Project Structure
The project is organized into several packages, each containing classes with specific responsibilities:

#### Controller
- **Event:** Handles game events and interactions.
- **Handler:** Manages user input through key events.
- **Utility:** Provides utility functions like setting game objects, collision checking, and sound management.

#### Model
- **Data:** Manages data-related functionalities such as saving and loading game states.
- **Entity:** Represents game entities such as the player and NPCs.
- **Enemy:** Represents enemy characters.
- **Objects:** Represents game objects like keys, chests, and herbs.
- **Tile:** Manages the tile-based game environment.

#### View
- **Main:** Contains the main game panel class that integrates all components and manages the game loop.
- **UI:** Manages user interface elements such as menus, dialogues, and HUD.

### Detailed Class Description

#### Controller Package

##### Event
- **Event**
  - Manages in-game events, such as interactions with specific tiles and objects.
  - Attributes: gp, eventRect, prevEventX, prevEventY, canTouchEvent.
  - Key Methods:
    - `checkEvent()`: Checks and handles events based on player's position.
    - `hitSalt(int col, int row, String reqDirection)`: Checks if the player hits a salt tile.
    - `damageSalt(int col, int row, int gameState)`: Damages the player when a salt tile is hit.

- **EventRect**
  - Defines event areas by extending Rectangle.
  - Attributes: eventRectDefaultX, eventRectDefaultY, eventDone.

##### Handler
- **KeyHandler**
  - Implements KeyListener to manage key events.
  - Attributes: gp, upPressed, downPressed, leftPressed, rightPressed, enterPressed, debugText.
  - Key Methods:
    - `keyPressed(KeyEvent e)`: Handles key press events based on the game state.
    - `keyReleased(KeyEvent e)`: Handles key release events.
    - State-specific methods: `titleState(int code)`, `playState(int code)`, `pauseState(int code)`, `dialogueState(int code)`, etc.

##### Utility
- **AllSetter**
  - Responsible for initializing and placing game objects, NPCs, and enemies.
  - Attributes: gp.
  - Key

