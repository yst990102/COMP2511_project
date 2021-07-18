package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class TrapBuilding extends Building {
    private int trapAttack;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.trapAttack = 10;
    }

    public int getTrapAttack() {
        return this.trapAttack;
    }
}
