package unsw.loopmania.model;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of enemy in the world
 */
public class BasicEnemy extends MovingEntity {
    
    private int hp;
    private int attack;
    
    private int gold_whenkilled;
    private int exp_whenkilled;


    public BasicEnemy(PathPosition position) {
        super(position);

        setHp(15);
        setAttack(5);

        setGold_whenkilled(50);
        setExp_whenkilled(100);

    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }


    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }


    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }


    public int getGold_whenkilled() {
        return this.gold_whenkilled;
    }

    public void setGold_whenkilled(int gold_whenkilled) {
        this.gold_whenkilled = gold_whenkilled;
    }

    public int getExp_whenkilled() {
        return this.exp_whenkilled;
    }

    public void setExp_whenkilled(int exp_whenkilled) {
        this.exp_whenkilled = exp_whenkilled;
    }

}
