package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Building;

public class VillageBuilding extends Building {

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
