package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    /**
     * object holding position in the path
     */
    private PathPosition position;

    /**
     * Create a moving entity which moves up and down the path in position
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position) {
        super();
        this.position = position;
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    /**
     * Get the SimpleIntegerProperty of X
     */
    public SimpleIntegerProperty x() {
        return position.getX();
    }

    /**
     * Get the SimpleIntegerProperty of Y
     */
    public SimpleIntegerProperty y() {
        return position.getY();
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
