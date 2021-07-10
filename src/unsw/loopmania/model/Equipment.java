package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Equipment extends Item {
    private int attack = 0;
    private int defence = 5;

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

}
