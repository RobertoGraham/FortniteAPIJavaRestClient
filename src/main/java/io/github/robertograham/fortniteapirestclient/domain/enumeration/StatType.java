package io.github.robertograham.fortniteapirestclient.domain.enumeration;

import java.util.EnumSet;

public enum StatType {

    PLACE_TOP_1("placetop1"),
    PLACE_TOP_3("placetop3"),
    PLACE_TOP_5("placetop5"),
    PLACE_TOP_6("placetop6"),
    PLACE_TOP_10("placetop10"),
    PLACE_TOP_12("placetop12"),
    PLACE_TOP_25("placetop25"),
    MATCHES_PLAYED("matchesplayed"),
    KILLS("kills"),
    MINUTES_PLAYED("minutesplayed"),
    SCORE("score");

    private final String code;

    StatType(String code) {
        this.code = code;
    }

    public static StatType fromCode(String code) {
        return EnumSet.allOf(StatType.class).stream()
                .filter(statType -> statType.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
