package mod.emt.offlawn.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class OLLItemFood extends ItemFood {
    public int itemUseDuration;
    public boolean alwaysEdible;

    public OLLItemFood(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
    }

    public OLLItemFood(int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
        super(amount, saturation, isWolfFood);
        itemUseDuration = eatingSpeed; // 32 by default
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        if (itemUseDuration == 0) {
            return 32;
        }

        return itemUseDuration;
    }

    public OLLItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
}
