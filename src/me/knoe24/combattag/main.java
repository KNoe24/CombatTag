package me.knoe24.combattag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class main extends JavaPlugin implements Listener {

    private HashMap<UUID, Integer> combatlist;

    @Override
    public void onEnable() {
        this.combatlist = new HashMap<>();
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                onInterval();
            }
        }.runTaskTimer(this, 0, 20);
        Bukkit.getServer().getPluginManager().registerEvents(new HealthtagListener(), this);
        getLogger().info("The CombatLog 1.0.0 was enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("The CombatLog 1.0.0 was disabled!");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player target = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            if (!combatlist.containsKey(target.getUniqueId())) {

                target.sendMessage(ChatColor.RED + "You are now in combat");
                damager.sendMessage(ChatColor.RED + "You are now in combat");
            }

            combatlist.put(target.getUniqueId(), 10);
            combatlist.put(damager.getUniqueId(), 10);


        }
    }

    public void onInterval() {
        for (UUID id : combatlist.keySet()) {
            int timer = combatlist.get(id) - 1;
            if (timer > 0) {
                combatlist.put(id, timer);
                Player player = getServer().getPlayer(id);
                if (player != null) {
                    actionbar.sendActionBar(player, ChatColor.RED + "Combat Timer: " + timer);
                }
            } else {
                combatlist.remove(id);


            }
        }
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        if (combatlist.containsKey(event.getPlayer().getUniqueId())) {
            List<String> commands = Arrays.asList("d");

            String[] parts = event.getMessage().split(" ");

            String cmd = parts[0].toLowerCase();

            if (commands.contains(cmd)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "You are in combat, you cannot run this command!");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (combatlist.containsKey(player.getUniqueId())) {
            // Simulate death if the player is in combat on logout
            player.setHealth(0);
            combatlist.remove(player.getUniqueId());
        }
    }
    public static main getInstance() {
        return getPlugin(main.class);
    }
}
