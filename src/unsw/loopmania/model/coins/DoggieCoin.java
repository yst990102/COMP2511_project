package unsw.loopmania.model.coins;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.model.Item;

/**
 * Description:
 * A revolutionary asset type, which randomly 
 * fluctuates in sellable price to an extraordinary extent. 
 * Can sell at shop
 * 
 * Where can obtain:
 * Obtained when defeat Doggie
 */
public class DoggieCoin extends Item {

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y, boolean hasMuskeSpawn) {
        super(x, y);

        String description = "===== DoggieCoin =====\n" + "A revolutionary asset type.\nCan sell at shop🐶\n";

        setDescription(description);

        int price = 0;
        if (hasMuskeSpawn) {
            price = 200 + new Random(System.currentTimeMillis()).nextInt(100);
        } else {
            price = 50 + new Random(System.currentTimeMillis()).nextInt(50);
        }

        setPrice(price);

    }

}
