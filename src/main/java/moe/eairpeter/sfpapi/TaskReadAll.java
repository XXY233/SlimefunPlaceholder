package moe.eairpeter.sfpapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.SlimefunStartup;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class TaskReadAll
        extends BukkitRunnable {
    private boolean processing = false;
    private List<Player> players = null;
    private List<String> ranks = null;
    private List<ResIdLv> researches = null;
    private final Plugin plugin;

    TaskReadAll(Plugin plugin_) {
        this.plugin = plugin_;
    }


    public void run() {
        if (this.processing)
            return;
        this.players = new LinkedList<>(Bukkit.getOnlinePlayers());
        this.ranks = new ArrayList<>(SlimefunStartup.getCfg().getStringList("research-ranks"));

        this.researches = new ArrayList<>();
        for (Research r : Research.list())
            this.researches.add(new ResIdLv(r.getID(), r.getLevel()));
        this.processing = true;
        (new BukkitRunnable() {
            public void run() {
                Map<UUID, PlayerInfo> map = new HashMap<>();
                for (Player p : TaskReadAll.this.players) {

                    ConfigurationSection cfgsec = YamlConfiguration.loadConfiguration(new File("data-storage/Slimefun/Players/" + p.getUniqueId() + ".yml")).getConfigurationSection("researches");
                    int count = 0;
                    int levels = 0;
                    if (cfgsec != null) {
                        Set<String> researched = cfgsec.getKeys(false);
                        for (TaskReadAll.ResIdLv ril : TaskReadAll.this.researches) {
                            if (researched.contains(String.valueOf(ril.id))) {
                                count++;
                                levels += ril.lv;
                            }
                        }
                    }
                    float progress = Math.round(count * 10000.0F / TaskReadAll.this
                            .researches.size()) / 100.0F;

                    int index = Math.round(progress * TaskReadAll.this.ranks.size() / 100.0F);
                    String rank = TaskReadAll.this.ranks.get((index > 0) ? (index - 1) : index);
                    map.put(p
                            .getUniqueId(), new PlayerInfo(levels, rank, progress));
                }


                PlaceholderHook.refMap.getAndSet(map);
                TaskReadAll.this.processing = false;
            }
        }).runTaskAsynchronously(this.plugin);
    }

    private static class ResIdLv {
        public final int id;
        public final int lv;

        public ResIdLv(int id_, int lv_) {
            this.id = id_;
            this.lv = lv_;
        }
    }
}