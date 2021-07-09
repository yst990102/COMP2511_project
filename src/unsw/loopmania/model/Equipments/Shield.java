package unsw.loopmania.model.Equipments;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Shield extends Equipment {

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(0);
        setDefence(2);

        setPrice(150);

    }
}