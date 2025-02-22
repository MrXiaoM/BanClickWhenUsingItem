package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_20_R4.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R4.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_20_R4 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.world.item.ItemStack nmsItem = ((CraftLivingEntity) entity).getHandle().fx();
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}
