package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class BarracksBuilding extends Building {

    public static Image image = new Image((new File("src/assets/barracks.png")).toURI().toString());

    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
}
