package unsw.loopmania.model;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class Building extends StaticEntity {
    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("x", this.getX());
        Info.put("y", this.getY());
        Info.put("type", this.getClass().getSimpleName());

        return Info;
    }
}
