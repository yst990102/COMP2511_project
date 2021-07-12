package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Vampire extends Enemy {

    public Vampire(PathPosition position) {
        super(position);

        setHp(25);
        setAttack(20);
        setSpeed(3);
        setBattle_radius(5);
        setSupport_radius(5);

        setGold_whenkilled(200);
        setExp_whenkilled(300);

    }

}