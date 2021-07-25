package unsw.loopmania.model.rareItems;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.RareItem;
import unsw.loopmania.model.Shield;
import unsw.loopmania.model.enemies.Boss;

public class TreeStump extends RareItem implements Shield {

    public static Image image = new Image((new File("src/assets/tree_stump.png")).toURI().toString());

    private int defence;
    private int Boss_damage_multiplier;

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setDefence(8);
        this.Boss_damage_multiplier = 3;

        String description = "===== TreeStump =====\n" + "+ 8 defence (+ 12 defence if BOSS)\n";

        setDescription(description);
        setPrice(500);
    }

    @Override
    public int getDefence() {
        return this.defence;
    }

    public int getDefenceByEnemy(Enemy enemy) {
        if (enemy instanceof Boss) {
            return this.defence * Boss_damage_multiplier;
        } else {
            return this.defence;
        }
    }

    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public int getCriticalPercentageDecrease() {
        return 0;
    }

    @Override
    public void setCriticalPercentageDecrease(int critical_percentage_decrease) {
        return;
    }

}
