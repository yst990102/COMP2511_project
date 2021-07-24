package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public interface Armour {

    /**
     * Get the Defence
     * @return int 
     */
    public int getDefence();

    /**
     * Set the Defence
     * @param defence
     */
    public void setDefence(int defence);

    /**
     * Get the Damage Reduce Percentage
     * @return int
     */
    public int getDamageReducePercentage();

    /**
     * Set the Damage Reduce Percentage
     * @param damageReducePercentage
     */
    public void setDamageReducePercentage(int damageReducePercentage);

}
