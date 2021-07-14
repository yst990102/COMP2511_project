package unsw.loopmania.model.enemies;

import java.util.Random;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Vampire extends Enemy {

    public int criticalExtraDamage = 10; // if critical, make 10 extra damage

    public Vampire(PathPosition position) {
        super(position);

        this.hp = 25;
        this.attack = 20;
        this.speed = 3;
        this.battleRadius = 5;
        this.supportRadius = 5;

        this.criticalPercentage = 20;

        this.goldWhenKilled = 200;
        this.expWhenKilled = 300;

    }

    @Override
    public int getAttack() {
        int critical_random = new Random().nextInt(100);
        if (critical_random < criticalPercentage) {
            // critical bite
            return attack + criticalExtraDamage;
        } else {
            return attack;
        }
    }

    public int getAttack(int critical_percentage_decrease) {
        int critical_random = new Random().nextInt(100);
        if (critical_random < (criticalPercentage * (1 - Double.valueOf(critical_percentage_decrease) / 100))) {
            // critical bite
            return attack + criticalExtraDamage;
        } else {
            return attack;
        }
    }

}
