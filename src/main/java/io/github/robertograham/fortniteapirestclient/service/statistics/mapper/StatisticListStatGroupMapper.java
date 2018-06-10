package io.github.robertograham.fortniteapirestclient.service.statistics.mapper;

import io.github.robertograham.fortniteapirestclient.domain.StatName;
import io.github.robertograham.fortniteapirestclient.domain.Stats;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatType;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.util.StatNameHelper;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

public class StatisticListStatGroupMapper {

    public static StatsGroup mapFrom(List<Statistic> statistics, String platform) {
        Stats solo = new Stats();
        Stats duo = new Stats();
        Stats squad = new Stats();

        statistics.forEach(statistic -> {
            StatName statName = StatNameHelper.parse(statistic.getName());
            IntSupplier valueFunction = statistic::getValue;
            Function<StatName, Stats> keyToStatsFunction = suppliedStatName -> PartyType.SOLO.equals(suppliedStatName.getPartyType()) ? solo : PartyType.DUO.equals(suppliedStatName.getPartyType()) ? duo : squad;

            if (attemptConsumeValue(statName, StatType.PLACE_TOP_1, Stats::setWins, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_3, Stats::setTop3, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_5, Stats::setTop5, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_6, Stats::setTop6, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_10, Stats::setTop10, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_12, Stats::setTop12, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_25, Stats::setTop25, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.MATCHES_PLAYED, Stats::setMatches, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.KILLS, Stats::setKills, keyToStatsFunction, valueFunction, platform))
                return;
            if (attemptConsumeValue(statName, StatType.MINUTES_PLAYED, Stats::setTimePlayed, keyToStatsFunction, valueFunction, platform))
                return;
            attemptConsumeValue(statName, StatType.SCORE, Stats::setScore, keyToStatsFunction, valueFunction, platform);
        });

        Stream.of(solo, duo, squad).forEach(stats -> {
            stats.setKillToDeathRatio(divide(stats.getKills(), stats.getMatches() - stats.getWins()));
            stats.setWinPercentage(percent(stats.getWins(), stats.getMatches()));
            stats.setKillsPerMin(divide(stats.getKills(), stats.getTimePlayed()));
            stats.setKillsPerMatch(divide(stats.getKills(), stats.getMatches()));
        });

        StatsGroup statsGroup = new StatsGroup();

        statsGroup.setSolo(solo);
        statsGroup.setDuo(duo);
        statsGroup.setSquad(squad);

        return statsGroup;
    }

    private static double divide(double a, double b) {
        return b == 0 ? 0 : a / b;
    }

    private static double percent(double a, double b) {
        return divide(a, b) * 100;
    }

    private static boolean attemptConsumeValue(StatName statName, String desiredStatType, BiConsumer<Stats, Integer> statsAndValueBiConsumer, Function<StatName, Stats> statNameToStatsFunction, IntSupplier valueSupplier, String platform) {
        if (statName.getStatType().equals(desiredStatType) && statName.getPlatform().equals(platform)) {
            statsAndValueBiConsumer.accept(statNameToStatsFunction.apply(statName), valueSupplier.getAsInt());

            return true;
        }

        return false;
    }
}
