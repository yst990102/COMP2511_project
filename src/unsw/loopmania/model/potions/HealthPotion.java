package unsw.loopmania.model.potions;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Potion;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class HealthPotion extends Potion {
    
    private int healthRecovered;

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        String description = "===== Health Potion =====\n" + "+ 50 HP when use";

        this.healthRecovered = 50;
        setDescription(description);
        setPrice(200);
    }

    /**
     * Get the Health Recovered
     * @return int 
     */
    public int getHealthRecovered() {
        return healthRecovered;
    }
}