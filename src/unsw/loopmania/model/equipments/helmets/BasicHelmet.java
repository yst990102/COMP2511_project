package unsw.loopmania.model.equipments.helmets;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.Equipment;
import unsw.loopmania.model.Helmet;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class BasicHelmet extends Equipment implements Helmet {

    private int attack;
    private int defence;
    private int enemyAttackDecrease;

    public BasicHelmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

        setAttack(-2);
        setDefence(2);

        setEnemyAttackDecrease(2);

        setPrice(200);

        String description = "===== Basic Helmet =====\n" + "- 2 attack\n" + "+ 2 defence\n" + "- 2 enemy attack";

        setDescription(description);
    }

    @Override
    public int getAttack() {
        return this.attack;
    }

    @Override
    public int getAttack(Enemy enemy) {
        return this.attack;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public int getDefence() {
        return this.defence;
    }

    @Override
    public int getDefence(Enemy enemy) {
        return this.defence;
    }

    @Override
    public void setDefence(int defence) {
        this.defence = defence;
    }

    @Override
    public int getEnemyAttackDecrease() {
        return this.enemyAttackDecrease;
    }

    @Override
    public void setEnemyAttackDecrease(int enemyAttackDecrease) {
        this.enemyAttackDecrease = enemyAttackDecrease;
    }

}