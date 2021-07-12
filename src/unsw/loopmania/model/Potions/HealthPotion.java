package unsw.loopmania.model.potions;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Potion;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class HealthPotion extends Potion {
    // TODO = add more weapon/item types
    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = 
         "===== Health Potion =====\n"
        +"+ 50 HP when use";

        setDescription(description);

        setPrice(200);
    }    
}