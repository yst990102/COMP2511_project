package unsw.loopmania.model.rareItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.RareItem;

public class Anduril extends RareItem {
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = "===== Anduril =====\n" + "+ 8 attack (triple damage if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }
}
