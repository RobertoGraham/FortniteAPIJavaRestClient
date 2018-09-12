package io.github.robertograham.fortniteapirestclient.service.account.model;

import com.google.api.client.util.Key;

import java.util.List;

public class ExternalAuth {

    @Key
    private String type;
    @Key
    private String externalAuthId;
    @Key
    private String externalAuthIdType;
    @Key
    private String accountId;
    @Key
    private String externalDisplayName;
    @Key
    private List<AuthId> authIds;

    public String getExternalAuthIdType() {
        return externalAuthIdType;
    }

    public void setExternalAuthIdType(String externalAuthIdType) {
        this.externalAuthIdType = externalAuthIdType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExternalAuthId() {
        return externalAuthId;
    }

    public void setExternalAuthId(String externalAuthId) {
        this.externalAuthId = externalAuthId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getExternalDisplayName() {
        return externalDisplayName;
    }

    public void setExternalDisplayName(String externalDisplayName) {
        this.externalDisplayName = externalDisplayName;
    }

    public List<AuthId> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<AuthId> authIds) {
        this.authIds = authIds;
    }

    @Override
    public String toString() {
        return "ExternalAuth{" +
                "type='" + type + '\'' +
                ", externalAuthId='" + externalAuthId + '\'' +
                ", externalAuthIdType='" + externalAuthIdType + '\'' +
                ", accountId='" + accountId + '\'' +
                ", externalDisplayName='" + externalDisplayName + '\'' +
                ", authIds=" + authIds +
                '}';
    }
}
