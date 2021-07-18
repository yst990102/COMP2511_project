package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;

public class VillageBuilding extends Building {
    private int regainHP;

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.regainHP = 20;
    }

    public int getRegainHp() {
        return this.regainHP;
    }
}
