package unsw.loopmania.model.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Card;

/**
 * represents a trap card in the backend game world
 */
public class TrapCard extends Card {

    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
