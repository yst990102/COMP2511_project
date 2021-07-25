package unsw.loopmania.model.equipments.armours;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Armour;
import unsw.loopmania.model.Equipment;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicArmour extends Equipment implements Armour {

    public static Image image = new Image((new File("src/assets/armour.png")).toURI().toString());

    private int defence;
    private int damageReducePercentage;

    public BasicArmour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(5);
        setDamageReducePercentage(50);

        setPrice(250);

        String description = "===== Basic Armour =====\n" + "+ 5 defence\n" + "-50% enemy attack";

        setDescription(description);

    }

    @Override
    public int getDefence() {
        return this.defence;
    }

    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public int getDamageReducePercentage() {
        return this.damageReducePercentage;
    }

    @Override
    public void setDamageReducePercentage(int damageReducePercentage) {
        this.damageReducePercentage = damageReducePercentage;
    }
}