package unsw.loopmania.strategy;

import javafx.scene.text.Text;
import unsw.loopmania.controller.StoreController.ITEM_TYPE;

public interface ModeStrategy {
    public boolean satisfyItemBuyConstraint(int numHealthPotionBought, int numProtectiveGearBought, Text description,
            ITEM_TYPE itemType);
}
