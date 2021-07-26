package unsw.loopmania.model;

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
}
