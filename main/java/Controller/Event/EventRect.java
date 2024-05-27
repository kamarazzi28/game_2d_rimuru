package Controller.Event;

import java.awt.*;

/**
 * Represents an event rectangle in the game world.
 * This rectangle is used to trigger specific events when the player intersects it.
 */
public class EventRect extends Rectangle {
    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;
}
