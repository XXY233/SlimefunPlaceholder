package moe.eairpeter.sfpapi;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class SlimefunPlaceholder extends JavaPlugin {
    private final BukkitRunnable task = new TaskReadAll(this);
    private final PlaceholderHook hook = new PlaceholderHook();


    public void onEnable() {
        PluginManager pm = Bukkit.getPluginManager();
        Validate.isTrue(pm.isPluginEnabled("Slimefun"), "The plugin Slimefun is required.");

        Validate.isTrue(pm.isPluginEnabled("PlaceholderAPI"), "The plugin PlaceholderAPI is required.");

        this.task.runTaskTimer(this, 0L, 20L);
        Validate.isTrue(this.hook.register(), "Failed to hook into PlaceholderAPI");
    }


    public void onDisable() {
        this.task.cancel();
    }
}
