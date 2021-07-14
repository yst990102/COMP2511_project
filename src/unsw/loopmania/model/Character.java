package unsw.loopmania.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // TODO = potentially implement relationships between this class and other
    // classes
    private IntegerProperty hp;
    private IntegerProperty gold;
    private IntegerProperty xp;
    private IntegerProperty numSoldier;
    private int atk;
    private int def;

    private Weapon dressedWeapon;
    private Armour dressedArmour;
    private Shield dressedShield;
    private Helmet dressedHelmet;

    private List<Item> Bag;

    public Character(PathPosition position) {
        super(position);

        hp = new SimpleIntegerProperty(300);
        gold = new SimpleIntegerProperty(100);
        xp = new SimpleIntegerProperty(0);
        numSoldier = new SimpleIntegerProperty(0);

        atk = 5;
        def = 0;

        Bag = new ArrayList<Item>(16);
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public StringProperty hpPercentageProperty() {
        Double HPpercentage = Double.valueOf(getHP()) / 300 * 100;
        String HP_percentage = String.format("%.2f", HPpercentage);

        return new SimpleStringProperty(HP_percentage + "%");
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
        int weapon_atk = 0;
        int helmet_atk = 0;

        if (dressedWeapon != null) {
            weapon_atk = dressedWeapon.getAttack();
        }

        if (dressedHelmet != null) {
            helmet_atk = dressedHelmet.getAttack();
        }

        return atk + weapon_atk + helmet_atk;
    }

    public void setATK(int atk) {
        this.atk = atk;
    }

    public int getDEF() {
        int armour_def = 0;
        int shield_def = 0;
        int helmet_def = 0;

        if (dressedArmour != null) {
            armour_def = dressedArmour.getDefence();
        }

        if (dressedShield != null) {
            shield_def = dressedShield.getDefence();
        }

        if (dressedHelmet != null) {
            helmet_def = dressedHelmet.getDefence();
        }

        return def + armour_def + shield_def + helmet_def;
    }

    public void setDEF(int def) {
        this.def = def;
    }

    public void DressUpEquipment(Equipment equipment) {
        if (equipment.getClass().equals(Weapon.class)) {
            setDressedWeapon((Weapon) equipment);
        } else if (equipment.getClass().equals(Armour.class)) {
            this.dressedArmour = (Armour) equipment;
        } else if (equipment.getClass().equals(Shield.class)) {
            this.dressedShield = (Shield) equipment;
        } else if (equipment.getClass().equals(Helmet.class)) {
            this.dressedHelmet = (Helmet) equipment;
        }

    }

    public Weapon getDressedWeapon() {
        return this.dressedWeapon;
    }

    public void setDressedWeapon(Weapon dressedWeapon) {
        this.dressedWeapon = dressedWeapon;
    }

    public Armour getDressedArmour() {
        return this.dressedArmour;
    }

    public void setDressedArmour(Armour dressedArmour) {
        this.dressedArmour = dressedArmour;
    }

    public Shield getDressedShield() {
        return this.dressedShield;
    }

    public void setDressedShield(Shield dressedShield) {
        this.dressedShield = dressedShield;
    }

    public Helmet getDressedHelmet() {
        return this.dressedHelmet;
    }

    public void setDressedHelmet(Helmet dressedHelmet) {
        this.dressedHelmet = dressedHelmet;
    }

    public List<Item> getBag() {
        return this.Bag;
    }

    public void setBag(List<Item> Bag) {
        this.Bag = Bag;
    }

}
