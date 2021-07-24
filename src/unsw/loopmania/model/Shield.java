package unsw.loopmania.model;

public interface Shield {

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
     * Get the Critical Percentage Decrease
     * @return int
     */
    public int getCriticalPercentageDecrease();

    /**
     * Set the Critical Percentage Decrease
     * @param critical_percentage_decrease
     */
    public void setCriticalPercentageDecrease(int critical_percentage_decrease);

}
