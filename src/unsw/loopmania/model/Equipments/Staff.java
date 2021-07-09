package unsw.loopmania.model.Equipments;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Equipment {

    private int trance_percentage;// unit : %
    private int trance_duration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);
        setDefence(0);

        this.trance_percentage = 20;
        this.trance_duration = 10;

        setPrice(200);
    }
}