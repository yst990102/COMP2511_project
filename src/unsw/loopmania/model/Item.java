package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {

    private String description;
    private int price;

    /**
     * Constructor for Item
     */
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Get the Description
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the Description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
