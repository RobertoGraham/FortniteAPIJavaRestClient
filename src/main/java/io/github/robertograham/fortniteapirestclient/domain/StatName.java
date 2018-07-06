package io.github.robertograham.fortniteapirestclient.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof StatName))
            return false;

        StatName statName = (StatName) object;

        return Objects.equals(gameMode, statName.gameMode) &&
                Objects.equals(partyType, statName.partyType) &&
                Objects.equals(statType, statName.statType) &&
                Objects.equals(unknownProperty, statName.unknownProperty) &&
                Objects.equals(platform, statName.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameMode, partyType, statType, unknownProperty, platform);
    }
}
