package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_18_R2 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.world.item.ItemStack item = ((CraftLivingEntity) entity).getHandle().eO();
        return item.b() ? null : CraftItemStack.asCraftMirror(item);
    }
}
