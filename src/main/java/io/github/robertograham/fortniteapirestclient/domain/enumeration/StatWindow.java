package io.github.robertograham.fortniteapirestclient.domain.enumeration;

import java.util.EnumSet;

public enum StatWindow {

    WEEKLY("weekly"),
    MONTHLY("monthly"),
    ALL_TIME("alltime");

    private final String code;

    StatWindow(String code) {
        this.code = code;
    }

    public static StatWindow fromCode(String code) {
        return EnumSet.allOf(StatWindow.class).stream()
                .filter(statWindow -> statWindow.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
