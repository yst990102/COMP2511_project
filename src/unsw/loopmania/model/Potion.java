package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Potion extends Item {

    /**
     * Constructor for Potion
     */
    private int price;

    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    /**
     * Get the Price
     * @return int 
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Set the Price
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }
}