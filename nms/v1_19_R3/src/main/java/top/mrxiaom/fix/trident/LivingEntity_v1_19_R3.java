package top.mrxiaom.fix.trident;

import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class LivingEntity_v1_19_R3 implements ILivingEntity {
    @Nullable
    @Override
    public ItemStack getActiveItem(LivingEntity entity) {
        net.minecraft.world.item.ItemStack item = ((CraftLivingEntity) entity).getHandle().fg();
        return item.b() ? null : CraftItemStack.asCraftMirror(item);
    }
}
