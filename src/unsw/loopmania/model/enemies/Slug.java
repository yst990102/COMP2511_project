package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * A basic form of Enemy in the world
 */
public class Slug extends Enemy {

    public Slug(PathPosition position) {
        super(position);

        this.hp = 15;
        this.attack = 5;
        this.speed = 5;
        this.battleRadius = 2;
        this.supportRadius = 2;
        this.goldWhenKilled = 50;
        this.expWhenKilled = 100;
    }

}
