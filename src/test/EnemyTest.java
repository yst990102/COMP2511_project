package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.loopmania.model.enemies.Slug;
import unsw.loopmania.model.enemies.Vampire;
import unsw.loopmania.model.enemies.Zombie;

public class EnemyTest {

    // Enemy Property Test
    @Test
    public void SlugPropertyTest() {
        Slug slug = new Slug(null);

        assertEquals(slug.hp, 15);
        assertEquals(slug.attack, 5);
        assertEquals(slug.speed, 5);
        assertEquals(slug.battleRadius, 2);
        assertEquals(slug.supportRadius, 2);

        assertEquals(slug.goldWhenKilled, 50);
        assertEquals(slug.expWhenKilled, 100);

    }

    @Test
    public void ZombiePropertyTest() {
        Zombie zombie = new Zombie(null);

        assertEquals(zombie.hp, 20);
        assertEquals(zombie.attack, 10);
        assertEquals(zombie.speed, 2);
        assertEquals(zombie.battleRadius, 3);
        assertEquals(zombie.supportRadius, 3);

        assertEquals(zombie.criticalPercentage, 20);

        assertEquals(zombie.goldWhenKilled, 100);
        assertEquals(zombie.expWhenKilled, 200);

    }

    @Test
    public void VampirePropertyTest() {
        Vampire vampire = new Vampire(null);

        assertEquals(vampire.hp, 25);
        assertEquals(vampire.attack, 20);
        assertEquals(vampire.speed, 3);
        assertEquals(vampire.battleRadius, 5);
        assertEquals(vampire.supportRadius, 5);

        assertEquals(vampire.criticalPercentage, 20);
        assertEquals(vampire.criticalExtraDamage, 10);

        assertEquals(vampire.goldWhenKilled, 200);
        assertEquals(vampire.expWhenKilled, 300);

    }
}