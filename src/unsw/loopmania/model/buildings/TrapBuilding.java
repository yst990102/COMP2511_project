package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class TrapBuilding extends Building {

    /**
     * Constructor for TrapBuilding
     */
    private int trapAttack;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.trapAttack = 10;
    }

    /**
     * Get the Trap Attack
     * @return int
     */
    public int getTrapAttack() {
        return this.trapAttack;
    }
}
