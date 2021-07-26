package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class VillageBuilding extends Building {

    public static Image image = new Image((new File("src/assets/village.png")).toURI().toString());

    /**
     * Constructor for VillageBuilding
     */
    private int regainHP;

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.regainHP = 20;
    }

    /**
     * Get the Regain Hp
     * @return int
     */
    public int getRegainHp() {
        return this.regainHP;
    }
}
