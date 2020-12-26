package tropico.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tropico.Object.Season;

import java.net.URL;
import java.util.*;

public class ImageManagement {

    private static final Map<Season, ArrayList<ImageView>> TREE_SAVE = new HashMap<>();
    private static final Map<String, ArrayList<ImageView>> OTHER_SAVE = new HashMap<>();
    private static final int NUMBER_TREE = 5;

    public static List<ImageView> getTrees(Season season, AnchorPane pane){
        return get(TREE_SAVE, season, pane, NUMBER_TREE, "tree" + season);
    }

    public static List<ImageView> getOthers(String name, AnchorPane pane, int number) {
        return get(OTHER_SAVE, name, pane, number, name);
    }

    private static <E> List<ImageView> get(Map<E, ArrayList<ImageView>> map, E name, AnchorPane pane, int number, String fileName){
        ArrayList<ImageView> images = map.get(name);
        if(Objects.nonNull(images)){
            return images;
        }
        images = new ArrayList<>();
        map.put(name, images);
        for (int i = 0; i < number; i++){
            images.add(createImage(fileName + (i + 1) + ".png", pane));
        }
        return images;
    }

    private static ImageView createImage(String name, AnchorPane pane){
        URL imageURL = ImageManagement.class.getResource(name);
        Image image = new Image(imageURL.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(750);
        imageView.setFitWidth(1050);
        pane.getChildren().add(imageView);
        return imageView;
    }


}
