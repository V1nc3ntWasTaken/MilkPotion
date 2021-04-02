package es.minegold.milkpotion.listeners;

import es.minegold.milkpotion.MilkPotion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public class MilkPotionListener implements Listener {

    List<String> protectedPlayers = new ArrayList<>();

    private static MilkPotion plugin;

    public MilkPotionListener(MilkPotion plugin) {
        MilkPotionListener.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final String POTION_NAME = MilkPotion.get().getConfig().getString("potions.MILK_POTION_NAME");
    private final String COLORED_POTION_NAME = translateAlternateColorCodes('&', Objects.requireNonNull(POTION_NAME));

    private final String TARGET_REMOVED_EFFECTS = MilkPotion.get().getConfig().getString("messages.TARGET_REMOVED_EFFECTS");
    private final String TARGET_PREVENT_EFFECT = MilkPotion.get().getConfig().getString("messages.TARGET_PREVENT_EFFECT");
    private final String TARGET_REMOVED_BAD_EFFECTS = MilkPotion.get().getConfig().getString("messages.TARGET_REMOVED_BAD_EFFECTS");
    private final String TARGET_PREVENT_BAD_EFFECT = MilkPotion.get().getConfig().getString("messages.TARGET_PREVENT_BAD_EFFECT");
    private final String TIMER_MESSAGE = MilkPotion.get().getConfig().getString("messages.TIMER_MESSAGE");
    private final String TIMER_BAD_MESSAGE = MilkPotion.get().getConfig().getString("messages.TIMER_BAD_MESSAGE");
    private final String COLORED_TARGET_REMOVED_EFFECTS = translateAlternateColorCodes('&', Objects.requireNonNull(TARGET_REMOVED_EFFECTS));
    private final String COLORED_TARGET_PREVENT_EFFECT = translateAlternateColorCodes('&', Objects.requireNonNull(TARGET_PREVENT_EFFECT));
    private final String COLORED_TARGET_REMOVED_BAD_EFFECTS = translateAlternateColorCodes('&', Objects.requireNonNull(TARGET_REMOVED_BAD_EFFECTS));
    private final String COLORED_TARGET_PREVENT_BAD_EFFECT = translateAlternateColorCodes('&', Objects.requireNonNull(TARGET_PREVENT_BAD_EFFECT));
    private final String COLORED_TIMER_MESSAGE = translateAlternateColorCodes('&', Objects.requireNonNull(TIMER_MESSAGE));
    private final String COLORED_TIMER_BAD_MESSAGE = translateAlternateColorCodes('&', Objects.requireNonNull(TIMER_BAD_MESSAGE));

    private final Integer TIMER = MilkPotion.get().getConfig().getInt("options.PROTECTION_TIMER");

    @EventHandler
    public void onPlayerShootOtherPlayer(ProjectileHitEvent e) {
        Entity ent = e.getHitEntity();
        if (ent != null) {
            if (ent instanceof Player) {
                if (MilkPotion.get().getConfig().getBoolean("options.REMOVE_ONLY_DEBUFF")) {
                    Player entity = (Player) e.getHitEntity();
                    if (e.getEntity().getType() == EntityType.ARROW) {
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            if (entity.hasPotionEffect(PotionEffectType.BAD_OMEN) || entity.hasPotionEffect(PotionEffectType.BLINDNESS) || entity.hasPotionEffect(PotionEffectType.CONFUSION) || entity.hasPotionEffect(PotionEffectType.HUNGER) || entity.hasPotionEffect(PotionEffectType.POISON) || entity.hasPotionEffect(PotionEffectType.SLOW) || entity.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || entity.hasPotionEffect(PotionEffectType.SLOW_FALLING) || entity.hasPotionEffect(PotionEffectType.UNLUCK) || entity.hasPotionEffect(PotionEffectType.WEAKNESS) || entity.hasPotionEffect(PotionEffectType.WITHER)) {
                                entity.removePotionEffect(PotionEffectType.BAD_OMEN);
                                entity.removePotionEffect(PotionEffectType.BLINDNESS);
                                entity.removePotionEffect(PotionEffectType.CONFUSION);
                                entity.removePotionEffect(PotionEffectType.HUNGER);
                                entity.removePotionEffect(PotionEffectType.POISON);
                                entity.removePotionEffect(PotionEffectType.SLOW);
                                entity.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                entity.removePotionEffect(PotionEffectType.SLOW_FALLING);
                                entity.removePotionEffect(PotionEffectType.UNLUCK);
                                entity.removePotionEffect(PotionEffectType.WEAKNESS);
                                entity.removePotionEffect(PotionEffectType.WITHER);
                                entity.sendMessage(COLORED_TARGET_PREVENT_BAD_EFFECT);
                            }
                        }, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHitOtherPlayer(PotionSplashEvent e) {

        if (e.getEntity().getItem().getItemMeta().getDisplayName().equalsIgnoreCase(COLORED_POTION_NAME))  {
            for (LivingEntity entity : e.getAffectedEntities()) {
                if (protectedPlayers.contains(entity.getName().toLowerCase())) {
                    protectedPlayers.remove(entity.getName().toLowerCase());
                    if (MilkPotion.get().getConfig().getBoolean("options.REMOVE_ONLY_DEBUFF")) {
                        if (entity.hasPotionEffect(PotionEffectType.BAD_OMEN) || entity.hasPotionEffect(PotionEffectType.BLINDNESS) || entity.hasPotionEffect(PotionEffectType.CONFUSION) || entity.hasPotionEffect(PotionEffectType.HUNGER) || entity.hasPotionEffect(PotionEffectType.POISON) || entity.hasPotionEffect(PotionEffectType.SLOW) || entity.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || entity.hasPotionEffect(PotionEffectType.SLOW_FALLING) || entity.hasPotionEffect(PotionEffectType.UNLUCK) || entity.hasPotionEffect(PotionEffectType.WEAKNESS) || entity.hasPotionEffect(PotionEffectType.WITHER)) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                entity.removePotionEffect(PotionEffectType.BAD_OMEN);
                                entity.removePotionEffect(PotionEffectType.BLINDNESS);
                                entity.removePotionEffect(PotionEffectType.CONFUSION);
                                entity.removePotionEffect(PotionEffectType.HUNGER);
                                entity.removePotionEffect(PotionEffectType.POISON);
                                entity.removePotionEffect(PotionEffectType.SLOW);
                                entity.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                entity.removePotionEffect(PotionEffectType.SLOW_FALLING);
                                entity.removePotionEffect(PotionEffectType.UNLUCK);
                                entity.removePotionEffect(PotionEffectType.WEAKNESS);
                                entity.removePotionEffect(PotionEffectType.WITHER);
                                entity.sendMessage(COLORED_TARGET_REMOVED_BAD_EFFECTS);
                            }, 1);
                        }
                        entity.sendMessage(COLORED_TIMER_BAD_MESSAGE.replace("%time%", TIMER.toString()));
                    } else {
                        for (PotionEffect effect : entity.getActivePotionEffects()) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> entity.removePotionEffect(effect.getType()), 1);
                        }
                        entity.sendMessage(COLORED_TARGET_REMOVED_EFFECTS);
                        entity.sendMessage(COLORED_TIMER_MESSAGE.replace("%time%", TIMER.toString()));
                    }
                } else {
                    if (MilkPotion.get().getConfig().getBoolean("options.REMOVE_ONLY_DEBUFF")) {
                        if (entity.hasPotionEffect(PotionEffectType.BAD_OMEN) || entity.hasPotionEffect(PotionEffectType.BLINDNESS) || entity.hasPotionEffect(PotionEffectType.CONFUSION) || entity.hasPotionEffect(PotionEffectType.HUNGER) || entity.hasPotionEffect(PotionEffectType.POISON) || entity.hasPotionEffect(PotionEffectType.SLOW) || entity.hasPotionEffect(PotionEffectType.SLOW_DIGGING) || entity.hasPotionEffect(PotionEffectType.SLOW_FALLING) || entity.hasPotionEffect(PotionEffectType.UNLUCK) || entity.hasPotionEffect(PotionEffectType.WEAKNESS) || entity.hasPotionEffect(PotionEffectType.WITHER)) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                entity.removePotionEffect(PotionEffectType.BAD_OMEN);
                                entity.removePotionEffect(PotionEffectType.BLINDNESS);
                                entity.removePotionEffect(PotionEffectType.CONFUSION);
                                entity.removePotionEffect(PotionEffectType.HUNGER);
                                entity.removePotionEffect(PotionEffectType.POISON);
                                entity.removePotionEffect(PotionEffectType.SLOW);
                                entity.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                                entity.removePotionEffect(PotionEffectType.SLOW_FALLING);
                                entity.removePotionEffect(PotionEffectType.UNLUCK);
                                entity.removePotionEffect(PotionEffectType.WEAKNESS);
                                entity.removePotionEffect(PotionEffectType.WITHER);
                            }, 1);
                            entity.sendMessage(COLORED_TARGET_REMOVED_BAD_EFFECTS);
                        }
                        entity.sendMessage(COLORED_TIMER_BAD_MESSAGE.replace("%time%", TIMER.toString()));
                    } else {
                        for (PotionEffect effect : entity.getActivePotionEffects()) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> entity.removePotionEffect(effect.getType()), 1);
                        }
                        entity.sendMessage(COLORED_TARGET_REMOVED_EFFECTS);
                        entity.sendMessage(COLORED_TIMER_MESSAGE.replace("%time%", TIMER.toString()));
                    }
                }
                protectedPlayers.add(entity.getName().toLowerCase());
                Bukkit.getScheduler().runTaskLater(plugin, () -> protectedPlayers.remove(entity.getName().toLowerCase()), TIMER * 20);
            }
        } else {
            for (LivingEntity entity : e.getAffectedEntities()) {
                if (protectedPlayers.contains(entity.getName().toLowerCase())) {
                    if (MilkPotion.get().getConfig().getBoolean("options.REMOVE_ONLY_DEBUFF")) {
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            entity.removePotionEffect(PotionEffectType.BAD_OMEN);
                            entity.removePotionEffect(PotionEffectType.BLINDNESS);
                            entity.removePotionEffect(PotionEffectType.CONFUSION);
                            entity.removePotionEffect(PotionEffectType.HUNGER);
                            entity.removePotionEffect(PotionEffectType.POISON);
                            entity.removePotionEffect(PotionEffectType.SLOW);
                            entity.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            entity.removePotionEffect(PotionEffectType.SLOW_FALLING);
                            entity.removePotionEffect(PotionEffectType.UNLUCK);
                            entity.removePotionEffect(PotionEffectType.WEAKNESS);
                            entity.removePotionEffect(PotionEffectType.WITHER);
                        }, 1);
                        entity.sendMessage(COLORED_TARGET_PREVENT_BAD_EFFECT);
                    } else {
                        for (PotionEffect effect : entity.getActivePotionEffects()) {
                            Bukkit.getScheduler().runTaskLater(plugin, () -> entity.removePotionEffect(effect.getType()), 1);
                        }
                        entity.sendMessage(COLORED_TARGET_PREVENT_EFFECT);
                    }
                }
            }
        }
    }
}