package unsw.loopmania.model.Weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Weapon {

    private int trance_percentage;// unit : %
    private int trance_duration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);

        this.trance_percentage = 20;
        this.trance_duration = 10;

        setPrice(200);

        String description = 
        "===== Staff =====\n"
       +"+ 2 attack\n\n"
       +"----Skill:\n"
       +"    a random chance of inflicting a trance, which transforms the attacked enemy into an allied soldier temporarily";

       setDescription(description);
    }
}