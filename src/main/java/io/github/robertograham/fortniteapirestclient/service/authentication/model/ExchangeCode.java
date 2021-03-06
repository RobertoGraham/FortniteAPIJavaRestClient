package io.github.robertograham.fortniteapirestclient.service.authentication.model;

public class ExchangeCode {

    private Integer expiresInSeconds;
    private String code;
    private String creatingClientId;

    public Integer getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Integer expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatingClientId() {
        return creatingClientId;
    }

    public void setCreatingClientId(String creatingClientId) {
        this.creatingClientId = creatingClientId;
    }

    @Override
    public String toString() {
        return "ExchangeCode{" +
                "expiresInSeconds=" + expiresInSeconds +
                ", code='" + code + '\'' +
                ", creatingClientId='" + creatingClientId + '\'' +
                '}';
    }
}
