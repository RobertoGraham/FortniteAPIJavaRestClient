package io.github.robertograham.fortniteapirestclient.util;

import io.github.robertograham.fortniteapirestclient.domain.StatName;

public class StatNameHelper {

    public static StatName parse(String statName) {
        String[] components = statName.split("_");

        if (components.length == 5) {
            StatName product = new StatName();

            product.setGameMode(components[0]);
            product.setStatType(components[1]);
            product.setPlatform(components[2]);
            product.setUnknownProperty(components[3]);
            product.setPartyType(components[4]);

            return product;
        } else
            throw new IllegalArgumentException("Stat name did not have 5 components only");
    }
}
