package unsw.loopmania.model.enemies.boss;

import java.io.File;

import javafx.scene.image.Image;
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

    private int treatment_amout = 10;

    public ElanMuske(PathPosition position) {
        super(position);

        this.hp = 150;
        this.attack = 35;
        this.speed = 5;
        this.battleRadius = 1;
        this.supportRadius = 1;
    }

}
