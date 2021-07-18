package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {

    int trancePercentage;// unit : %
    int tranceDuration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);

        this.trancePercentage = 20;
        this.tranceDuration = 10;

        setPrice(200);

        String description = 
        "===== Staff =====\n"
       +"+ 2 attack\n\n"
       +"----Skill:\n"
       +"    a random chance of inflicting a trance, which transforms the attacked enemy into an allied soldier temporarily";

       setDescription(description);
    }
}