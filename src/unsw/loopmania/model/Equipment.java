package unsw.loopmania.model;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class Equipment extends Item {

    /**
     * Constructor for Equipment
     */
    public Equipment(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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
