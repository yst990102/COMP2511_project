package unsw.loopmania.model;

import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;

public class RareItem extends Item {

    /**
     * Constructor for RareItem
     */
    public RareItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * save the data to Json
     * @return JSONObject
     */
    public JSONObject toJson() {
        JSONObject Info = new JSONObject();
        Info.put("x", this.getX());
        Info.put("y", this.getY());
        Info.put("type", this.getClass().getSimpleName());

        return Info;
    }
}
