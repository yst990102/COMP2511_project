package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building {
    
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
