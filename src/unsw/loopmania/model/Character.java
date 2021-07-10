package unsw.loopmania.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // TODO = potentially implement relationships between this class and other classes
    private IntegerProperty hp;
    private IntegerProperty gold;
    private IntegerProperty xp;
    private IntegerProperty numSoldier;
    private int atk;
    private int def;

    private Weapon Dressed_weapon;
    private Armour Dressed_armour;
    private Shield Dressed_shield;
    private Helmet Dressed_helmet;


    public Character(PathPosition position) {
        super(position);
        hp = new SimpleIntegerProperty(300);
        gold = new SimpleIntegerProperty(100);
        xp = new SimpleIntegerProperty(0);
        numSoldier = new SimpleIntegerProperty(0);
        atk = 5;
        def = 0;
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public StringProperty hpPercentageProperty() {
        return new SimpleStringProperty((getHP() / 300 * 100) + "%");
    }
    
    public int getHP() {
        return hp.get();
    }

    public void setHP(int hp) {
        this.hp.set(hp);
    }

    public IntegerProperty goldProperty() {
        return gold;
    }

    public int getGold() {
        return gold.get();
    }

    public void setGold(int gold) {
        this.gold.set(gold);
    }

    public IntegerProperty xpProperty() {
        return xp;
    }
    
    public int getXP() {
        return xp.get();
    }

    public void setXP(int xp) {
        this.xp.set(xp);
    }

    public IntegerProperty soldierProperty() {
        return numSoldier;
    }

    public int getNumSoldier() {
        return numSoldier.get();
    }

    public void setNumSoldier(int numSoldier) {
        this.numSoldier.set(numSoldier);
    }

    public int getATK() {
        return atk;
    }

    public void setATK(int atk) {
        this.atk = atk;
    }

    public int getDEF() {
        return def;
    }

    public void setDEF(int def) {
        this.def = def;
    }
}
