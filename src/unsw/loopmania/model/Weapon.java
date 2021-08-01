package unsw.loopmania.model;

import org.json.JSONObject;

public interface Weapon {

    /**
    * Get the Attack
    * @return int 
    */
    public int getAttack();

    public int getAttack(Enemy enemy);

    /**
     * Set the Attack
     * @param attack
     */
    public void setAttack(int attack);

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson();
    
}
