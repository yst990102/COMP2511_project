package unsw.loopmania.model;

import java.util.ArrayList;
import java.util.List;

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
        int weapon_atk = 0;
        int helmet_atk = 0;
        
        if (Dressed_weapon != null){
            weapon_atk = Dressed_weapon.getAttack();
        }
        
        if (Dressed_helmet != null){
            helmet_atk = Dressed_helmet.getAttack();
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

        if (Dressed_armour != null){
            armour_def = Dressed_armour.getDefence();
        }

        if (Dressed_shield != null){
            shield_def = Dressed_shield.getDefence();
        }

        if (Dressed_helmet != null){
            helmet_def = Dressed_helmet.getDefence();
        }


        return def + armour_def + shield_def + helmet_def;
    }

    public void setDEF(int def) {
        this.def = def;
    }

    public void DressUpEquipment(Equipment equipment){
        if (equipment.getClass().equals(Weapon.class)){
            this.Dressed_weapon = (Weapon)equipment;
        }else if (equipment.getClass().equals(Armour.class)){
            this.Dressed_armour = (Armour)equipment;
        }else if (equipment.getClass().equals(Shield.class)){
            this.Dressed_shield = (Shield)equipment;
        }else if (equipment.getClass().equals(Helmet.class)){
            this.Dressed_helmet = (Helmet)equipment;
        }

    }
}
