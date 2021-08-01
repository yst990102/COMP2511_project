package unsw.loopmania.model.enemies;

import java.util.Random;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of Vampire in the world
 */
public class Vampire extends Enemy {

    /**
     * Constructor for Vampire
     * If critical, make 10 extra damage
     */
    public int criticalExtraDamage = 10;

    public Vampire(PathPosition position) {
        super(position);

        this.hp = 25;
        this.attack = 20;
        this.speed = 3;
        this.battleRadius = 2;
        this.supportRadius = 3;
        this.criticalPercentage = 20;
        this.goldWhenKilled = 200;
        this.expWhenKilled = 300;
    }

    /**
     * Get the Attack
     * @return int
     */
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

    /**
     * Get the Attack
     * @param critical_percentage_decrease
     * @return int
     */
    public int getAttack(int critical_percentage_decrease) {
        int critical_random = new Random().nextInt(100);
        if (critical_random < (criticalPercentage * (1 - Double.valueOf(critical_percentage_decrease) / 100))) {
            // critical bite
            return attack + criticalExtraDamage;
        } else {
            return attack;
        }
    }

    /**
     * Set the HP
     * @param newHP
     */
    @Override
    public void setHP(int newHP) {
        if (newHP > 25) {
            this.hp = 25;
        } else if (newHP < 0) {
            this.hp = 0;
        } else {
            this.hp = newHP;
        }
        return;
    }

}
