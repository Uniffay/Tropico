package tropico.Model;

import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Represents the snow in winter
 * @author Quentin & Cléis
 */
public class Snow {
    /**
     * Image shown in the game
     */
    private final ImageView imageView;
    /**
     * speed of the snow
     */
    private final int speed;

    public Snow(ImageView imageView) {
        Random r = new Random();
        this.imageView = imageView;
        speed = r.nextInt(4) + 4;
    }

    /**
     * get image shown
     * @return image shown
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * get speed of the snow
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }
}
