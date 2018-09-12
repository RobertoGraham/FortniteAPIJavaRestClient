package io.github.robertograham.fortniteapirestclient.service.authentication.model;

import com.google.api.client.util.Key;

import java.util.List;

public class OAuthToken {

    @Key("access_token")
    private String accessToken;
    @Key("expires_in")
    private Long expiresIn;
    @Key("expires_at")
    private String expiresAt;
    @Key("token_type")
    private String tokenType;
    @Key("refresh_token")
    private String refreshToken;
    @Key("refresh_expires")
    private Long refreshExpires;
    @Key("refresh_expires_at")
    private String refreshExpiresAt;
    @Key("account_id")
    private String accountId;
    @Key("client_id")
    private String clientId;
    @Key("internal_client")
    private Boolean internalClient;
    @Key("client_service")
    private String clientService;
    @Key
    private String lastPasswordValidation;
    @Key("perms")
    private List<Permission> perms;
    @Key
    private String app;
    @Key("in_app_id")
    private String inAppId;

    public List<Permission> getPerms() {
        return perms;
    }

    public void setPerms(List<Permission> perms) {
        this.perms = perms;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshExpires() {
        return refreshExpires;
    }

    public void setRefreshExpires(Long refreshExpires) {
        this.refreshExpires = refreshExpires;
    }

    public String getRefreshExpiresAt() {
        return refreshExpiresAt;
    }

    public void setRefreshExpiresAt(String refreshExpiresAt) {
        this.refreshExpiresAt = refreshExpiresAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Boolean getInternalClient() {
        return internalClient;
    }

    public void setInternalClient(Boolean internalClient) {
        this.internalClient = internalClient;
    }

    public String getClientService() {
        return clientService;
    }

    public void setClientService(String clientService) {
        this.clientService = clientService;
    }

    public String getLastPasswordValidation() {
        return lastPasswordValidation;
    }

    public void setLastPasswordValidation(String lastPasswordValidation) {
        this.lastPasswordValidation = lastPasswordValidation;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getInAppId() {
        return inAppId;
    }

    public void setInAppId(String inAppId) {
        this.inAppId = inAppId;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", expiresAt=" + expiresAt +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshExpires=" + refreshExpires +
                ", refreshExpiresAt=" + refreshExpiresAt +
                ", accountId='" + accountId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", internalClient=" + internalClient +
                ", clientService='" + clientService + '\'' +
                ", lastPasswordValidation=" + lastPasswordValidation +
                ", perms=" + perms +
                ", app='" + app + '\'' +
                ", inAppId='" + inAppId + '\'' +
                '}';
    }

}
