package unsw.loopmania.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import unsw.loopmania.model.buildings.CampfireBuilding;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Zombie;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.friendlyforces.Soldier;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private IntegerProperty hp;
    private IntegerProperty gold;
    private IntegerProperty xp;
    private List<Soldier> soldiers;
    private int atk;
    private int def;

    private Weapon dressedWeapon;
    private Armour dressedArmour;
    private Shield dressedShield;
    private Helmet dressedHelmet;

    private List<Item> Bag;
    private List<Building> buildingEntities;

    private boolean isFighting;

    public Character(PathPosition position) {
        super(position);

        hp = new SimpleIntegerProperty(300);
        gold = new SimpleIntegerProperty(100);
        xp = new SimpleIntegerProperty(0);
        soldiers = new ArrayList<Soldier>();

        atk = 5;
        def = 0;

        Bag = new ArrayList<Item>(16);
        buildingEntities = new ArrayList<Building>();
    }

    /**
     * Get the HP Property
     * @return IntegerProperty
     */
    public IntegerProperty hpProperty() {
        return hp;
    }

    /**
     * Get the HP Percentage Property
     * @return StringProperty
     */
    public StringProperty hpPercentageProperty() {
        Double hpPercentage = Double.valueOf(getHP()) / 300 * 100;
        String formattedHP = String.format("%.2f", hpPercentage);

        return new SimpleStringProperty(formattedHP + "%");
    }

    /**
     * Get the value of HP
     * @return int
     */
    public int getHP() {
        return hp.get();
    }

    /**
     * Set the value of HP
     * @param hp
     */
    public void setHP(int hp) {
        if (hp > 300) {
            this.hp.set(300);
        } else if (hp < 0) {
            this.hp.set(0);
        } else {
            this.hp.set(hp);
        }
    }

    /**
     * Get the Gold Property
     * @return IntegerProperty
     */
    public IntegerProperty goldProperty() {
        return gold;
    }

    /**
     * Get the value of Gold
     * @return int
     */
    public int getGold() {
        return gold.get();
    }

    /**
     * Set the value of Gold
     * @param gold
     */
    public void setGold(int gold) {
        this.gold.set(gold);
    }

    /**
     * Get the XP Property
     * @return IntegerProperty
     */
    public IntegerProperty xpProperty() {
        return xp;
    }

    /**
     * Get the value of XP
     * @return int
     */
    public int getXP() {
        return xp.get();
    }

    /**
     * Set the value of XP
     * @param xp
     */
    public void setXP(int xp) {
        this.xp.set(xp);
    }

    public List<Soldier> getSoldiers() {
        return this.soldiers;
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

        if (isInCampfireRadius()) {
            return 2 * (atk + weaponAtk + helmetAtk);
        }

        return atk + weaponAtk + helmetAtk;
    }

    /**
     * Get the Enemy Attack
     * @param enemy
     * @return int
     */
    public int getATK(Enemy enemy) {

        int weaponAtk = 0;
        int helmetAtk = 0;

        if (dressedWeapon != null) {
            weaponAtk = dressedWeapon.getAttack(enemy);
        }

        if (dressedHelmet != null) {
            helmetAtk = dressedHelmet.getAttack(enemy);
        }

        if (isInCampfireRadius()) {
            return 2 * (atk + weaponAtk + helmetAtk);
        }

        return atk + weaponAtk + helmetAtk;

    }

    /**
     * Set the Attack
     * @param atk
     */
    public void setATK(int atk) {
        this.atk = atk;
    }

    /**
     * Get the Defence
     * @return int
     */
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

    /**
    * Get the Defence
    * @return int
    */
    public int getDEF(Enemy enemy) {
        int armourDef = 0;
        int shieldDef = 0;
        int helmetDef = 0;

        if (dressedArmour != null) {
            armourDef = dressedArmour.getDefence(enemy);
        }

        if (dressedShield != null) {
            shieldDef = dressedShield.getDefence(enemy);
        }

        if (dressedHelmet != null) {
            helmetDef = dressedHelmet.getDefence(enemy);
        }

        return def + armourDef + shieldDef + helmetDef;
    }

    /**
     * Set the Defence
     * @param def
     */
    public void setDEF(int def) {
        this.def = def;
    }

    /**
     * Dress up the Equipment
     * @param equipment
     */
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

    /**
     * Get the dressed Weapon
     * @return Weapon
     */
    public Weapon getDressedWeapon() {
        return this.dressedWeapon;
    }

    /**
     * Set the dressed Weapon
     * @param dressedWeapon
     */
    public void setDressedWeapon(Weapon dressedWeapon) {
        this.dressedWeapon = dressedWeapon;
    }

    /**
     * Get the dressed Armour
     * @return Armour
     */
    public Armour getDressedArmour() {
        return this.dressedArmour;
    }

    /**
     * Set the dressed Armour
     * @param dressedArmour
     */
    public void setDressedArmour(Armour dressedArmour) {
        this.dressedArmour = dressedArmour;
    }

    /**
     * Get the dressed Shield
     * @return Shield
     */
    public Shield getDressedShield() {
        return this.dressedShield;
    }

    /**
     * Set the dressed Shield
     * @param dressedShield
     */
    public void setDressedShield(Shield dressedShield) {
        this.dressedShield = dressedShield;
    }

    /**
     * Get the dressed Helmet
     * @return Helmet
     */
    public Helmet getDressedHelmet() {
        return this.dressedHelmet;
    }

    /**
     * Set the dressed Helmet
     * @param dressedHelmet
     */
    public void setDressedHelmet(Helmet dressedHelmet) {
        this.dressedHelmet = dressedHelmet;
    }

    /**
     * Get the Bag
     * @return List<Item>
     */
    public List<Item> getBag() {
        return this.Bag;
    }

    /**
     * Set the Bag
     * @param Bag
     */
    public void setBag(List<Item> Bag) {
        this.Bag = Bag;
    }

    /**
     * Set the Building Entitiy
     * @param buildings
     */
    public void setBuildingEntities(List<Building> buildings) {
        this.buildingEntities = buildings;
    }

    /**
     * Check whether the Character is in the Campfire Radius
     * @return boolean
     */
    private boolean isInCampfireRadius() {
        for (Building b : buildingEntities) {
            if (b instanceof CampfireBuilding) {
                CampfireBuilding campfire = new CampfireBuilding(new SimpleIntegerProperty(b.getX()),
                        new SimpleIntegerProperty(b.getY()));
                if (Math.pow((getX() - b.getX()), 2) + Math.pow((getY() - b.getY()), 2) < Math
                        .pow(campfire.getBattleRadius(), 2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Use the Health Potion
     */
    public void useHealthPotion() {
        setHP(getHP() + 50);
    }

    public void setIsFighting(boolean status) {
        isFighting = status;
    }

    public boolean isFighting() {
        return isFighting;
    }

}
