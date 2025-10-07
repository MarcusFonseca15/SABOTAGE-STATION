package jogo_de_trap;

import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Som {
    private Clip clip;

    public void tocar(String path, float volumeDb) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Volume
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volumeDb);

            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void parar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
