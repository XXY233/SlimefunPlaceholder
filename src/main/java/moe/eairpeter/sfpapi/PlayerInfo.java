package moe.eairpeter.sfpapi;


public class PlayerInfo {
    public final String levels;
    public final String rank;
    public final String progress;

    public PlayerInfo(int levels_, String rank_, float progress_) {
        this.levels = String.valueOf(levels_);
        this.rank = rank_;
        this.progress = String.valueOf(progress_);
    }
}