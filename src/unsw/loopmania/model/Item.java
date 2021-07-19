package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {

    /**
     * Constructor for Item
     */
    private String description;

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

}
