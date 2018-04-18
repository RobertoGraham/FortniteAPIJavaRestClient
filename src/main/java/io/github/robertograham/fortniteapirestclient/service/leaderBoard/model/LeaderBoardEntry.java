package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

public class LeaderBoardEntry {

    private String accountId;
    private Integer value;
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
