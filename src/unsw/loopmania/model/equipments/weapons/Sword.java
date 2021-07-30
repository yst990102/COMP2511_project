package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Equipment implements Weapon {

    private int attack;

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(5);

        setPrice(150);

        String description = "===== Sword =====\n" + "+ 5 attack";

        setDescription(description);
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getAttack(Enemy enemy) {
        return this.attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }
}
