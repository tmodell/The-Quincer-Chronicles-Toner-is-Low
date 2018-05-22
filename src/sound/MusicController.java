//Controls sound and audio output

package sound;

import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;

public class MusicController {
    
    public File Sound;
    public Clip clip;
    
    public void changeMusic(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(playMe));
        clip.start();//Play Audio
        //Thread.sleep(clip.getMicrosecondLength()); //Only one at a time
    }
    
    public void startAudio() {
        clip.start(); //Play/Resume Audio
    }
    
    public void stopAudio() {
        clip.stop(); //Pause Audio
    }
    
    public void trackOne() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackOne.wav");
        changeMusic(Sound);
    }
    
    public void trackTwo() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackTwo.wav");
        changeMusic(Sound);
    }
    
    public void trackThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackThree.wav");
        changeMusic(Sound);
    }
    
    //https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
}
