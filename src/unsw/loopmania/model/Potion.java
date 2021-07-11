package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Potion extends Item {
    private int price;

    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}