package tropico.Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;

public class MediaManagement {

    private static MediaPlayer MEDIA_SAVE;
    private static boolean MEDIA_MUTED = false;

    public static void setMedia(String name) {
        if(Objects.nonNull(MEDIA_SAVE)){
            MEDIA_SAVE.dispose();
        }
        URL musicURL = MediaManagement.class.getResource(name);
        Media media = new Media(musicURL.toExternalForm());
        MEDIA_SAVE = new MediaPlayer(media);
        MEDIA_SAVE.setMute(!SoundManagement.isSoundOn());
    }

    public static MediaPlayer getMedia() {
        return MEDIA_SAVE;
    }

    private static void switchMediaMuted(){
        MEDIA_MUTED = !MEDIA_MUTED;
    }

    public static void switchMute() {
        switchMediaMuted();
        MEDIA_SAVE.setMute(MEDIA_MUTED);
    }

    public static void dispose() {
        MEDIA_SAVE.dispose();
    }

    public static void play() {
        MEDIA_SAVE.play();
    }

    public static void setAutoReplay(double time) {
        MEDIA_SAVE.setOnEndOfMedia(() -> MEDIA_SAVE.seek(Duration.seconds(time)));
    }
}
