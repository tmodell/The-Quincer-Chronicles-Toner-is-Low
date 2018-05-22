//Controls sound and audio output

package sound;

import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;

/*
Implementation instructions
If music is already playing, must run stopAudio otherwise stream control will be lost
If music has stopped, must reinitialize the stream
running track set method will set the track and play the music
stopAudio will pause the stream, startAudio will resume/play the stream
*/

public class MusicController {
    
    public static File Sound;
    public static Clip clip;
    
    public static void changeMusic(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(playMe));
        clip.start();//Play Audio
        //Thread.sleep(clip.getMicrosecondLength()); //Only one at a time
    }
    
    public static void startAudio() {
        clip.start(); //Play/Resume Audio
    }
    
    public static void stopAudio() {
        clip.stop(); //Pause Audio
    }
    
    public static void trackOne() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackOne.wav");
        changeMusic(Sound);
    }
    
    public static void trackTwo() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackTwo.wav");
        changeMusic(Sound);
    }
    
    public static void trackThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/Music/trackThree.wav");
        changeMusic(Sound);
    }
    
    //https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
}