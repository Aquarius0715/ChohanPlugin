package aquarius0715.aquachohanplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class AquaChohanPlugin extends JavaPlugin {

    Map<String, Integer> c_result = new HashMap<String, Integer>();
    Map<String, Integer> h_result = new HashMap<String, Integer>();
    int rand = new Random().nextInt(1);
    int Playercount = 0;
    boolean gamestats = false;
    int time = 30;

    private AquaChohanPlugin plugin;

    public void newGame(AquaChohanPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ac")) {
            if (!(sender instanceof Player)) {
                Player player = (Player) sender;
                player.sendMessage("You cannot this!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage("==========ChohanPlugin==========");
                sender.sendMessage("/ac create : 新しい丁半のゲームを始めます。");
                sender.sendMessage("/ac c : 始まったゲームに「丁」として参加します。");
                sender.sendMessage("/ac h : 始まったゲームに「半」として参加します。");
                sender.sendMessage("==========ChohanPlugin==========");
            }


            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("create")) {
                    Player player = (Player) sender;

                    Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getDisplayName() + "さんが新しく丁半を始めました");

                    TimeCount();
                    gamestats = true;
                    return true;
                }

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("c")) {
                        Player c_player = (Player) sender;
                        if (gamestats == false) {
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "丁半は始まっていません");
                            return false;
                        }
                        for (String key : c_result.keySet()) {
                            for (String key1 : c_result.keySet()) {
                                if (key == c_player.getDisplayName() || key1 == c_player.getDisplayName()) {
                                    c_player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "貴方はすでに参加しています");
                                    return false;
                                }
                            }
                        }

                        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + c_player.getDisplayName() + "さんが丁に参加しました");

                        c_result.put(c_player.getDisplayName(), 0);

                        Playercount++;
                    }
                    if (args[0].equalsIgnoreCase("h")) {
                        Player h_player = (Player) sender;
                        if (gamestats == false) {
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "丁半は始まっていません");
                            return false;
                        }
                        for (String key : c_result.keySet()) {
                            for (String key1 : c_result.keySet()) {
                                if (key == h_player.getDisplayName() || key1 == h_player.getDisplayName()) {
                                    h_player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "貴方はすでに参加しています");
                                    return false;
                                }
                            }
                        }

                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + h_player.getDisplayName() + "さんが半に参加しました");

                        h_result.put(h_player.getDisplayName(), 1);

                        Playercount++;
                    }
                }
            }
        }
        return false;
    }


    public void TimeCount() {

        new BukkitRunnable() {
            @Override
            public void run() {
                if (gamestats) {
                    if (plugin.time >= -2) {
                        if (plugin.time % 10 == 0 || plugin.time <= 5) {
                            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "終了まであと" + plugin.time + "秒です");
                        }
                    }

                    if (plugin.time == 0) {
                        if (Playercount < 2) {
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "人数が足りないためゲームを停止します");
                            GameEnd();
                            return;
                        } else {
                            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "サイコロを引いています");
                            if (plugin.time == -3) {
                                Game();
                                cancel();
                                return;
                            }
                        }
                    }
                } else {
                    cancel();
                    return;
                }
                plugin.time--;
            }
        }.runTaskTimer(plugin, 0, 20); // ここでエラー発生
    }

    public void GameEnd() {

        Playercount = 0;

        gamestats = false;

        c_result.clear();

        h_result.clear();

        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "丁半が終了しました");
    }

    public void Game() {

        if (rand == 0) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "丁の勝利！");
            for (String key : c_result.keySet()) {
                int value = c_result.get(key);
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + key + "の勝利です！");
            }
            GameEnd();
        }
        if (rand == 1) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "半の勝利！");
            for (String key : h_result.keySet()) {
                int value = h_result.get(key);
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + key + "の勝利です！");
            }
            GameEnd();
        }
    }
}
