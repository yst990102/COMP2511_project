package unsw.loopmania.model.enemies.boss;

import java.util.Random;

import javafx.util.Pair;
import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.enemies.Boss;

/**
 * Description:
 * Wow much coin how money so crypto plz mine v rich very currencyA 
 * special boss which spawns the DoggieCoin upon defeat, which randomly 
 * fluctuates in sellable price to an extraordinary extent. It has high  
 * health and can stun the character, which prevents the character from 
 * making an attack temporarily. The battle and support radii are the same as for slugs
 * 
 * Spawn Conditions:
 * Spawns after 20 cycles
 */
public class Doggie extends Boss {

    private double stun_percentage = 0.2;
    private int stun_round = 2;

    public Doggie(PathPosition position) {
        super(position);

        this.hp = 75;
        this.attack = 20;
        this.speed = 5;
        this.battleRadius = 2;
        this.supportRadius = 2;

    }

    public Pair<Boolean, Integer> getAttackByStun() {
        int randint = new Random(System.currentTimeMillis()).nextInt(100);
        if (randint < stun_percentage) {
            return new Pair<Boolean, Integer>(true, getAttack());
        } else {
            return new Pair<Boolean, Integer>(false, getAttack());
        }
    }

    public double getStun_percentage() {
        return this.stun_percentage;
    }

    public void setStun_percentage(double stun_percentage) {
        this.stun_percentage = stun_percentage;
    }

    public int getStun_round() {
        return this.stun_round;
    }

    public void setStun_round(int stun_round) {
        this.stun_round = stun_round;
    }

}
