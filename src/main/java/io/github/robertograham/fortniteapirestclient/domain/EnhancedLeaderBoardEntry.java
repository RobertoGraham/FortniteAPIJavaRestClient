package io.github.robertograham.fortniteapirestclient.domain;

public class EnhancedLeaderBoardEntry {

    private String displayName;
    private String accountId;
    private int rank;
    private int wins;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public String toString() {
        return "EnhancedLeaderBoardEntry{" +
                "displayName='" + displayName + '\'' +
                ", accountId='" + accountId + '\'' +
                ", rank=" + rank +
                ", wins=" + wins +
                '}';
    }
}
