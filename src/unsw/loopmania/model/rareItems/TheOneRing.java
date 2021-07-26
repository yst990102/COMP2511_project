package unsw.loopmania.model.rareItems;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.RareItem;

/**
 * represents an equipped or unequipped sword in the backend world
 */

public class TheOneRing extends RareItem {

    public static Image image = new Image((new File("src/assets/the_one_ring.png")).toURI().toString());

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = "===== The One Ring =====\n" + "(Rare Item)\n"
                + "    If the Character is killed, it respawns with full health up to a single time";

        setDescription(description);
        setPrice(500);
    }
}
