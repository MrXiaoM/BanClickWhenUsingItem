package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_16_R3 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.server.v1_16_R3.ItemStack item = ((CraftLivingEntity) entity).getHandle().getActiveItem();
        return item.isEmpty() ? null : CraftItemStack.asCraftMirror(item);
    }
}
