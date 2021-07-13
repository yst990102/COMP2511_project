package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Zombie extends Enemy {

    public Zombie(PathPosition position) {
        super(position);

        this.hp = 20;
        this.attack = 10;
        this.speed = 2;
        this.battle_radius = 3;
        this.support_radius = 3;

        this.critical_percentage = 20;

        this.gold_whenkilled = 100;
        this.exp_whenkilled = 200;

    }

}
