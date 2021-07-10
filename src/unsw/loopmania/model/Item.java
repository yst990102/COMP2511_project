package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {
    private String description;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
