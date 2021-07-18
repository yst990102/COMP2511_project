package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Weapon {

    /**
     * Constructor for Stake
     */
    private int attackToVampire = 8;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(3);

        this.attackToVampire  = 8;

        setPrice(150);

        String description = 
        "===== Stake =====\n"
        +"+ 3 attack (+ 8 if Vampire)\n";

       setDescription(description);
    }

    /**
     * Get the Attack to Vampire
     * @return int 
     */
    public int getAttackToVampire() {
        return this.attackToVampire ;
    }

}