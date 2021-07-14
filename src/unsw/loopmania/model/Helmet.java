package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Equipment {
    private int attack;
    private int defence;

    private int enemyAttackDecrease;

    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getEnemyAttackDecrease() {
        return this.enemyAttackDecrease;
    }

    public void setEnemyAttackDecrease(int enemyAttackDecrease) {
        this.enemyAttackDecrease = enemyAttackDecrease;
    }
    
}
