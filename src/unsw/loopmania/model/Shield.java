package unsw.loopmania.model;

import org.json.JSONObject;

public interface Shield {

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
     * Get the Critical Percentage Decrease
     * @return int
     */
    public int getCriticalPercentageDecrease();

    /**
     * Set the Critical Percentage Decrease
     * @param critical_percentage_decrease
     */
    public void setCriticalPercentageDecrease(int critical_percentage_decrease);

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson();

}
