package unsw.loopmania.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import unsw.loopmania.model.buildings.Building;
import unsw.loopmania.model.buildings.CampfireBuilding;

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
    private List<Building> buildingEntities;

    public Character(PathPosition position) {
        super(position);

        hp = new SimpleIntegerProperty(300);
        gold = new SimpleIntegerProperty(100);
        xp = new SimpleIntegerProperty(0);
        numSoldier = new SimpleIntegerProperty(0);

        atk = 5;
        def = 0;

        Bag = new ArrayList<Item>(16);
        buildingEntities = new ArrayList<Building>();
    }

    public IntegerProperty hpProperty() {
        return hp;
    }

    public StringProperty hpPercentageProperty() {
        Double hpPercentage = Double.valueOf(getHP()) / 300 * 100;
        String formattedHP = String.format("%.2f", hpPercentage);

        return new SimpleStringProperty(formattedHP + "%");
    }

    public int getHP() {
        return hp.get();
    }

    public void setHP(int hp) {
        if (hp < 300) {
            this.hp.set(hp);
        } else {
            this.hp.set(300);
        }
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
        int weaponAtk = 0;
        int helmetAtk = 0;

        if (dressedWeapon != null) {
            weaponAtk = dressedWeapon.getAttack();
        }

        if (dressedHelmet != null) {
            helmetAtk = dressedHelmet.getAttack();
        }

        if (isInTowerRadius()) {
            return 2 * (atk + weaponAtk + helmetAtk);
        }

        return atk + weaponAtk + helmetAtk;
    }

    public void setATK(int atk) {
        this.atk = atk;
    }

    public int getDEF() {
        int armourDef = 0;
        int shieldDef = 0;
        int helmetDef = 0;

        if (dressedArmour != null) {
            armourDef = dressedArmour.getDefence();
        }

        if (dressedShield != null) {
            shieldDef = dressedShield.getDefence();
        }

        if (dressedHelmet != null) {
            helmetDef = dressedHelmet.getDefence();
        }

        return def + armourDef + shieldDef + helmetDef;
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

    public void setBuildingEntities(List<Building> buildings) {
        this.buildingEntities = buildings;
    }

    private boolean isInTowerRadius() {
        for (Building b : buildingEntities) {
            if (b instanceof CampfireBuilding) {
                CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(b.getX()), new SimpleIntegerProperty(b.getY()));
                if (Math.pow((getX() - b.getX()), 2) + Math.pow((getY() - b.getY()), 2) < Math.pow(campfire.getBattleRadius(), 2)) {
                    return true;
                }     
            }
        }
        return false;
    }

    public void useHealthPotion() {
        setHP(getHP() + 50);
    }
}
