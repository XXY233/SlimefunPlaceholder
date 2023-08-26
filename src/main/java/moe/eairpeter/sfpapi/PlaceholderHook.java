package moe.eairpeter.sfpapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public class PlaceholderHook extends PlaceholderExpansion {
    static AtomicReference<Map<UUID, PlayerInfo>> refMap = new AtomicReference<>(new HashMap<>());

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String id) {
        if (player == null)
            return "";
        PlayerInfo pi = (PlayerInfo) ((Map<?, ?>) refMap.get()).get(player.getUniqueId());
        if (pi == null)
            return "";
        switch (id) {
            case "level":
                return pi.levels;
            case "rank":
                return pi.rank;
            case "progress":
                return pi.progress;
        }
        return "";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "slimefun";
    }

    @Override
    public @NotNull String getAuthor() {
        return "EAirPeter, TheLittle_Yang";
    }

    @Override
    public @NotNull String getVersion() {
        return "3.0.0";
    }
}
