package unsw.loopmania.model.rareItems;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Shield;
import unsw.loopmania.model.enemies.Boss;

/**
 * represents an equipped or unequipped TreeStump in the backend world
 */
public class TreeStump extends RareItem implements Shield {

    /**
     * Constructor for TreeStump
     */
    private int defence;
    private int Boss_damage_multiplier;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(8);
        this.Boss_damage_multiplier = 3;

        String description = "===== TreeStump =====\n" + "+ 8 defence (+ 12 defence if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }

    /**
     * Get the Defence
     * @return int 
     */
    @Override
    public int getDefence() {
        return this.defence;
    }

    /**
     * Get the Defence
     * @param enemy
     * @return int 
     */
    @Override
    public int getDefence(Enemy enemy) {
        if (enemy instanceof Boss) {
            return this.defence * Boss_damage_multiplier;
        } else {
            return this.defence;
        }
    }

    /**
     * Set the Defence
     * @param defence
     */
    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the Critical Percentage Decrease
     * @return int
     */
    @Override
    public int getCriticalPercentageDecrease() {
        return 0;
    }

    /**
     * Set the Critical Percentage Decrease
     * @param critical_percentage_decrease
     */
    @Override
    public void setCriticalPercentageDecrease(int critical_percentage_decrease) {
        return;
    }

}
