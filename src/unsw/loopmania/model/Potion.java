package unsw.loopmania.model;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Potion extends Item {

    /**
     * Constructor for Potion
     */
    public Potion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
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