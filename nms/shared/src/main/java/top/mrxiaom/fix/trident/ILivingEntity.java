package top.mrxiaom.fix.trident;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface ILivingEntity {
    @Nullable
    ItemStack getActiveItem(LivingEntity entity);
}
