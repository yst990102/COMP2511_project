package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

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
