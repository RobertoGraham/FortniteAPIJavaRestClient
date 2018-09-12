package io.github.robertograham.fortniteapirestclient.domain.enumeration;

import java.util.EnumSet;

public enum Platform {

    PLAYSTATION_4("ps4"),
    XBOX_ONE("xb1"),
    PC("pc"),
    IOS("ios"),
    NINTENDO_SWITCH("switch");

    private final String code;

    Platform(String code) {
        this.code = code;
    }

    public static Platform fromCode(String code) {
        return EnumSet.allOf(Platform.class).stream()
                .filter(platform -> platform.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
