package unsw.loopmania.model.cards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Card;

/**
 * represents a tower card in the backend game world
 */
public class TowerCard extends Card {

    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
