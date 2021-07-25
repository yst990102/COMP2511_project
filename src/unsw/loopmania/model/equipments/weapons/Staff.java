package unsw.loopmania.model.equipments.weapons;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Weapon;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Staff extends Equipment implements Weapon {

    public static Image image = new Image((new File("src/assets/staff.png")).toURI().toString());

    private int attack;

    int trancePercentage;// unit : %
    int tranceDuration;// unit : second

    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(2);

        this.trancePercentage = 20;
        this.tranceDuration = 10;

        setPrice(200);

        String description = "===== Staff =====\n" + "+ 2 attack\n\n" + "----Skill:\n"
                + "    a random chance of inflicting a trance, which transforms the attacked enemy into an allied soldier temporarily";

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