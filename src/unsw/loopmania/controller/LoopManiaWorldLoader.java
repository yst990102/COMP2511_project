package unsw.loopmania.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

import unsw.loopmania.model.LoopManiaWorld;
import unsw.loopmania.model.Entity;
import unsw.loopmania.model.PathTile;
import unsw.loopmania.model.buildings.HeroCastle;
import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.Character;

/**
 * Loads a world from a .json file.
 * 
 * By extending this class, a subclass can hook into entity creation.
 * This is useful for creating UI elements with corresponding entities.
 * 
 * this class is used to load the world.
 * it loads non-spawning entities from the configuration files.
 * spawning of enemies/cards must be handled by the controller.
 */
public abstract class LoopManiaWorldLoader {
    public enum MAP_TYPE {
        FOREST, ICEWORLD, DESERT
    }

    private JSONObject json;

    public LoopManiaWorldLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("worlds/" + filename)));
    }

    public LoopManiaWorldLoader() {
    }

    /**
     * Parses the JSON to create a world.
     */
    public LoopManiaWorld load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        // initial path is empty, path will be loaded after selecting map
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();

        JSONObject goalObject = json.getJSONObject("goal-condition");

        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath, goalObject);
        world.setInitialPath(json.getJSONObject("path"));
        world.setInitialEntities(json.getJSONArray("entities"));

        return world;
    }

    /**
     * load an entity into the world
     * @param world backend world object
     * @param json a JSON object to parse (different from the )
     * @param orderedPath list of pairs of x, y cell coordinates representing game path
     */
    public void loadEntity(LoopManiaWorld world, JSONObject currentJson, List<Pair<Integer, Integer>> orderedPath,
            List<ImageView> entityImages) {
        String type = currentJson.getString("type");
        int x = currentJson.getInt("x");
        int y = currentJson.getInt("y");
        int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));
        assert indexInPath != -1;

        // TODO = load more entity types from the file
        switch (type) {
        case "HeroCastle":
            Character character = new Character(new PathPosition(indexInPath, orderedPath));
            HeroCastle heroCastle = new HeroCastle(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            onLoad(character, entityImages);
            onLoad(heroCastle, entityImages);
            world.setCharacter(character);
            world.addEntity(character);
            world.addEntity(heroCastle);
            break;
        case "path_tile":
            throw new RuntimeException("path_tile's aren't valid entities, define the path externally.");
        // TODO Handle other possible entities
        }

    }

    /**
     * load path tiles
     * @param path json data loaded from file containing path information
     * @param width width in number of cells
     * @param height height in number of cells
     * @return list of x, y cell coordinate pairs representing game path
     */
    public List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height, MAP_TYPE type,
            List<ImageView> entityImages) {

        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.  No other path types supported at this moment.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")),
                new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir : path.getJSONArray("path").toList()) {
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.size() == 0) {
            throw new IllegalArgumentException(
                    "This path just consists of a single tile, it needs to consist of multiple to form a loop.");
        }

        // load the first position into the orderedPath
        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        // add all coordinates of the path into the orderedPath
        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));

            if (y >= height || y < 0 || x >= width || x < 0) {
                throw new IllegalArgumentException(
                        "Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }

            PathTile.Direction dir = connections.get(i);
            PathTile tile = new PathTile(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
            onLoad(tile, connections.get(i - 1), dir, type, entityImages);
        }
        // we should connect back to the starting point
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }
        onLoad(starting, connections.get(connections.size() - 1), connections.get(0), type, entityImages);

        return orderedPath;
    }

    public abstract void onLoad(Character character, List<ImageView> entityImages);

    public abstract void onLoad(PathTile pathTile, PathTile.Direction into, PathTile.Direction out, MAP_TYPE type,
            List<ImageView> entityImages);

    // TODO Create additional abstract methods for the other entities
    public abstract void onLoad(HeroCastle heroCastle, List<ImageView> entityImages);

}
