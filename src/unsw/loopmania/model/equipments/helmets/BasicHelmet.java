package unsw.loopmania.model.equipments.helmets;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Helmet;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicHelmet extends Helmet {

    public BasicHelmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(-2);
        setDefence(2);

        setEnemyAttackDecrease(2);

        setPrice(200);

        String description = 
        "===== Basic Helmet =====\n"
        +"- 2 attack\n"
        +"+ 2 defence\n"
        +"- 2 enemy attack";

       setDescription(description);
    }

}