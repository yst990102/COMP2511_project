package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.enemies.Vampire;

/**
 * represents an equipped or unequipped stake in the backend world
 */
public class Stake extends Equipment implements Weapon {

    /**
     * Constructor for Stake
     */
    private int attack;
    private int attackToVampire;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(3);
        setPrice(150);

        this.attackToVampire = 8;

        String description = "===== Stake =====\n" + "+ 3 attack (+ 8 if Vampire)\n";
        setDescription(description);
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
        if (enemy instanceof Vampire) {
            return this.attackToVampire;
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