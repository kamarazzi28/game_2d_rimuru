package Controller.Utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Sound class is responsible for managing audio playback in the game.
 * It supports loading, playing, looping, stopping, and adjusting the volume of sound clips.
 */
public class Sound {

    private static final Logger logger = Logger.getLogger(Sound.class.getName());
    Clip clip;
    URL[] soundURL = new URL[30];
    public int volumeScale = 3;
    float volume = 0.5f;

    /**
     * Constructs a new Sound object and initializes the sound URLs.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/Sound/Rimuru_Quote.wav");
        soundURL[1] = getClass().getResource("/Sound/fading_theme.wav");
        soundURL[2] = getClass().getResource("/Sound/recieveddamage.wav");
        soundURL[3] = getClass().getResource("/Sound/hitdamage.wav");
        soundURL[4] = getClass().getResource("/Sound/level_up.wav");
        soundURL[5] = getClass().getResource("/Sound/hipokute.wav");
        soundURL[6] = getClass().getResource("/Sound/full_hearts.wav");
        soundURL[7] = getClass().getResource("/Sound/gameover.wav");
        soundURL[8] = getClass().getResource("/Sound/gamefinished.wav");
    }

    /**
     * Loads the sound file at the specified index.
     *
     * @param i the index of the sound file to load
     */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            checkVolume();
            logger.log(Level.INFO, "Sound file at index {0} loaded successfully.", i);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading sound file at index " + i, e);
        }
    }

    /**
     * Starts playback of the loaded sound clip.
     */
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    /**
     * Loops the playback of the loaded sound clip continuously.
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops playback of the sound clip.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Sets the volume of the sound clip.
     *
     * @param volume the volume level to set (between 0.0 and 1.0)
     */
    public void setVolume(float volume) {
        this.volume = volume;
        if (clip != null && clip.isOpen()) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    /**
     * Adjusts the volume based on the current volume scale.
     */
    public void checkVolume() {
        setVolume(volumeScale / 5.0f);
    }
}
