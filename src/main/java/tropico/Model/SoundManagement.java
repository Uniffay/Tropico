package tropico.Model;

/**
 * manage sound of the media used
 * @author Cléis & Quentin
 */
public class SoundManagement {

    /**
     * true if the sound is on and false otherwise
     */
    private static boolean SOUND_ON = true;

    /**
     * test if the sound is on
     * @return true if the sound is on and false otherwise
     */
    public static boolean isSoundOn(){
        return SOUND_ON;
    }

    /**
     * switch the sound value and set mute of the media
     */
    public static void switchSoundOn(){
        SOUND_ON = !SOUND_ON;
        MediaManagement.setMute(SOUND_ON);
    }
}
