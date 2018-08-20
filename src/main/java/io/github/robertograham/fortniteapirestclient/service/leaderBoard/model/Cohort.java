package io.github.robertograham.fortniteapirestclient.service.leaderBoard.model;

import java.time.LocalDateTime;
import java.util.List;

public class Cohort {

    private String accountId;
    private String playlist;
    private List<String> cohortAccounts;
    private LocalDateTime expiresAt;

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

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
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
