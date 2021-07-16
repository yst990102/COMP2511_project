package unsw.loopmania.strategy;

import javafx.scene.text.Text;
import unsw.loopmania.controller.StoreController.ITEM_TYPE;

public class SurvivalModeStrategy implements ModeStrategy {
    @Override
    public boolean satisfyItemBuyConstraint(int numHealthPotionBought, int numProtectiveGearBought, Text description, ITEM_TYPE itemType) {
        if (itemType.equals(ITEM_TYPE.HEALTH_POTION)) {
            if (numHealthPotionBought >= 1) {
                description.setText("You can only purchase 1 health potion in survival mode!");
                return false;
            }
        }
        return true;
    }
}
