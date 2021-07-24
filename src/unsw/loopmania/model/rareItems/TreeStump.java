package unsw.loopmania.model.rareItems;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.RareItem;

public class TreeStump extends RareItem {
    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = "===== TreeStump =====\n" + "+ 8 defence (+ 12 damage if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }
}
