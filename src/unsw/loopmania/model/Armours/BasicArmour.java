package unsw.loopmania.model.Armours;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Armour;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicArmour extends Armour {
    public BasicArmour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(5);

        setPrice(250);
    }
}