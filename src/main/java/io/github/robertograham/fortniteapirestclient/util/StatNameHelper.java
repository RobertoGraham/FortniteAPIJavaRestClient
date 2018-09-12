package io.github.robertograham.fortniteapirestclient.util;

import io.github.robertograham.fortniteapirestclient.domain.StatName;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.GameMode;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatType;

public class StatNameHelper {

    public static StatName parse(String statName) {
        String[] components = statName.split("_");

        if (components.length == 5) {
            StatName product = new StatName();

            product.setGameMode(GameMode.fromCode(components[0]));
            product.setStatType(StatType.fromCode(components[1]));
            product.setPlatform(Platform.fromCode(components[2]));
            product.setUnknownProperty(components[3]);
            product.setPartyType(PartyType.fromCode(components[4]));

            return product;
        } else
            throw new IllegalArgumentException("Stat name did not have 5 components only");
    }
}
