package io.github.robertograham.fortniteapirestclient.domain;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.GameMode;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatType;

import java.util.Objects;

public class StatName {

    private GameMode gameMode;
    private PartyType partyType;
    private StatType statType;
    private String unknownProperty;
    private Platform platform;

    public StatName() {
        unknownProperty = "m0";
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public PartyType getPartyType() {
        return partyType;
    }

    public void setPartyType(PartyType partyType) {
        this.partyType = partyType;
    }

    public StatType getStatType() {
        return statType;
    }

    public void setStatType(StatType statType) {
        this.statType = statType;
    }

    public String getUnknownProperty() {
        return unknownProperty;
    }

    public void setUnknownProperty(String unknownProperty) {
        this.unknownProperty = unknownProperty;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String formatted() {
        return String.format("%s_%s_%s_%s_%s", gameMode.getCode(), statType.getCode(), platform.getCode(), unknownProperty, partyType.getCode());
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
