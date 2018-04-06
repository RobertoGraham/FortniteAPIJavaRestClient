package io.github.robertograham.fortniteapirestclient.domain;

public class BattleRoyaleStats {

    private Player player;
    private StatsGroup stats;
    private String platform;

    public StatsGroup getStats() {
        return stats;
    }

    public void setStats(StatsGroup stats) {
        this.stats = stats;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "BattleRoyaleStats{" +
                "player=" + player +
                ", stats=" + stats +
                ", platform='" + platform + '\'' +
                '}';
    }
}
