package unsw.loopmania.model;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends Equipment {
    private int attack;
    private int defence;

    private int enemy_attack_decrease;

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

    public int getEnemy_attack_decrease() {
        return this.enemy_attack_decrease;
    }

    public void setEnemy_attack_decrease(int enemy_attack_decrease) {
        this.enemy_attack_decrease = enemy_attack_decrease;
    }
    
}
