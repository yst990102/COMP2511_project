package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.model.LoopManiaWorld;

import unsw.loopmania.model.rareItems.TheOneRing;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.potions.HealthPotion;
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.equipments.weapons.*;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class TrivialTest {

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
    }

    @Test
    public void TestBasicHelmet() {
        
        BasicHelmet a = new BasicHelmet(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        assertEquals(a.getDefence(), 2);
        assertEquals(a.getAttack(), -2);
        assertEquals(a.getEnemyAttackDecrease(), 2);
    }

    @Test
    public void TestWeapon() {
        
        Staff a = new Staff(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Stake b = new Stake(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        Sword c = new Sword(new SimpleIntegerProperty(1), new SimpleIntegerProperty(3));

        assertEquals(a.getAttack(), 2);
        assertEquals(b.getAttack(), 3);
        assertEquals(b.getAttackToVampire(), 8);

        assertEquals(c.getAttack(), 5);
        assertEquals(b.getAttackToVampire(), 8);

    }

}
