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
     * Get Weapon Attack
     * @return int
     */
    public int getAttack() {
        return this.attack;
    }
    
    /**
     * Set Weapon Attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

}
