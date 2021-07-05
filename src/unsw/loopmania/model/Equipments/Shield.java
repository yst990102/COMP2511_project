package unsw.loopmania.model.Equipments;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Shield extends Equipment {
    // TODO = add more weapon/item types
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    
}