package unsw.loopmania.strategy;

import javafx.scene.text.Text;
import unsw.loopmania.controller.StoreController.ITEM_TYPE;

public class StandardModeStrategy implements ModeStrategy {
    @Override
    public boolean satisfyItemBuyConstraint(int numHealthPotionBought, int numProtectiveGearBought, Text description, ITEM_TYPE itemType) {
        return true;
    }
}
