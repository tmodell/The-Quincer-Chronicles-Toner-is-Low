//Controls sound and audio output

package sound;

import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;

public class SoundController {
    
    public File Sound;
    public Clip clip;
    
    public void soundController() throws LineUnavailableException {
        clip = AudioSystem.getClip();
    }
    
    public void changeAudio(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
            clip.open(AudioSystem.getAudioInputStream(playMe));
            clip.start();
            //Thread.sleep(clip.getMicrosecondLength()); //Only one at a time
    }
    
    public void startAudio() {
        clip.start();
    }
    
    public void stopAudio() {
        clip.stop();
    }
    
    public void trackOne() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
         Sound = new File("./src/Sound/trackOne.wav");
         changeAudio(Sound);
    }
    
    public void trackTwo() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
         Sound = new File("./src/Sound/trackTwo.wav");
         changeAudio(Sound);
    }
    
    public void trackThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
         Sound = new File("./src/Sound/trackThree.wav");
         changeAudio(Sound);
    }
    
}
