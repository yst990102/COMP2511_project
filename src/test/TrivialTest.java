package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.controller.StoreController.ITEM_TYPE;
import unsw.loopmania.model.LoopManiaWorld;

import unsw.loopmania.model.rareItems.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.weapons.*;
import unsw.loopmania.strategy.*;
import unsw.loopmania.controller.*;
import unsw.loopmania.model.Store;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.coins.*;
import unsw.loopmania.model.enemies.boss.*;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class TrivialTest {

    // @FXML
    // Text description;

    @Test
    public void TestTheOneRing() {

        TheOneRing a = new TheOneRing(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getPrice(), 500);
    }

    @Test
    public void TestBasicArmour() {

        BasicArmour a = new BasicArmour(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getDefence(), 5);
        assertEquals(a.getDamageReducePercentage(), 50);
        Slug slug = new Slug(null);
        assertEquals(a.getDefence(slug), 5);
    }

    @Test
    public void TestHealthPotion() {

        HealthPotion a = new HealthPotion(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getPrice(), 200);
        assertEquals(a.getHealthRecovered(), 50);
    }

    @Test
    public void TestBasicShield() {

        BasicShield a = new BasicShield(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getDefence(), 2);
        assertEquals(a.getCriticalPercentageDecrease(), 60);
        Slug slug = new Slug(null);
        assertEquals(a.getDefence(slug), 2);
    }

    @Test
    public void TestBasicHelmet() {

        BasicHelmet a = new BasicHelmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getDefence(), 2);
        assertEquals(a.getAttack(), -2);
        assertEquals(a.getEnemyAttackDecrease(), 2);
        Slug slug = new Slug(null);
        assertEquals(a.getDefence(slug), 2);
        assertEquals(a.getAttack(slug), -2);
    }

    @Test
    public void TestWeapon() {

        Staff a = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Stake b = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        Sword c = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(3));
        Vampire vampire = new Vampire(null);
        Slug slug = new Slug(null);
        Zombie zombie = new Zombie(null);

        assertEquals(a.getAttack(), 2);
        assertEquals(a.getAttack(slug), 2);
        assertEquals(a.getAttack(vampire), 2);
        assertEquals(a.getAttack(zombie), 2);
        assertEquals(b.getAttack(), 3);
        assertEquals(b.getAttack(vampire), 8);
        assertEquals(b.getAttack(slug), 3);
        assertEquals(b.getAttack(zombie), 3);

        assertEquals(c.getAttack(), 5);

    }

    @Test
    public void TestMode() {
        SurvivalModeStrategy a = new SurvivalModeStrategy();
        BerserkerModeStrategy b = new BerserkerModeStrategy();
        StandardModeStrategy c = new StandardModeStrategy();

        Text description = new Text("");

        assertEquals(a.satisfyItemBuyConstraint(1, 0, description, ITEM_TYPE.HEALTH_POTION), false);
        assertEquals(a.satisfyItemBuyConstraint(0, 0, description, ITEM_TYPE.HEALTH_POTION), true);
        assertEquals(a.satisfyItemBuyConstraint(0, 2, description, ITEM_TYPE.ARMOUR), true);

        assertEquals(b.satisfyItemBuyConstraint(0, 1, description, ITEM_TYPE.SHIELD), false);
        assertEquals(b.satisfyItemBuyConstraint(0, 1, description, ITEM_TYPE.HELMET), false);
        assertEquals(b.satisfyItemBuyConstraint(0, 1, description, ITEM_TYPE.ARMOUR), false);
        assertEquals(b.satisfyItemBuyConstraint(0, 0, description, ITEM_TYPE.ARMOUR), true);
        assertEquals(b.satisfyItemBuyConstraint(1, 1, description, ITEM_TYPE.HEALTH_POTION), true);

        assertEquals(c.satisfyItemBuyConstraint(1, 1, description, ITEM_TYPE.ARMOUR), true);

    }

    @Test
    public void TestStore() {

        Store store = new Store();

        assertEquals(store.getStoreItems().size(), 7);
        assertEquals(store.getHeroItems().size(), 0);
    }

    @Test
    public void TestDoggieCoin() {

        DoggieCoin coin1 = new DoggieCoin(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), true, false);
        DoggieCoin coin2 = new DoggieCoin(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), true, true);
        DoggieCoin coin3 = new DoggieCoin(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), false, false);

        assertEquals(coin1.getPrice()>200, true);
        assertEquals(coin2.getPrice()<200, true);
        assertEquals(coin3.getPrice()>10&&coin3.getPrice()<61, true);

    }

    @Test
    public void TestAnduril() {

        Anduril a = new Anduril(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Vampire vampire = new Vampire(null);
        Doggie dog = new Doggie(null);
        assertEquals(a.getPrice(), 500);
        assertEquals(a.getAttack(), 8);
        assertEquals(a.getAttack(vampire), 8);
        assertEquals(a.getAttack(dog), 24);
    }

    @Test
    public void TestTreeStump() {

        TreeStump a = new TreeStump(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Vampire vampire = new Vampire(null);
        Doggie dog = new Doggie(null);
        assertEquals(a.getPrice(), 500);
        assertEquals(a.getDefence(), 8);
        assertEquals(a.getDefence(vampire), 8);
        assertEquals(a.getDefence(dog), 24);
        assertEquals(a.getCriticalPercentageDecrease(), 0);
    }

}
