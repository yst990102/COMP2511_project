package unsw.loopmania.model.Equipments;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Helmet extends Equipment {

    private int enemy_attack = -2;

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(-2);
        setDefence(2);

        this.enemy_attack = -2;
    }
}