package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_13_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_13_R2 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.server.v1_13_R2.ItemStack nmsItem = ((CraftLivingEntity) entity).getHandle().cW();
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}
