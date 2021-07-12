package unsw.loopmania.model.enemies;

import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Zombie extends Enemy {

    public Zombie(PathPosition position) {
        super(position);

        setHp(20);
        setAttack(10);
        setSpeed(2);
        setBattle_radius(3);
        setSupport_radius(3);

        setGold_whenkilled(100);
        setExp_whenkilled(200);

    }

}
