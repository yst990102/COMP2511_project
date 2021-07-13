package unsw.loopmania.model;

import java.util.Random;

public class Enemy extends MovingEntity {

    public int hp;
    public int attack;
    public int speed;
    public int battle_radius;
    public int support_radius;

    public int critical_percentage;

    public int gold_whenkilled;
    public int exp_whenkilled;

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
