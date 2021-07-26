package unsw.loopmania.model.cards;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Card;

public class CampfireCard extends Card {

    public static Image image = new Image((new File("src/assets/campfire_card.png")).toURI().toString());

    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
}
