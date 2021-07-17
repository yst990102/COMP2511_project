package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.loopmania.model.LoopManiaWorld;

import unsw.loopmania.model.rareItems.TheOneRing;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.potions.HealthPotion;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class TrivialTest {
    @Test
    public void blahTest() {
        assertEquals("a", "a");
    }

    @Test
    public void blahTest2() {
        // LoopManiaWorld d = new LoopManiaWorld(1, 2, new ArrayList<>());
        // assertEquals(d.getWidth(), 1);
        assertEquals("a", "a");
    }

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
}
