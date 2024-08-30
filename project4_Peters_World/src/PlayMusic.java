// Java program to play an Audio
// file using Clip Object
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.sound.sampled.*;


public class PlayMusic implements LineListener {

    public String filename;
    boolean musicAintPlaying;

    public PlayMusic(String filename) {

    }

    public void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                "Kane_Theme.mp3");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat audioFormat = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.addLineListener(this);
        audioClip.start();
    }

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            musicAintPlaying = true;
            System.out.println("Playback completed.");
        }
    }
}
