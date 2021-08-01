package unsw.loopmania.model.equipments.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped staff in the backend world
 */
public class Staff extends Equipment implements Weapon {

    /**
     * Constructor for Staff
     */
    private int attack;
    public int trancePercentage;// unit : %
    public int tranceDuration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);
        setPrice(200);

        this.trancePercentage = 20;
        this.tranceDuration = 10;

        String description = "===== Staff =====\n" + "+ 2 attack\n\n" + "----Skill:\n"
                + "    a random chance of inflicting a trance, which transforms the attacked enemy into an allied soldier temporarily";
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
        return this.attack;
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