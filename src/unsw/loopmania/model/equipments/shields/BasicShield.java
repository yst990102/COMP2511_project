package unsw.loopmania.model.equipments.shields;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Shield;

/**
 * represents an equipped or unequipped BasicShield in the backend world
 */
public class BasicShield extends Equipment implements Shield {

    /**
     * Constructor for BasicShield
     */
    private int defence;
    // decrease 60% chance of critical bite from enemy
    private int critical_percentage_decrease; 

    public BasicShield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(2);
        setCriticalPercentageDecrease(60);
        setPrice(150);

        String description = "===== Basic Shield =====\n" + "+ 2 defence\n" + "-60% Vampire crit chance";
        setDescription(description);
    }

    @Override
    public int getDefence() {
        return this.defence;
    }

    @Override
    public int getDefence(Enemy enemy) {
        return this.defence;
    }

    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public int getCriticalPercentageDecrease() {
        return this.critical_percentage_decrease;
    }

    @Override
    public void setCriticalPercentageDecrease(int critical_percentage_decrease) {
        this.critical_percentage_decrease = critical_percentage_decrease;
    }

}