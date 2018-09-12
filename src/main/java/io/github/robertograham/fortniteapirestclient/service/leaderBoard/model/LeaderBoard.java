package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

import com.google.api.client.util.Key;

import java.util.List;

public class LeaderBoard {

    @Key
    private String statName;
    @Key
    private String statWindow;
    @Key
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
