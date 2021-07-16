package unsw.loopmania.strategy;

import javafx.scene.text.Text;
import unsw.loopmania.controller.StoreController.ITEM_TYPE;

public class BerserkerModeStrategy implements ModeStrategy {
    @Override
    public boolean satisfyItemBuyConstraint(int numHealthPotionBought, int numProtectiveGearBought, Text description, ITEM_TYPE itemType) {
        if (itemType.equals(ITEM_TYPE.SHIELD) || itemType.equals(ITEM_TYPE.HELMET) || itemType.equals(ITEM_TYPE.ARMOUR)) {
            if (numProtectiveGearBought >= 1) {
                description.setText("You can only purchase 1 protective gear in berserker mode!");
                return false;
            }
        }
        return true;
    }
}
