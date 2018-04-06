package io.github.robertograham.fortniteapirestclient.domain;

public class Stats {

    private int wins;
    private int top3;
    private int top5;
    private int top6;
    private int top10;
    private int top12;
    private int top25;
    private double killToDeathRatio;
    private double winPercentage;
    private int matches;
    private int kills;
    private double killsPerMin;
    private double killsPerMatch;
    private int timePlayed;
    private int score;

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getTop3() {
        return top3;
    }

    public void setTop3(int top3) {
        this.top3 = top3;
    }

    public int getTop5() {
        return top5;
    }

    public void setTop5(int top5) {
        this.top5 = top5;
    }

    public int getTop6() {
        return top6;
    }

    public void setTop6(int top6) {
        this.top6 = top6;
    }

    public int getTop10() {
        return top10;
    }

    public void setTop10(int top10) {
        this.top10 = top10;
    }

    public int getTop12() {
        return top12;
    }

    public void setTop12(int top12) {
        this.top12 = top12;
    }

    public int getTop25() {
        return top25;
    }

    public void setTop25(int top25) {
        this.top25 = top25;
    }

    public double getKillToDeathRatio() {
        return killToDeathRatio;
    }

    public void setKillToDeathRatio(double killToDeathRatio) {
        this.killToDeathRatio = killToDeathRatio;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(double winPercentage) {
        this.winPercentage = winPercentage;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public double getKillsPerMin() {
        return killsPerMin;
    }

    public void setKillsPerMin(double killsPerMin) {
        this.killsPerMin = killsPerMin;
    }

    public double getKillsPerMatch() {
        return killsPerMatch;
    }

    public void setKillsPerMatch(double killsPerMatch) {
        this.killsPerMatch = killsPerMatch;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "wins=" + wins +
                ", top3=" + top3 +
                ", top5=" + top5 +
                ", top6=" + top6 +
                ", top10=" + top10 +
                ", top12=" + top12 +
                ", top25=" + top25 +
                ", killToDeathRatio=" + killToDeathRatio +
                ", winPercentage=" + winPercentage +
                ", matches=" + matches +
                ", kills=" + kills +
                ", killsPerMin=" + killsPerMin +
                ", killsPerMatch=" + killsPerMatch +
                ", timePlayed=" + timePlayed +
                ", score=" + score +
                '}';
    }
}
