package unsw.loopmania.model.rareItems;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.enemies.Boss;

/**
 * represents an equipped or unequipped Anduril in the backend world
 */
public class Anduril extends RareItem implements Weapon {

    /**
     * Constructor for Anduril
     */
    private int attack;
    private int Boss_damage_multiplier;

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(8);
        this.Boss_damage_multiplier = 3;

        String description = "===== Anduril =====\n" + "+ 8 attack (triple damage if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }

    /**
    * Get the Attack
    * @return int 
    */
    @Override
    public int getAttack() {
        return this.attack;
    }

    /**
    * Get the Attack
    * @param enemy
    * @return int 
    */
    @Override
    public int getAttack(Enemy enemy) {
        if (enemy instanceof Boss) {
            return this.attack * Boss_damage_multiplier;
        } else {
            return this.attack;
        }
    }

    /**
     * Set the Attack
     * @param attack
     */
    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

}
