package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Weapon extends Equipment {
    
    /**
     * Constructor for Weapon
     */
    private int attack;

    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Get the Attack
     * @return int
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Set the Attack
     * @param attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

}
