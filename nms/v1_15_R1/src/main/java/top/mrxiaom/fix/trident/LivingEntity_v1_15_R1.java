package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_15_R1 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.server.v1_15_R1.ItemStack nmsItem = ((CraftLivingEntity) entity).getHandle().dD();
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
}
