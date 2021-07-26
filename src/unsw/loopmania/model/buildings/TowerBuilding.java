package unsw.loopmania.model.buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Building;

public class TowerBuilding extends Building {

    public static Image image = new Image((new File("src/assets/tower.png")).toURI().toString());

    /**
     * Constructor for TowerBuilding
     */
    private int shootingRadius;
    private int towerAttack;

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.shootingRadius = 2;
        this.towerAttack = 5;
    }

    /**
     * Get the Shooting Radius
     * @return int
     */
    public int getShootingRadius() {
        return this.shootingRadius;
    }

    /**
     * Get the Tower Attack
     * @return int
     */
    public int getTowerAttack() {
        return this.towerAttack;
    }
}
