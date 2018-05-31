//Controls sound and audio output

package sound;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.sound.sampled.*;

public class SoundFXController {
    
    public static File Sound;
    public static Clip clip;

    public static void changeFX(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
    //Change clip binding and play audio 
    //Do not call me manually, unless you know what you are doing
        clip = AudioSystem.getClip(); //initialize clip
        clip.open(AudioSystem.getAudioInputStream(playMe)); //Get audio stream and bind to clip
        clip.start(); //Play clip (sound)
    }
    
    public static void startAudio() {
        clip.start(); //Play clip
    }
    
    public static void swordFX() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        switch (ThreadLocalRandom.current().nextInt(0, 2)) { //Random clip from 0-1 (2 options)
            case 0:
                Sound = new File("./lib/sound/fx/swordOne.wav"); //Clip one
                break;
            case 1:
                Sound = new File("./lib/sound/fx/swordTwo.wav"); //Clip two
                break;
        }
        changeFX(Sound); //Change clip and play sound
    }
    
    public static void shieldFX() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./lib/sound/fx/shieldOne.wav");
        changeFX(Sound);
    }
    
    public static void mapMoveFX() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./lib/sound/fx/mapMoveOne.wav");
        changeFX(Sound);
    }

    public static void effectThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./lib/sound/fx/effectThree.wav");
        changeFX(Sound);
    }
    
}
