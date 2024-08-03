package org.padrewin.spidersense;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.padrewin.spidersense.Commands.Commands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderSense extends JavaPlugin implements Listener {

    private String prefix;
    //private String warningMessage;
    private String removedMessage;
    private String reloadMessage;
    private String noPermissionMessage;
    private String shutdownMessage;
    private int cobwebTimer;
    private final Set<Block> cobwebs = new HashSet<>();
    private final Set<Player> notifiedPlayers = new HashSet<>(); // Set pentru a urmări jucătorii notificați
    private final HashMap<Player, BukkitTask> removalTasks = new HashMap<>(); // Map pentru urmărirea task-urilor de eliminare
    private BukkitTask warningTask;
    private BukkitTask removalTask;

    @Override
    public void onEnable() {
        String name = getDescription().getName();
        getLogger().info("");
        getLogger().info("  ____ ___  _     ____  ");
        getLogger().info(" / ___/ _ \\| |   |  _ \\ ");
        getLogger().info("| |  | | | | |   | | | |");
        getLogger().info("| |__| |_| | |___| |_| |");
        getLogger().info(" \\____\\___/|_____|____/");
        getLogger().info("    " + name + " v" + getDescription().getVersion());
        getLogger().info("    Author(s): " + String.join(", ", getDescription().getAuthors()));
        getLogger().info("    (c) Cold Development. All rights reserved.");
        getLogger().info("");

        // Înregistrăm evenimentele la pornirea pluginului
        Bukkit.getPluginManager().registerEvents(this, this);

        // Înregistrăm comanda
        Objects.requireNonNull(getCommand("spidersense")).setExecutor(new Commands(this));

        // Încărcăm configurația
        saveDefaultConfig();
        loadConfig();
    }

    @Override
    public void onDisable() {
        for (Block cobweb : cobwebs) {
            if (cobweb.getType() == Material.COBWEB) {
                cobweb.setType(Material.AIR);
            }
        }

        Bukkit.broadcastMessage(prefix + shutdownMessage);

        if (warningTask != null) {
            warningTask.cancel();
        }
        if (removalTask != null) {
            removalTask.cancel();
        }

        cobwebs.clear();
    }

    public void reloadConfigFiles() {
        reloadConfig();
        loadConfig();
    }

    private void loadConfig() {
        prefix = translateHexCodes(getConfig().getString("plugin-prefix"));
        //warningMessage = translateHexCodes(getConfig().getString("warning-message"));
        removedMessage = translateHexCodes(getConfig().getString("removed-message"));
        reloadMessage = translateHexCodes(getConfig().getString("reload-message"));
        noPermissionMessage = translateHexCodes(getConfig().getString("no-permission-message"));
        shutdownMessage = translateHexCodes(getConfig().getString("shutdown-message"));
        cobwebTimer = getConfig().getInt("cobweb-timer", 30); // Timpul în secunde
    }

    public String getPrefix() {
        return prefix;
    }

    public String getReloadMessage() {
        return reloadMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    private String translateHexCodes(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            StringBuilder replacement = new StringBuilder("§x");
            char[] hex = matcher.group(1).toCharArray();
            for (char c : hex) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.getBlockPlaced().getType() == Material.COBWEB) {
            Block cobweb = event.getBlockPlaced();
            cobwebs.add(cobweb);

            // Înlocuim blocul cu aer după un delay specificat în configurație
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (cobweb.getType() == Material.COBWEB) {
                        cobweb.setType(Material.AIR);

                        // Trimitem mesajul doar dacă jucătorul nu a fost notificat deja
                        if (!notifiedPlayers.contains(player)) {
                            player.sendMessage(prefix + removedMessage);
                            notifiedPlayers.add(player);

                            // Planificăm eliminarea jucătorului din set după 15 secunde, dacă nu există deja un task planificat
                            if (!removalTasks.containsKey(player)) {
                                BukkitTask removalTask = schedulePlayerRemoval(player);
                                removalTasks.put(player, removalTask);
                            }
                        }
                    }
                }
            }.runTaskLater(this, cobwebTimer * 20L); // Delay în secunde convertit în ticks
        }
    }

    private BukkitTask schedulePlayerRemoval(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                notifiedPlayers.remove(player);
                removalTasks.remove(player); // Eliminăm task-ul din map după ce a fost executat
            }
        }.runTaskLater(this, cobwebTimer * 2 * 20L); // 15 secunde convertite în ticks
    }
}
