package unsw.loopmania.model.enemies;

import java.io.File;
import java.util.Random;

import org.javatuples.Pair;

import javafx.scene.image.Image;
import unsw.loopmania.model.Enemy;
import unsw.loopmania.model.PathPosition;

/**
 * a basic form of enemy in the world
 */
public class Zombie extends Enemy {
    
    private int InfectionPercentage = 100;

    public Zombie(PathPosition position) {
        super(position);

        this.hp = 20;
        this.attack = 10;
        this.speed = 2;
        this.battleRadius = 2;
        this.supportRadius = 2;
        this.criticalPercentage = 20;
        this.goldWhenKilled = 100;
        this.expWhenKilled = 200;
    }

    public Pair<Integer, Boolean> getAttackByCritical() {
        int randomInt = new Random().nextInt(100);

        boolean isCriticalBite = false;
        if (randomInt < criticalPercentage) {
            isCriticalBite = true;
        }
        return new Pair<>(super.getAttack(), isCriticalBite);
    }

    @Override
    public void setHP(int newHP) {
        if (newHP > 20) {
            this.hp = 20;
        } else if (newHP < 0) {
            this.hp = 0;
        } else {
            this.hp = newHP;
        }
        return;
    }

    public int getInfectionPercentage() {
        return this.InfectionPercentage;
    }

}
