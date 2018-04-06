package io.github.robertograham.fortniteapirestclient.util;

import io.github.robertograham.fortniteapirestclient.domain.BattleRoyaleStats;
import io.github.robertograham.fortniteapirestclient.domain.Player;
import io.github.robertograham.fortniteapirestclient.util.mapper.impl.StatsJsonStatsGroupMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.stream.StreamSupport;

public class StatsHelper {

    public static boolean statsExistForPlatform(String statsJson, String platformCode) {
        JSONArray statsJsonArray = new JSONArray(statsJson);

        final String platformSearchString = String.format("_%s_", platformCode);

        return StreamSupport.stream(statsJsonArray.spliterator(), false)
                .map(JSONObject.class::cast)
                .map(statsObject -> statsObject.getString("name"))
                .anyMatch(name -> name.contains(platformSearchString));
    }

    public static BattleRoyaleStats buildBattleRoyaleStats(String statsJson, Player player, String platformCode) {
        BattleRoyaleStats battleRoyaleStats = new BattleRoyaleStats();

        battleRoyaleStats.setStats(new StatsJsonStatsGroupMapper(platformCode).map(statsJson));
        battleRoyaleStats.setPlayer(player);
        battleRoyaleStats.setPlatform(platformCode);

        return battleRoyaleStats;
    }
}
