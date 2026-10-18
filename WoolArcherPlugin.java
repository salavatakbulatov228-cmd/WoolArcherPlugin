package ru.dipsik.woolars;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WoolArcherPlugin extends JavaPlugin implements Listener {

    private static final List<String> PARTICLE_NAMES = Arrays.asList(
            "BLOCK", "DUST", "FLAME", "HEART", "CRIT", "SMOKE",
            "EXPLOSION", "FIREWORK", "SOUL_FIRE_FLAME", "END_ROD",
            "TOTEM_OF_UNDYING", "ENCHANT", "PORTAL", "LAVA"
    );

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("WoolArcherPlugin включен! Используйте /woolarcher для настройки.");
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;

        Block hitBlock = event.getHitBlock();
        if (hitBlock == null || !hitBlock.getType().name().endsWith("_WOOL")) return;

        FileConfiguration config = getConfig();
        int count = config.getInt("particle-count", 20);
        double spread = config.getDouble("particle-spread", 0.3);
        String soundName = config.getString("sound", "BLOCK_WOOL_BREAK");
        int delay = config.getInt("arrow-remove-delay", 1);
        String particleName = config.getString("particle", "BLOCK");

        // Звук
        try {
            Sound sound = Registry.SOUNDS.get(org.bukkit.NamespacedKey.minecraft(soundName.toLowerCase()));
            if (sound != null) {
                hitBlock.getWorld().playSound(hitBlock.getLocation(), sound, 1.0f, 1.0f);
            }
        } catch (Exception ignored) {}

        // Частицы
        Particle particle;
        try {
            particle = Particle.valueOf(particleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            particle = Particle.BLOCK;
        }

        if (particle == Particle.BLOCK) {
            hitBlock.getWorld().spawnParticle(particle, hitBlock.getLocation().add(0.5, 0.5, 0.5), count, spread, spread, spread, 0.1, hitBlock.getBlockData());
        } else {
            hitBlock.getWorld().spawnParticle(particle, hitBlock.getLocation().add(0.5, 0.5, 0.5), count, spread, spread, spread, 0.1);
        }

        // Разрушение
        hitBlock.setType(Material.AIR);

        // Удаление стрелы с задержкой
        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isValid()) arrow.remove();
            }
        }.runTaskLater(this, delay);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("woolarcher")) return false;

        if (args.length == 0) {
            sender.sendMessage("§e/woolarcher <delay|count|spread|sound|particle> <значение>");
            sender.sendMessage("§e/woolarcher reload");
            return true;
        }

        // Обработка reload
        if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage("§aКонфигурация перезагружена!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cУкажите значение!");
            return true;
        }

        String key = args[0].toLowerCase();
        String value = args[1];
        FileConfiguration config = getConfig();

        try {
            switch (key) {
                case "delay" -> config.set("arrow-remove-delay", Integer.parseInt(value));
                case "count" -> config.set("particle-count", Integer.parseInt(value));
                case "spread" -> config.set("particle-spread", Double.parseDouble(value));
                case "sound" -> config.set("sound", value.toUpperCase());
                case "particle" -> config.set("particle", value.toUpperCase());
                default -> {
                    sender.sendMessage("§cНеизвестный параметр!");
                    return true;
                }
            }
            saveConfig(); // Сохраняем сразу после изменения!
            sender.sendMessage("§a" + key + " установлено на §e" + value);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cОшибка: значение должно быть числом!");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("delay", "count", "spread", "sound", "particle", "reload");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("sound")) return SoundHelper.getAllSoundNames();
            if (args[0].equalsIgnoreCase("particle")) return PARTICLE_NAMES;
        }
        return new ArrayList<>();
    }
}
