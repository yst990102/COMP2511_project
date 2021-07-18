package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Equipment extends Item {
    private int price;

    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
