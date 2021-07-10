package unsw.loopmania.model.Shields;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Shield;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicShield extends Shield {

    public BasicShield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(2);

        setPrice(150);

        String description = 
        "===== Basic Shield =====\n"
       +"+ 2 defence\n"
       +"-60% Vampire crit chance";

       setDescription(description);
    }
}