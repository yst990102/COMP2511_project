package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Equipment {
    private int defence;
    private int damage_reduce_percentage;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDamage_reduce_percentage() {
        return this.damage_reduce_percentage;
    }

    public void setDamage_reduce_percentage(int damage_reduce_percentage) {
        this.damage_reduce_percentage = damage_reduce_percentage;
    }

}
