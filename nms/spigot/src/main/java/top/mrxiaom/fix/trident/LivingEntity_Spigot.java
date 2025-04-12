package top.mrxiaom.fix.trident;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class LivingEntity_Spigot implements ILivingEntity {
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        return entity.getItemInUse();
    }
}
