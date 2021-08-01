package unsw.loopmania.model.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Card;

/**
 * represents a zombie pit card in the backend game world
 */
public class ZombiePitCard extends Card {

    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
