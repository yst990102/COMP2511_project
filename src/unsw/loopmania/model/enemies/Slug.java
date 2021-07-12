package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Slug extends Enemy {

    public Slug(PathPosition position) {
        super(position);

        setHp(15);
        setAttack(5);
        setSpeed(5);
        setBattle_radius(2);
        setSupport_radius(2);

        setGold_whenkilled(50);
        setExp_whenkilled(100);

    }

}
