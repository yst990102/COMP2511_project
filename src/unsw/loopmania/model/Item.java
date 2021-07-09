package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {
    private int price;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
