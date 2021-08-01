package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;
import unsw.loopmania.model.enemies.boss.*;

public class EnemyTest {

    // Enemy Property Test
    @Test
    public void SlugPropertyTest() {
        Slug slug = new Slug(null);

        assertEquals(slug.hp, 15);
        assertEquals(slug.attack, 5);
        assertEquals(slug.speed, 5);
        assertEquals(slug.battleRadius, 1);
        assertEquals(slug.supportRadius, 1);

        assertEquals(slug.goldWhenKilled, 50);
        assertEquals(slug.expWhenKilled, 100);

        slug.setHP(20);
        assertEquals(slug.hp, 15);
        slug.setHP(-1);
        assertEquals(slug.hp, 0);
        slug.setHP(10);
        assertEquals(slug.hp, 10);

    }

    @Test
    public void ZombiePropertyTest() {
        Zombie zombie = new Zombie(null);

        assertEquals(zombie.hp, 20);
        assertEquals(zombie.attack, 10);
        assertEquals(zombie.speed, 2);
        assertEquals(zombie.battleRadius, 2);
        assertEquals(zombie.supportRadius, 2);

        assertEquals(zombie.criticalPercentage, 20);

        assertEquals(zombie.goldWhenKilled, 100);
        assertEquals(zombie.expWhenKilled, 200);

        zombie.setHP(21);
        assertEquals(zombie.hp, 20);
        zombie.setHP(-1);
        assertEquals(zombie.hp, 0);
        zombie.setHP(10);
        assertEquals(zombie.hp, 10);
        assertEquals(zombie.getInfectionPercentage(), 100);


    }

    @Test
    public void VampirePropertyTest() {
        Vampire vampire = new Vampire(null);

        assertEquals(vampire.hp, 25);
        assertEquals(vampire.attack, 20);
        assertEquals(vampire.speed, 3);
        assertEquals(vampire.battleRadius, 2);
        assertEquals(vampire.supportRadius, 3);

        assertEquals(vampire.criticalPercentage, 20);
        assertEquals(vampire.criticalExtraDamage, 10);

        assertEquals(vampire.goldWhenKilled, 200);
        assertEquals(vampire.expWhenKilled, 300);

        vampire.setHP(30);
        assertEquals(vampire.hp, 25);
        vampire.setHP(-1);
        assertEquals(vampire.hp, 0);
        vampire.setHP(10);
        assertEquals(vampire.hp, 10);

    }

    @Test
    public void DoggieTest() {
        Doggie dog = new Doggie(null);
        assertEquals(dog.getStun_percentage(), 20);
        assertEquals(dog.getStun_round(), 2);
        
    }


    @Test
    public void ElanMuskeTest() {
        Doggie dog = new Doggie(null);
        assertEquals(dog.getStun_percentage(), 20);
        assertEquals(dog.getStun_round(), 2);
        
    }

}