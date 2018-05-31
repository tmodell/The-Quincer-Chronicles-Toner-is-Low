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
    
    public static Clip clip;
    
    public static int currentTrack;
    
    public static long[] time = new long[] {0, 0, 0, 0};
    public static File[] music = {new File("lib/sound/music/trackOne.wav"), new File("lib/sound/music/trackTwo.wav"), 
        new File("lib/sound/music/trackThree.wav"), new File("lib/sound/music/trackFour.wav")};
    
    public static void changeMusic(int trackNum) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        currentTrack = trackNum-1;
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(music[currentTrack]));
        clip.setMicrosecondPosition(time[currentTrack]);
        clip.start();//Play Audio
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public static void startAudio() {
        clip.start(); //Play/Resume Audio
    }
    
    public static void stopAudio() {
        time[currentTrack] = clip.getMicrosecondPosition();
        clip.stop(); //Pause Audio
        //looper.stop();
    }
    
    public static void reStartAudio(int trackNum) {
        clip.setMicrosecondPosition(0);
        time[currentTrack] = 0;
        clip.start();
    }
    
    public static void reSetAllMusic() {
        time[0] = 0;
        time[1] = 0;
        time[2] = 0;
        time[3] = 0;
        reStartAudio(currentTrack);
    }
}
