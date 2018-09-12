package io.github.robertograham.fortniteapirestclient.domain.enumeration;

import java.util.EnumSet;

public enum GameMode {

    BATTLE_ROYALE("br"),
    PLAYER_VERSUS_ENVIRONMENT("pve");

    private final String code;

    GameMode(String code) {
        this.code = code;
    }

    public static GameMode fromCode(String code) {
        return EnumSet.allOf(GameMode.class).stream()
                .filter(gameMode -> gameMode.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
