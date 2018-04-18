package io.github.robertograham.fortniteapirestclient.domain;

import java.util.List;

public class EnhancedLeaderBoard {

    private List<EnhancedLeaderBoardEntry> entries;

    public List<EnhancedLeaderBoardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<EnhancedLeaderBoardEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "EnhancedLeaderBoard{" +
                "entries=" + entries +
                '}';
    }
}
