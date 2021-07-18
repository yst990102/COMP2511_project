package unsw.loopmania.model.equipments.armours;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Armour;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicArmour extends Armour {
    public BasicArmour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(5);
        setDamageReducePercentage(50);

        setPrice(250);

        String description = "===== Basic Armour =====\n" + "+ 5 defence\n" + "-50% enemy attack";

        setDescription(description);

    }
}