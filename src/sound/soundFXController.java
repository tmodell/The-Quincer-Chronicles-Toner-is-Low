//Controls sound and audio output

package sound;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Random;
import javax.sound.sampled.*;

public class soundFXController {
    
    public File Sound;
    public Clip clip;
    
    public Random rand;
    
    public void changeFX(File playMe) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
    //Change clip binding and play audio 
    //Do not call me manually, unless you know what you are doing
        clip = AudioSystem.getClip(); //initialize clip
        clip.open(AudioSystem.getAudioInputStream(playMe)); //Get audio stream and bind to clip
        clip.start(); //Play clip (sound)
    }
    
    public void startAudio() {
        clip.start(); //Play clip
    }
    
    public void swordDrawFX() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        switch (rand.nextInt(3)) { //Random clip from 0-2 (3 options)
            case 0:
                Sound = new File("./src/Sound/FX/swordDrawOne.wav"); //Clip one
            case 1:
                Sound = new File("./src/Sound/FX/swordDrawTwo.wav"); //Clip two
            case 2:
                Sound = new File("./src/Sound/FX/swordDrawThree.wav"); //Clip three
        }
        changeFX(Sound); //Change clip and play sound
    }
    
    public void effectOne() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/FX/effectOne.wav");
        changeFX(Sound);
    }
    
    public void effectTwo() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/FX/effectTwo.wav");
        changeFX(Sound);
    }

    public void effectThree() throws UnsupportedAudioFileException, LineUnavailableException, IOException, MalformedURLException, InterruptedException {
        Sound = new File("./src/Sound/FX/effectThree.wav");
        changeFX(Sound);
    }
    
}
