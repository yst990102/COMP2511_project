package unsw.loopmania.model.equipments.weapons;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Equipment implements Weapon {

    public static Image image = new Image((new File("src/assets/basic_sword.png")).toURI().toString());

    private int attack;

    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(5);

        setPrice(150);

        String description = "===== Sword =====\n" + "+ 5 attack";

        setDescription(description);
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }
}
