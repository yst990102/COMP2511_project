package unsw.loopmania.model;

import org.json.JSONObject;

public interface Helmet {

    /**
     * Get the Attack
     * @return int 
     */
    public int getAttack();

    /**
     * Get the Attack
     * @param enemy
     * @return int 
     */
    public int getAttack(Enemy enemy);

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
     * Get the Defence
     * @param enemy
     * @return int 
     */
    public int getDefence(Enemy enemy);

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

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson();

}
