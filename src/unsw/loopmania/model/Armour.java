package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Equipment {

    /**
     * Constructor for Armour
     */
    private int defence;
    private int damageReducePercentage;

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Get the Defence
     * @return int 
     */
    public int getDefence() {
        return this.defence;
    }

    /**
     * Set the Defence
     * @param defence
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the Damage Reduce Percentage
     * @return int
     */
    public int getDamageReducePercentage() {
        return this.damageReducePercentage;
    }

    /**
     * Set the Damage Reduce Percentage
     * @param damageReducePercentage
     */
    public void setDamageReducePercentage(int damageReducePercentage) {
        this.damageReducePercentage = damageReducePercentage;
    }

}
