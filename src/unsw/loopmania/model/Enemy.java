package unsw.loopmania.model;

import java.util.Random;

import org.json.JSONObject;

/**
 * a basic form of enemy in the world
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

    /**
     * Get the Attack
     * @return int
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Get the HP
     * @return int
     */
    public int getHP() {
        return this.hp;
    }

    /**
     * Set the HP
     * @param newHP
     */
    public void setHP(int newHP) {
        this.hp = newHP;
    }

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("x", this.getX());
        Info.put("y", this.getY());
        Info.put("type", this.getClass().getSimpleName());

        return Info;
    }

}
