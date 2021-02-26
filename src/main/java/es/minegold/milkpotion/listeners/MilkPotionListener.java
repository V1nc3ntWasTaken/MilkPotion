package es.minegold.milkpotion.listeners;

import es.minegold.milkpotion.MilkPotion;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MilkPotionListener implements Listener {
    private final String POTION_NAME = MilkPotion.get().getConfig().getString("potions.MILK_POTION_NAME");

    private final String COLORED_POTION_NAME = ChatColor.translateAlternateColorCodes('&', Objects. < String > requireNonNull(this.POTION_NAME));

    private final String TARGET_REMOVED_EFFECTS = MilkPotion.get().getConfig().getString("messages.TARGET_REMOVED_EFFECTS");

    private final String COLORED_TARGET_REMOVED_EFFECTS = ChatColor.translateAlternateColorCodes('&', Objects. < String > requireNonNull(this.TARGET_REMOVED_EFFECTS));

    @EventHandler
    public void onPlayerHitOtherPlayer(PotionSplashEvent e) {
        if (e.getEntity().getItem().hasItemMeta() &&
            e.getEntity().getItem().getItemMeta().getDisplayName().equalsIgnoreCase(this.COLORED_POTION_NAME)) {
            e.setCancelled(true);
            for (LivingEntity en: e.getAffectedEntities()) {
                for (PotionEffect effect: en.getActivePotionEffects())
                    en.removePotionEffect(PotionEffectType.BAD_OMEN);
                en.removePotionEffect(PotionEffectType.BLINDNESS);
                en.removePotionEffect(PotionEffectType.CONFUSION);
                en.removePotionEffect(PotionEffectType.HARM);
                en.removePotionEffect(PotionEffectType.HUNGER);
                en.removePotionEffect(PotionEffectType.POISON);
                en.removePotionEffect(PotionEffectType.SLOW);
                en.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                en.removePotionEffect(PotionEffectType.SLOW_FALLING);
                en.removePotionEffect(PotionEffectType.UNLUCK);
                en.removePotionEffect(PotionEffectType.WEAKNESS);
                en.removePotionEffect(PotionEffectType.WITHER);
                en.sendMessage(this.COLORED_TARGET_REMOVED_EFFECTS);
            }
        }
    }
}
