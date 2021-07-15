package unsw.loopmania.model;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword; 

public class Store {
    
    private List<Item> storeItems;
    private List<Item> heroItems;

    public Store() {
        this.storeItems = new ArrayList<>();
        this.heroItems = new ArrayList<>();
        initStoreInventory();
    }

    private void initStoreInventory() {
        storeItems.add(new BasicArmour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new BasicHelmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new BasicShield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        storeItems.add(new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
    }

    public List<Item> getStoreItems() {
        return storeItems;
    }

    public List<Item> getHeroItems() {
        return heroItems;
    }

    public void setHeroItems(List<Item> items) {
        this.heroItems = items;
    }
    
}
