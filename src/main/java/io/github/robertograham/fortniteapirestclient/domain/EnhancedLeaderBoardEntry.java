package io.github.robertograham.fortniteapirestclient.domain;

import io.github.robertograham.fortniteapirestclient.service.account.model.Account;

public class EnhancedLeaderBoardEntry {

    private Account account;
    private int rank;
    private int wins;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
                "account=" + account +
                ", rank=" + rank +
                ", wins=" + wins +
                '}';
    }
}
