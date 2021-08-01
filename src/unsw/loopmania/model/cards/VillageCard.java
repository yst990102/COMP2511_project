package unsw.loopmania.model.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Card;

/**
 * represents a village card in the backend game world
 */
public class VillageCard extends Card {

    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
