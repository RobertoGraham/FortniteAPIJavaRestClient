package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

import com.google.api.client.util.Key;

import java.util.List;

public class Cohort {

    @Key
    private String accountId;
    @Key
    private String playlist;
    @Key
    private List<String> cohortAccounts;
    @Key
    private String expiresAt;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public List<String> getCohortAccounts() {
        return cohortAccounts;
    }

    public void setCohortAccounts(List<String> cohortAccounts) {
        this.cohortAccounts = cohortAccounts;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Cohort{" +
                "accountId='" + accountId + '\'' +
                ", playlist='" + playlist + '\'' +
                ", cohortAccounts=" + cohortAccounts +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
