package tropico.Model;

public class SoundManagement {

    private static boolean SOUND_ON = true;

    public static boolean isSoundOn(){
        return SOUND_ON;
    }

    public static void switchSoundOn(){
        SOUND_ON = !SOUND_ON;
    }
}
