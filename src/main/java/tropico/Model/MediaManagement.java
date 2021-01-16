package tropico.Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;

/**
 * manage the media used in the app
 * @author Cléis & Quentin
 */
public class MediaManagement {
    /**
     * media currently saved
     */
    private static MediaPlayer MEDIA_SAVE;

    /**
     * set the current media player
     * @param name
     *      name of the media
     */
    public static void setMedia(String name) {
        if(Objects.nonNull(MEDIA_SAVE)){
            MEDIA_SAVE.dispose();
        }
        URL musicURL = MediaManagement.class.getResource(name);
        Media media = new Media(musicURL.toExternalForm());
        MEDIA_SAVE = new MediaPlayer(media);
        MEDIA_SAVE.setMute(SoundManagement.isSoundOff());
    }

    /**
     * get the current media player
     * @return the current media player
     */
    public static MediaPlayer getMedia() {
        return MEDIA_SAVE;
    }

    /**
     * set sound of the media to off or on
     */
    public static void setMute(boolean mute) {
        MEDIA_SAVE.setMute(mute);
    }

    /**
     * dispose of the media
     */
    public static void dispose() {
        MEDIA_SAVE.dispose();
    }

    /**
     * play the media
     */
    public static void play() {
        MEDIA_SAVE.play();
    }

    /**
     * add auto replay to the media by restarting it at time
     * @param time
     *      time of the video when video replay
     */
    public static void setAutoReplay(double time) {
        MEDIA_SAVE.setOnEndOfMedia(() -> MEDIA_SAVE.seek(Duration.seconds(time)));
    }
}
