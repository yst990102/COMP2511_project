package unsw.loopmania.model;

import java.util.Random;

/**
 * A basic form of Enemy in the world
 */
public class Enemy extends MovingEntity {
    
    /**
     * Constructor for Enemy
     */
    public int hp;
    public int attack;
    public int speed;
    public int battleRadius;
    public int supportRadius;
    public int criticalPercentage;
    public int goldWhenKilled;
    public int expWhenKilled;

    public Enemy(PathPosition position) {
        super(position);
    }

    /**
     * Move the Enemy
     */
    public void move() {
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }
    }

    /**
     * Get Enemy Attack
     * @return int
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Set Enemy Hp
     * @param hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

}
