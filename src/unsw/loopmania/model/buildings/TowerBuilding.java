package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerBuilding extends Building {
    private int shootingRadius;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.shootingRadius = 5;
    }

    public int getShootingRadius() {
        return shootingRadius;
    }
}
