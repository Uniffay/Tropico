package tropico.Model;

/**
 * manage sound of the media used
 * @author Cléis & Quentin
 */
public class SoundManagement {

    /**
     * true if the sound is off and false otherwise
     */
    private static boolean SOUND_OFF = false;

    /**
     * test if the sound is on
     * @return true if the sound is on and false otherwise
     */
    public static boolean isSoundOff(){
        return SOUND_OFF;
    }

    /**
     * switch the sound value and set mute of the media
     */
    public static void switchSound(){
        SOUND_OFF = !SOUND_OFF;
        MediaManagement.setMute(SOUND_OFF);
    }
}
