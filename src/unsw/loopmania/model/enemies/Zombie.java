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
        this.battleRadius = 3;
        this.supportRadius = 3;
        this.criticalPercentage = 20;
        this.goldWhenKilled = 100;
        this.expWhenKilled = 200;
    }

    @Override
    public void setHP(int newHP) {
        if (newHP > 20) {
            this.hp = 20;
        }
        if (newHP < 0 ) {
            this.hp = 0;
        }
        this.hp = newHP;
    }

}
