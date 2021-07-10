package unsw.loopmania.model.Weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Weapon {

    private int attack_to_vampire = 8;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(3);

        this.attack_to_vampire = 8;

        setPrice(150);
    }
}