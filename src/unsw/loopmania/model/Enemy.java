package unsw.loopmania.model;

import java.util.Random;

import jdk.jfr.Percentage;

public class Enemy extends MovingEntity {

    private int hp;
    private int attack;
    private int speed;
    private int battle_radius;
    private int support_radius;

    private int gold_whenkilled;
    private int exp_whenkilled;

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

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBattle_radius() {
        return this.battle_radius;
    }

    public void setBattle_radius(int battle_radius) {
        this.battle_radius = battle_radius;
    }

    public int getSupport_radius() {
        return this.support_radius;
    }

    public void setSupport_radius(int support_radius) {
        this.support_radius = support_radius;
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
