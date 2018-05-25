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
loop must be stopped else stream control will be lost
If music has stopped, must reinitialize the stream
running track set method will set the track and play the music
stopAudio will pause the stream, startAudio will resume/play the stream, 
reStartAudio will restart the audio stream
looping is supported and enabled by default (only for music)
startLooping will cause the music to loop, and restart if too much time has elasped
stoplooping will stop the music from looping
*/

public class MusicController {
    
    public static File Sound;
    public static Clip clip;
    public static Timer looper;
    
    public static void changeMusic(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(playMe));
        clip.start();//Play Audio
        looper = new Timer(((int) (clip.getMicrosecondLength()/1000)), listener);
        looper.start();
    }
    
    public static void startAudio() {
        clip.start(); //Play/Resume Audio
    }
    
    public static void stopAudio() {
        clip.stop(); //Pause Audio
    }
    
    public static void reStartAudio() {
        clip.setMicrosecondPosition(0);
        clip.start();
    }
    
    public static void startLooping() {
        if (clip.getMicrosecondPosition() > 3000000)
            clip.setMicrosecondPosition(0);
        looper.start();
    }
    
    public static void stopLooping() {
        looper.stop();
    }
    
    public static void trackOne() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        //Main Menu Music
        Sound = new File("./src/Sound/Music/trackOne.wav");
        changeMusic(Sound);
    }
    
    public static void trackTwo() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        //Traveling Music
        Sound = new File("./src/Sound/Music/trackTwo.wav");
        changeMusic(Sound);
    }
    
    public static void trackThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        //Village Music
        Sound = new File("./src/Sound/Music/trackThree.wav");
        changeMusic(Sound);
    }
    
    public static void trackFour() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        //Dungeon Music
        Sound = new File("./src/Sound/Music/trackThree.wav");
        changeMusic(Sound);
    }
    
    public static ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event){
            reStartAudio();
        }
    };
    
    //https://stackoverflow.com/questions/557903/how-can-i-wait-for-a-java-sound-clip-to-finish-playing-back
}