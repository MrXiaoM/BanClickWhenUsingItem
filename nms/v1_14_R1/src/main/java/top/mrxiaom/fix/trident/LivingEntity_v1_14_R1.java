package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_14_R1 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.server.v1_14_R1.ItemStack item = ((CraftLivingEntity) entity).getHandle().dl();
        return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
    }
}
