package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Equipment extends Item {

    /**
     * Constructor for Equipment
     */
    private int price;

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
