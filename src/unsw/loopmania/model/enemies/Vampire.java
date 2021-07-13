package unsw.loopmania.model.enemies;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Vampire extends Enemy {

    public int critical_extra_damage = 10; // if critical, make 10 extra damage

    public Vampire(PathPosition position) {
        super(position);

        this.hp = 25;
        this.attack = 20;
        this.speed = 3;
        this.battle_radius = 5;
        this.support_radius = 5;

        this.critical_percentage = 20;

        this.gold_whenkilled = 200;
        this.exp_whenkilled = 300;

    }

    @Override
    public int getAttack() {
        int critical_random = new Random().nextInt(100);
        if (critical_random < critical_percentage) {
            // critical bite
            return attack + critical_extra_damage;
        } else {
            return attack;
        }
    }

    public int getAttack(int critical_percentage_decrease) {
        int critical_random = new Random().nextInt(100);
        if (critical_random < (critical_percentage * (1 - Double.valueOf(critical_percentage_decrease) / 100))) {
            // critical bite
            return attack + critical_extra_damage;
        } else {
            return attack;
        }
    }

}