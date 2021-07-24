package unsw.loopmania.model;

public interface Helmet {

    /**
     * Get the Attack
     * @return int 
     */
    public int getAttack();

    /**
     * Set the Attack
     * @param attack
     */
    public void setAttack(int attack);

    /**
     * Get the Defence
     * @return int 
     */
    public int getDefence();

    /**
     * Set the Defence
     * @param defence
     */
    public void setDefence(int defence);

    /**
     * Get the Enemy Attack Decrease
     * @return int 
     */
    public int getEnemyAttackDecrease();

    /**
     * Set the Enemy Attack Decrease
     * @param enemyAttackDecrease
     */
    public void setEnemyAttackDecrease(int enemyAttackDecrease);

}
