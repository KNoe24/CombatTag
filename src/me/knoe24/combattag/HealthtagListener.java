package me.knoe24.combattag;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HealthtagListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Player player = event.getPlayer();
        Scoreboard board1 = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = board.registerNewObjective(main.getInstance().getName() + "_health", Criteria.HEALTH, ChatColor.RED + "❤");
        Objective objective1 = board1.registerNewObjective(main.getInstance().getName() + "__health", Criteria.HEALTH, ChatColor.RED + "❤");

        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective1.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        player.setScoreboard(board);


    }


}
