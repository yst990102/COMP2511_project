package unsw.loopmania.model.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Card;

/**
 * represents a campfire card in the backend game world
 */
public class CampfireCard extends Card {

    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
