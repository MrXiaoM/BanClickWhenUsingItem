package top.mrxiaom.fix.trident;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class NMS {
    private static ILivingEntity livingEntity;
    private static boolean loaded;
    private static final Map<String, String> VERSION_TO_REVISION = new HashMap<String, String>() {{
        put("1.20", "v1_20_R1");
        put("1.20.1", "v1_20_R1");
        put("1.20.2", "v1_20_R2");
        put("1.20.3", "v1_20_R3");
        put("1.20.4", "v1_20_R3");
        put("1.20.5", "v1_20_R4");
        put("1.20.6", "v1_20_R4");
        put("1.21", "v1_21_R1");
        put("1.21.1", "v1_21_R1");
        put("1.21.2", "v1_21_R2");
        put("1.21.3", "v1_21_R2");
        put("1.21.4", "v1_21_R3");
    }};

    public static boolean init(Logger logger) {
        if (loaded) return true;
        for (Method method : LivingEntity.class.getDeclaredMethods()) {
            if (method.getName().equals("getActiveItem")) {
                livingEntity = new LivingEntity_Paper();
                logger.info("Found Paper interface method 'LivingEntity#getActiveItem()'.");
                logger.info("NMS support 'Paper' loaded!");
                return loaded = true;
            }
        }
        String nmsVersion;
        // Thanks https://github.com/tr7zw/Item-NBT-API
        try {
            String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            logger.info("Found Minecraft: " + ver + "! Trying to find NMS support");
            nmsVersion = ver;
        } catch (Exception e) {
            logger.info("Found Minecraft: " + Bukkit.getServer().getBukkitVersion().split("-")[0] + "! Trying to find NMS support");
            String ver = Bukkit.getServer().getBukkitVersion().split("-")[0];
            nmsVersion = VERSION_TO_REVISION.getOrDefault(ver, "null");
        }
        try {
            Class<?> classLivingEntity = Class.forName("top.mrxiaom.fix.trident.LivingEntity_" + nmsVersion);
            livingEntity = (ILivingEntity) classLivingEntity.getConstructor().newInstance();
            loaded = true;
        } catch (Exception ignored) {
        }

        if (loaded) {
            logger.info("NMS support '" + nmsVersion + "' loaded!");
        } else {
            logger.warning("This Server-Version(" + Bukkit.getServer().getBukkitVersion() + ") is not supported by this plugin!");
        }

        return loaded;
    }

    @Nullable
    public static ItemStack getActiveItem(LivingEntity entity) {
        return livingEntity.getActiveItem(entity);
    }
}
