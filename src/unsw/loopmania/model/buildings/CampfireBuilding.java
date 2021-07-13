package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building {
    
    private int battleRadius;

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.battleRadius = 5;
    }

    public int getBattleRadius() {
        return battleRadius;
    }
}
