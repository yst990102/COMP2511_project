package unsw.loopmania.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a non-moving entity
 * unlike the moving entities, this can be placed anywhere on the game map
 */
public abstract class StaticEntity extends Entity {

    /**
     * x and y coordinates represented by IntegerProperty, so ChangeListeners can be added
     */
    private IntegerProperty x, y;

    public StaticEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Get the IntegerProperty of X
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * Get the IntegerProperty of Y
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * Get the value of X
     */
    public int getX() {
        return x().get();
    }

    /**
     * Get the value of Y
     */
    public int getY() {
        return y().get();
    }
}
