package unsw.loopmania.model.enemies.boss;

import unsw.loopmania.model.PathPosition;
import unsw.loopmania.model.enemies.Boss;

/**
 * Description:
 * To the moon!An incredibly tough boss which, when appears, causes the price of
 * DoggieCoin to increase drastically. Defeating this boss causes the price of
 * DoggieCoin to plummet. Elan has the ability to heal other enemy NPCs. The
 * battle and support radii are the same as for slugs.
 * 
 * Spawn Conditions:
 * Spawns after 40 cycles, and the player has reached 10000 experience points.
 */

public class ElanMuske extends Boss {

    public static int treatment_amount = 3;

    public ElanMuske(PathPosition position) {
        super(position);

        this.hp = 5000;
        this.speed = 5;
        this.battleRadius = 1;
        this.supportRadius = 1;
    }

}
