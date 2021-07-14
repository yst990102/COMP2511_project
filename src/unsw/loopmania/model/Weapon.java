package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Weapon extends Equipment {
    
    private int attack;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }


    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
