//Controls sound and audio output

package sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.Timer;

/*
Implementation instructions
If music is already playing, must run stopAudio otherwise stream control will be lost
If music has stopped, must reinitialize the stream
running track set method will set the track and play the music
stopAudio will pause the stream, startAudio will resume/play the stream
*/

public class MusicController {
    
    public static Clip clip;
    public static Timer looper;
    
    public static int currentTrack;
    
    public static long[] time = new long[] {0, 0, 0, 0};
    public static File[] music = {new File("./src/Sound/Music/trackOne.wav"), new File("./src/Sound/Music/trackTwo.wav"), 
        new File("./src/Sound/Music/trackThree.wav"), new File("./src/Sound/Music/trackFour.wav")};
    
    public static void changeMusic(int trackNum) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        currentTrack = trackNum-1;
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(music[currentTrack]));
        clip.setMicrosecondPosition(time[currentTrack]);
        clip.start();//Play Audio
        startLooping();
    }
    
    public static void startAudio() {
        clip.start(); //Play/Resume Audio
        startLooping();
    }
    
    public static void stopAudio() {
        clip.stop(); //Pause Audio
        time[currentTrack] = clip.getMicrosecondPosition();
        looper.stop();
    }
    
    public static void reStartAudio(int trackNum) {
        clip.setMicrosecondPosition(0);
        time[currentTrack] = 0;
        clip.start();
    }
    
    public static void startLooping() {
        if (clip.getMicrosecondPosition() > 1000000)
            clip.setMicrosecondPosition(0);
        looper = new Timer(((int) (clip.getMicrosecondLength()/1000)), listener);
        looper.start();
    }
    
    public static ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event){
        clip.setMicrosecondPosition(0);
        clip.start();
        }
    };
    
    //https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
}