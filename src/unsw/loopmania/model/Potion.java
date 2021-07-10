package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Potion extends Item {
    // TODO = add more weapon/item types
    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    
}