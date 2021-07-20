package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;

import unsw.loopmania.model.Character;
import unsw.loopmania.model.Item;

// shield
import unsw.loopmania.model.equipments.shields.BasicShield;
import unsw.loopmania.model.Shield;
import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;
// armour
import unsw.loopmania.model.equipments.armours.BasicArmour;
import unsw.loopmania.model.Armour;

// helmet
import unsw.loopmania.model.equipments.helmets.BasicHelmet;
import unsw.loopmania.model.Helmet;

// weapon
import unsw.loopmania.model.equipments.weapons.Staff;
import unsw.loopmania.model.equipments.weapons.Stake;
import unsw.loopmania.model.equipments.weapons.Sword;

public class CharacterTest {
    @Test
    public void CharacterPropertyTest() {
        Character character = new Character(null);

        assertEquals(character.hpProperty().get(), 300);
        assertEquals(character.goldProperty().get(), 100);
        assertEquals(character.xpProperty().get(), 0);
        assertEquals(character.getSoldiers().size(), 0);

        assertEquals(character.getATK(), 5);
        assertEquals(character.getDEF(), 0);

        assertEquals(character.getBag(), new ArrayList<Item>(16));

        assertEquals(character.hpPercentageProperty().get(), "100.00%");

    }

    @Test
    public void DressUpTest() {
        Character character = new Character(null);

        // weapon
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // armour
        Armour armour = new BasicArmour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // helmet
        Helmet helmet = new BasicHelmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        // shield
        Shield shield = new BasicShield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.setDressedWeapon(sword);
        assertEquals(character.getATK(), 10);
        assertEquals(character.getDEF(), 0);

        character.setDressedArmour(armour);
        assertEquals(character.getATK(), 10);
        assertEquals(character.getDEF(), 5);

        character.setDressedHelmet(helmet);
        assertEquals(character.getATK(), 8);
        assertEquals(character.getDEF(), 7);

        character.setDressedShield(shield);
        assertEquals(character.getATK(), 8);
        assertEquals(character.getDEF(), 9);

        character.setDressedWeapon(staff);
        assertEquals(character.getATK(), 5);
        assertEquals(character.getDEF(), 9);

        character.setDressedWeapon(stake);
        assertEquals(character.getATK(), 6);
        assertEquals(character.getDEF(), 9);

    }

    @Test
    public void BagNormalTest() {
        Character character = new Character(null);

        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        character.getBag().add(sword);
        character.getBag().add(sword);

        List<Item> ItemList = Arrays.asList(sword, sword);

        assertEquals(character.getBag(), ItemList);

    }

    @Test
    public void AttackTest() {
        Slug slug = new Slug(null);
        Zombie zombie = new Zombie(null);
        Vampire vampire = new Vampire(null);

        Character character = new Character(null);

        assertEquals(character.getATK(slug), 5);
        assertEquals(character.getATK(zombie), 5);
        assertEquals(character.getATK(vampire), 5);

        character.setDressedWeapon(new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        assertEquals(character.getATK(slug), 10);
        assertEquals(character.getATK(zombie), 10);
        assertEquals(character.getATK(vampire), 10);

        character.setDressedWeapon(new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        assertEquals(character.getATK(slug), 8);
        assertEquals(character.getATK(zombie), 8);
        assertEquals(character.getATK(vampire), 13);

    }
}
