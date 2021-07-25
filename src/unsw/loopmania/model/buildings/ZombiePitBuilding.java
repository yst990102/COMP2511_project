package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class ZombiePitBuilding extends Building {

    public static Image image = new Image((new File("src/assets/zombie_pit.png")).toURI().toString());

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
