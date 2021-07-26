package unsw.loopmania.model.rareItems;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Weapon;
import unsw.loopmania.model.enemies.Boss;

public class Anduril extends RareItem implements Weapon {

    private int attack;
    private int Boss_damage_multiplier;

    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(8);
        this.Boss_damage_multiplier = 3;

        String description = "===== Anduril =====\n" + "+ 8 attack (triple damage if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getAttack(Enemy enemy) {
        if (enemy instanceof Boss) {
            return this.attack * Boss_damage_multiplier;
        } else {
            return this.attack;
        }
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

}
