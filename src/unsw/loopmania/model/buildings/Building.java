package unsw.loopmania.model.buildings;

import unsw.loopmania.model.StaticEntity;
import javafx.beans.property.SimpleIntegerProperty;

public class Building extends StaticEntity {
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x,y);
    }
}
