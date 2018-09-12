package io.github.robertograham.fortniteapirestclient.domain.enumeration;

import java.util.EnumSet;

public enum PartyType {

    SOLO("p2"),
    DUO("p10"),
    SQUAD("p9");

    private final String code;

    PartyType(String code) {
        this.code = code;
    }

    public static PartyType fromCode(String code) {
        return EnumSet.allOf(PartyType.class).stream()
                .filter(partyType -> partyType.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public String getCode() {
        return code;
    }
}
