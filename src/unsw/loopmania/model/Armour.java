package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Equipment {
    private int defence;
    private int damageReducePercentage;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDamageReducePercentage() {
        return this.damageReducePercentage;
    }

    public void setDamageReducePercentage(int damageReducePercentage) {
        this.damageReducePercentage = damageReducePercentage;
    }

}
