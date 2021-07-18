package unsw.loopmania.model.buildings;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Character;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building {
    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    
    /**
     * Constructor for VampireCastleBuilding
     */
    private int currentCycle;

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        currentCycle = 0;
    }

    /**
     * Update the Cycle
     */
    public void updateCycle() {
        currentCycle++;
    }

    /**
     * Check whether the Vampire Castle can spawn Vampire
     * @param c
     * @return boolean
     */
    public boolean canSpawnVampire(Character c) {
        if (currentCycle % 5 == 0 && c.getX() == 0 && c.getY() == 0) {
            return true;
        }
        return false;
    }
}
