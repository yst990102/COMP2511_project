package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.rareitemproperty.AndurilProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Stake extends Equipment implements Weapon {

    private int attack;
    private int attackToVampire;

    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(3);

        this.attackToVampire = 8;

        setPrice(150);

        String description = "===== Stake =====\n" + "+ 3 attack (+ 8 if Vampire)\n";

        setDescription(description);
    }

    @Override
    public int getAttack() {
        if (this.rareItemProperty.getClass().equals(AndurilProperty.class)) {
            return 3 * this.attack;
        } else {
            return this.attack;
        }
    }

    @Override
    public int getAttack(Enemy enemy) {
        int attack;
        if (enemy instanceof Vampire) {
            attack = this.attackToVampire;
        } else {
            attack = this.attack;
        }

        if (this.rareItemProperty.getClass().equals(AndurilProperty.class)) {
            return 3 * attack;
        } else {
            return attack;
        }
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

}