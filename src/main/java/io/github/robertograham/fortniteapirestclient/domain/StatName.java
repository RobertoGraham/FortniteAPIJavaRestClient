package io.github.robertograham.fortniteapirestclient.domain;

public class StatName {

    private String gameMode;
    private String partyType;
    private String statType;
    private String unknownProperty;
    private String platform;

    public StatName() {
        unknownProperty = "m0";
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public String getUnknownProperty() {
        return unknownProperty;
    }

    public void setUnknownProperty(String unknownProperty) {
        this.unknownProperty = unknownProperty;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String formatted() {
        return String.format("%s_%s_%s_%s_%s", gameMode, statType, platform, unknownProperty, partyType);
    }

    @Override
    public String toString() {
        return "StatName{" +
                "gameMode='" + gameMode + '\'' +
                ", partyType='" + partyType + '\'' +
                ", statType='" + statType + '\'' +
                ", unknownProperty='" + unknownProperty + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
