package tropico.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tropico.Object.Season;

import java.net.URL;
import java.util.*;

/**
 * manage the image used during the game
 * @author Cléis & Quentin
 */
public class ImageManagement {

    /**
     * map containing the tree of all season
     */
    private static final Map<Season, ArrayList<ImageView>> TREE_SAVE = new HashMap<>();
    /**
     * map containing all the other image
     */
    private static final Map<String, ArrayList<ImageView>> OTHER_SAVE = new HashMap<>();
    /**
     * map saving the element visible
     */
    private static final Map<String, ImageView> OTHER_SHOW = new HashMap<>();
    /**
     * Number of three in total
     */
    private static final int NUMBER_TREE = 5;

    /**
     * return list of the tree of the specified season or add it to the map and return it if the key isn't mapped
     * @param season
     *      season of the tree
     * @param pane
     *      pane where the image will be put (if need to instantiate it)
     * @return the list of image of tree of the specified season
     */
    public static List<ImageView> getTrees(Season season, AnchorPane pane){
        return get(TREE_SAVE, season, pane, NUMBER_TREE, "tree" + season);
    }

    /**
     * Returns the value to which the specified name is mapped, or add it to the map and return it
     * @param name
     *      name of the image
     * @param pane
     *      pane where the image will be put (if need to instantiate it)
     * @param number
     *      number of image needed
     * @return list to which the specified is mapped limited to number element
     */
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
        imageView.setVisible(false);
        return imageView;
    }

    /**
     *  create an image from the name of the image( image in Model folder)
     * @param name
     *      name of the image + extension
     * @return an instance Image of the image
     */
    public static Image createImage(String name){
        return new Image((ImageManagement.class.getResource(name)).toExternalForm());
    }

    private static ImageView createImage(String name, AnchorPane pane, double posX, double posY){
        URL imageURL = ImageManagement.class.getResource(name);
        Image image = new Image(imageURL.toExternalForm());
        ImageView imageView = new ImageView(image);
        AnchorPane.setLeftAnchor(imageView, posX);
        AnchorPane.setTopAnchor(imageView, posY);
        pane.getChildren().add(imageView);
        imageView.setVisible(false);
        return imageView;
    }

    /**
     * Returns the value to which the specified name is mapped, or null if this map contains no mapping for the key
     * @param name
     *      name of the image
     * @return the value to which the specified name is mapped, or null if this map contains no mapping for the key
     */
    public static ImageView getShow(String name){
        return OTHER_SHOW.getOrDefault(name, null);
    }

    /**
     * put in the map name as key and image as value (replace if the key already exist)
     * @param name
     *      name of the image
     * @param image
     *      image view
     */
    public static void putShow(String name, ImageView image){
        if(OTHER_SHOW.containsKey(name)){
            OTHER_SHOW.replace(name, image);
            return;
        }
        OTHER_SHOW.put(name, image);

    }

    /**
     * initialize position of "number" of the corn image and place them in a list
     * @param anchorPane
     *     pane where the image will be placed
     * @param number
     *      number of corn image needed
     * @return list of corn image
     */
    public static List<ImageView> getFarm(AnchorPane anchorPane, int number) {
        List<ImageView> corns= OTHER_SAVE.getOrDefault("corn", null);
        if(Objects.nonNull(corns))
            return corns;
        corns = new ArrayList<>();
        for (int i = 0; i < number; i++){
            ImageView corn = createImage("corn.png", anchorPane, 300 + i * 60, 450);
            corns.add(corn);
        }
        return corns;

    }
}
