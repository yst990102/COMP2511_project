package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Slug extends Enemy {

    public Slug(PathPosition position) {
        super(position);

        this.hp = 15;
        this.attack = 5;
        this.speed = 5;
        this.battle_radius = 2;
        this.support_radius = 2;

        this.gold_whenkilled = 50;
        this.exp_whenkilled = 100;

    }

}
