package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Equipment {
    private int defence;


    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

}
