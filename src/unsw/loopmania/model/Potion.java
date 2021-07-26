package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Potion extends Item {

    /**
     * Constructor for Potion
     */
    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}