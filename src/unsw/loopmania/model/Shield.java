package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends Equipment {

    /**
     * Constructor for Shield
     */
    private int defence;
    private int critical_percentage_decrease; // decrease 60% chance of critical bite from enemy

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
     * Get the Critical Percentage Decrease
     * @return int
     */
    public int getCriticalPercentageDecrease() {
        return this.critical_percentage_decrease;
    }

    /**
     * Set the Critical Percentage Decrease
     * @param critical_percentage_decrease
     */
    public void setCriticalPercentageDecrease(int critical_percentage_decrease) {
        this.critical_percentage_decrease = critical_percentage_decrease;
    }

}
