package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

import com.google.api.client.util.Key;

public class LeaderBoardEntry {

    @Key
    private String accountId;
    @Key
    private Integer value;
    @Key
    private Integer rank;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "LeaderBoardEntry{" +
                "accountId='" + accountId + '\'' +
                ", value=" + value +
                ", rank=" + rank +
                '}';
    }
}
