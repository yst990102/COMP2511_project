package unsw.loopmania.model;

import java.util.Collection;

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

    public JSONObject toJson();
}
