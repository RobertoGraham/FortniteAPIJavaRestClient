package io.github.robertograham.fortniteapirestclient.service.authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class OAuthToken {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_expires")
    private Long refreshExpires;
    @JsonProperty("refresh_expires_at")
    private LocalDateTime refreshExpiresAt;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("internal_client")
    private Boolean internalClient;
    @JsonProperty("client_service")
    private String clientService;
    private LocalDateTime lastPasswordValidation;
    @JsonProperty("perms")
    private List<Permission> perms;
    private String app;
    @JsonProperty("in_app_id")
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

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
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

    public LocalDateTime getRefreshExpiresAt() {
        return refreshExpiresAt;
    }

    public void setRefreshExpiresAt(LocalDateTime refreshExpiresAt) {
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

    public LocalDateTime getLastPasswordValidation() {
        return lastPasswordValidation;
    }

    public void setLastPasswordValidation(LocalDateTime lastPasswordValidation) {
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
