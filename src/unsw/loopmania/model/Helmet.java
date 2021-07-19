package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Equipment {

    /**
     * Constructor for Helmet
     */
    private int attack;
    private int defence;
    private int enemyAttackDecrease;

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    /**
     * Get the Attack
     * @return int 
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Set the Attack
     * @param attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Get the Defence
     * @return int 
     */
    public int getDefence() {
        return this.defence;
    }

    /**
     * Set the Defence
     * @param defence
     */
    public void setDefence(int defence) {
        this.defence = defence;
    }

    /**
     * Get the Enemy Attack Decrease
     * @return int 
     */
    public int getEnemyAttackDecrease() {
        return this.enemyAttackDecrease;
    }

    /**
     * Set the Enemy Attack Decrease
     * @param enemyAttackDecrease
     */
    public void setEnemyAttackDecrease(int enemyAttackDecrease) {
        this.enemyAttackDecrease = enemyAttackDecrease;
    }
    
}
