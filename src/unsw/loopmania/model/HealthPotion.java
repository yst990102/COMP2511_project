package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class HealthPotion extends Potion {
    // TODO = add more weapon/item types
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    
}