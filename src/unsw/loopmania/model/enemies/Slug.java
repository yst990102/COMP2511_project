package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of Slug in the world
 */
public class Slug extends Enemy {

    public Slug(PathPosition position) {
        super(position);

        this.hp = 15;
        this.attack = 5;
        this.speed = 5;
        this.battleRadius = 1;
        this.supportRadius = 1;
        this.criticalPercentage = 0;
        this.goldWhenKilled = 50;
        this.expWhenKilled = 100;
    }

    /**
     * Set the HP
     * @param newHP
     */
    @Override
    public void setHP(int newHP) {
        if (newHP > 15) {
            this.hp = 15;
        } else if (newHP < 0) {
            this.hp = 0;
        } else {
            this.hp = newHP;
        }
        return;
    }

}
