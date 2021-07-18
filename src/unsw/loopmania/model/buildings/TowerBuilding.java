package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerBuilding extends Building {
    private int shootingRadius;
    private int towerAttack;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.shootingRadius = 2;
        this.towerAttack = 5;
    }

    public int getShootingRadius() {
        return this.shootingRadius;
    }

    public int getTowerAttack() {
        return this.towerAttack;
    }
}
