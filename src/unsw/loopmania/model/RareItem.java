package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class RareItem extends Item {

    /**
     * Constructor for RareItem
     */
    private int price;

    public RareItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
