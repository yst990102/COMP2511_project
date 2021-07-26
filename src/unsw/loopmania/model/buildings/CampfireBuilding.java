package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class CampfireBuilding extends Building {

    public static Image image = new Image((new File("src/assets/campfire.png")).toURI().toString());

    /**
     * Constructor for CampfireBuilding
     */
    private int battleRadius;

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.battleRadius = 3;
    }

    /**
     * Get the Battle Radius
     * @return int
     */
    public int getBattleRadius() {
        return battleRadius;
    }
}
