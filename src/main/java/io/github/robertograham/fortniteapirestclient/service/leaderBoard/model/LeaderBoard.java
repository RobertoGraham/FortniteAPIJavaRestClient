package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

import java.util.List;

public class LeaderBoard {

    private String statName;
    private String statWindow;
    private List<LeaderBoardEntry> entries;

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getStatWindow() {
        return statWindow;
    }

    public void setStatWindow(String statWindow) {
        this.statWindow = statWindow;
    }

    public List<LeaderBoardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LeaderBoardEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "LeaderBoard{" +
                "statName='" + statName + '\'' +
                ", statWindow='" + statWindow + '\'' +
                ", entries=" + entries +
                '}';
    }
}
