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
        this.battleRadius = 2;
        this.supportRadius = 2;
        this.goldWhenKilled = 50;
        this.expWhenKilled = 100;
    }

    @Override
    public void setHP(int newHP) {
        if (newHP > 15) {
            this.hp = 15;
        }
        if (newHP < 0 ) {
            this.hp = 0;
        }
        this.hp = newHP;
    }
    
}
