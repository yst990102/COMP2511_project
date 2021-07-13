package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends Equipment {
    private int defence;

    private int critical_percentage_decrease; // decrease 60% chance of critical bite from enemy

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getCritical_percentage_decrease() {
        return this.critical_percentage_decrease;
    }

    public void setCritical_percentage_decrease(int critical_percentage_decrease) {
        this.critical_percentage_decrease = critical_percentage_decrease;
    }

}
