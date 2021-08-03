package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.rareitemproperty.AndurilProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Equipment implements Weapon {

    private int attack;

    public int trancePercentage;// unit : %
    public int tranceDuration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);

        this.trancePercentage = 20;
        this.tranceDuration = 10;

        setPrice(200);

        String description = "===== Staff =====\n" + "+ 2 attack\n\n" + "----Skill:\n"
                + "    a random chance of inflicting a trance, which transforms the attacked enemy into an allied soldier temporarily";

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
        return getAttack();
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }
}