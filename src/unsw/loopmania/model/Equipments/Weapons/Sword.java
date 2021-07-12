package unsw.loopmania.model.equipments.Weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(5);

        setPrice(150);

        String description = 
        "===== Sword =====\n"
        +"+ 5 attack";

       setDescription(description);
    }
}
