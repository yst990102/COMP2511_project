package unsw.loopmania.model.Equipments;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Equipment {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(5);
        setDefence(0);

        setPrice(150);
    }
}
