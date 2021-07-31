package unsw.loopmania.model;

import org.json.JSONObject;

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

    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("x", this.getX());
        Info.put("y", this.getY());
        Info.put("type", this.getClass().getSimpleName());

        return Info;
    }

}
