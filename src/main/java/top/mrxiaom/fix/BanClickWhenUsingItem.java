package top.mrxiaom.fix;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BanClickWhenUsingItem extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        HumanEntity entity = e.getWhoClicked();
        ItemStack item = entity.getActiveItem();
        if (item != null && !item.getType().equals(Material.AIR)) {
            // entity.sendMessage("fixed exploit: " + item.getType().name());
            e.setCancelled(true);
        }
    }

}
