package unsw.loopmania.model;

import java.util.Random;

/**
 * a basic form of enemy in the world
 */
public class Enemy extends MovingEntity {
    
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
     * move the enemy
     */
    public void move() {
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }
    }

    public int getAttack() {
        return this.attack;
    }

}
