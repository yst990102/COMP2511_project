package unsw.loopmania.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import unsw.loopmania.model.Character;
import unsw.loopmania.model.PathTile;
import unsw.loopmania.model.buildings.HeroCastle;
import unsw.loopmania.model.Entity;

/**
 * A LoopManiaLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * this should NOT be used to load any entities which spawn, or might be removed
 * (use controller for that) since this doesnt add listeners or teardown
 * functions (so it will be very hacky to remove event handlers)
 */
public class LoopManiaWorldControllerLoader extends LoopManiaWorldLoader {

    private List<ImageView> entities;

    // Images
    private Image characterImage;
    private Image pathTilesImage;
    private Image heroCastleImage;
    private Image forestTileImage;
    private Image iceTileImage;
    private Image desertTileImage;


    public LoopManiaWorldControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        characterImage = new Image((new File("src/assets/human_new.png")).toURI().toString());
        pathTilesImage = new Image((new File("src/assets/32x32GrassAndDirtPath.png")).toURI().toString());
        heroCastleImage = new Image((new File("src/assets/hero_castle.png")).toURI().toString());
        forestTileImage = new Image((new File("src/assets/32x32GrassAndDirtPath.png")).toURI().toString());
        iceTileImage = new Image((new File("src/assets/32x32SnowAndIcePath.png")).toURI().toString());
        desertTileImage = new Image((new File("src/assets/32x32SandAndStonePath.png")).toURI().toString());
    }

    // TODO = load more entity types from the file
    @Override
    public void onLoad(HeroCastle heroCastle, List<ImageView> entityImages) {
        ImageView view = new ImageView(heroCastleImage);
        addEntity(heroCastle, view, entityImages);
    }

    @Override
    public void onLoad(Character character, List<ImageView> entityImages) {
        ImageView view = new ImageView(characterImage);
        addEntity(character, view, entityImages);
    }

    /**
     * load path tile ImageView based on configuration in file. Note how
     * src/images/32x32GrassAndDirtPath.png has 8 images within it x and y values we
     * produce here are the coordinates of the top left of our sub-image, taken from
     * the top-left of the pathTilesImage
     */
    @Override
    public void onLoad(PathTile pathTile, PathTile.Direction into, PathTile.Direction out, MAP_TYPE type, List<ImageView> entityImages) {
        // note https://stackoverflow.com/a/58041962
        // we need to find the offset within the rectangle, we can do this from
        // adjacencies
        ImageView view = new ImageView(pathTilesImage);
        switch (type) {
            case FOREST:
                view = new ImageView(forestTileImage);
                break;
            case ICEWORLD:
                view = new ImageView(iceTileImage);
                break;
            case DESERT:
                view = new ImageView(desertTileImage);
                break;
            default:
                break;
        }

        int x;
        int y;

        if (into == out && (into == PathTile.Direction.DOWN || into == PathTile.Direction.UP)) {
            // vertical, no corners
            x = 32;
            y = 0;
        } else if ((into == PathTile.Direction.DOWN && out == PathTile.Direction.RIGHT)
                || (into == PathTile.Direction.LEFT && out == PathTile.Direction.UP)) {
            // corner piece, turning left up, or turning down right
            x = 0;
            y = 64;
        } else if ((into == PathTile.Direction.UP && out == PathTile.Direction.RIGHT)
                || (into == PathTile.Direction.LEFT && out == PathTile.Direction.DOWN)) {
            // corner piece, turning up right, or turning left down
            x = 32;
            y = 32;
        } else if ((into == PathTile.Direction.DOWN && out == PathTile.Direction.LEFT)
                || (into == PathTile.Direction.RIGHT && out == PathTile.Direction.UP)) {
            // corner piece, turning down left, or turning right up
            x = 32;
            y = 64;
        } else if ((into == PathTile.Direction.RIGHT && out == PathTile.Direction.DOWN)
                || (into == PathTile.Direction.UP && out == PathTile.Direction.LEFT)) {
            // corner piece, turning right down, or turning up left
            x = 0;
            y = 32;
        } else if (into == out && (into == PathTile.Direction.LEFT || into == PathTile.Direction.RIGHT)) {
            // horizontal, no corners
            x = 0;
            y = 96;
        } else {
            throw new RuntimeException("Not a valid adjacency mapping " + into + "->" + out);
        }
        Rectangle2D imagePart = new Rectangle2D(x, y, 32, 32);
        view.setViewport(imagePart);
        addEntity(pathTile, view, entityImages);
    }

    /**
     * pair the backendentity and view, so the view tracks the coordinates of the
     * entity
     * 
     * @param entity backend entity
     * @param view   frontend image to be paired with the backend entity
     */
    private void addEntity(Entity entity, ImageView view, List<ImageView> entityImages) {
        trackPositionOfNonSpawningEntities(entity, view);
        entityImages.add(view);
    }

    /**
     * Track the position of entities which don't spawn or require removal. We only
     * setup the node to follow the coordinates of the backend entity.<br>
     * Items which potentially need to be removed should be spawned by controller,
     * and have listener handles and teardown functions added.
     * 
     * @param entity backend entity
     * @param node   frontend image to track the coordinates of the backend entity
     */
    private static void trackPositionOfNonSpawningEntities(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });

    }

    /**
     * Create a controller that can be attached to the LoopManiaView with all the
     * loaded entities.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public LoopManiaWorldController loadController() throws FileNotFoundException {
        return new LoopManiaWorldController(load(), entities);
    }

}
