package unsw.loopmania.model.rareItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.RareItem;

/**
 * represents an equipped or unequipped sword in the backend world
 */

public class TheOneRing extends RareItem {
    // TODO = add more weapon/item types
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = 
         "===== The One Ring =====\n"
        +"(Rare Item)\n"
        +"    If the Character is killed, it respawns with full health up to a single time";

        setDescription(description);
    }    
}
