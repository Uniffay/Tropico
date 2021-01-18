package tropico.Model;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Snow {

    private final ImageView imageView;
    private final int speed;

    public Snow(ImageView imageView) {
        Random r = new Random();
        this.imageView = imageView;
        speed = r.nextInt(8) + 1;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getSpeed() {
        return speed;
    }
}
