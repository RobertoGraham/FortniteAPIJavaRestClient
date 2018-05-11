package io.github.robertograham.fortniteapirestclient.service.statistics.mapper;

import io.github.robertograham.fortniteapirestclient.domain.StatName;
import io.github.robertograham.fortniteapirestclient.domain.Stats;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatType;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.util.Mapper;
import io.github.robertograham.fortniteapirestclient.util.StatNameHelper;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class StatisticListStatGroupMapper implements Mapper<List<Statistic>, StatsGroup> {

    private final String platform;

    public StatisticListStatGroupMapper(String platform) {
        this.platform = platform;
    }

    @Override
    public StatsGroup mapFrom(List<Statistic> statistics) {
        Stats solo = new Stats();
        Stats duo = new Stats();
        Stats squad = new Stats();
        Stats lifetime = new Stats();

        statistics.forEach(statistic -> {
            StatName statName = StatNameHelper.parse(statistic.getName());
            IntSupplier valueFunction = statistic::getValue;
            Function<StatName, Stats> keyToStatsFunction = suppliedStatName -> PartyType.SOLO.equals(suppliedStatName.getPartyType()) ? solo : PartyType.DUO.equals(suppliedStatName.getPartyType()) ? duo : squad;

            if (attemptConsumeValue(statName, StatType.PLACE_TOP_1, Stats::setWins, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_3, Stats::setTop3, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_5, Stats::setTop5, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_6, Stats::setTop6, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_10, Stats::setTop10, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_12, Stats::setTop12, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.PLACE_TOP_25, Stats::setTop25, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.MATCHES_PLAYED, Stats::setMatches, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.KILLS, Stats::setKills, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(statName, StatType.MINUTES_PLAYED, Stats::setTimePlayed, keyToStatsFunction, valueFunction))
                return;
            attemptConsumeValue(statName, StatType.SCORE, Stats::setScore, keyToStatsFunction, valueFunction);
        });

        lifetime.setWins(summedIntegerPropertyOfStatsInstances(Stats::getWins, solo, duo, squad));
        lifetime.setTop3(summedIntegerPropertyOfStatsInstances(Stats::getTop3, solo, duo, squad));
        lifetime.setTop5(summedIntegerPropertyOfStatsInstances(Stats::getTop5, solo, duo, squad));
        lifetime.setTop6(summedIntegerPropertyOfStatsInstances(Stats::getTop6, solo, duo, squad));
        lifetime.setTop10(summedIntegerPropertyOfStatsInstances(Stats::getTop10, solo, duo, squad));
        lifetime.setTop12(summedIntegerPropertyOfStatsInstances(Stats::getTop12, solo, duo, squad));
        lifetime.setTop25(summedIntegerPropertyOfStatsInstances(Stats::getTop25, solo, duo, squad));
        lifetime.setMatches(summedIntegerPropertyOfStatsInstances(Stats::getMatches, solo, duo, squad));
        lifetime.setKills(summedIntegerPropertyOfStatsInstances(Stats::getKills, solo, duo, squad));
        lifetime.setTimePlayed(summedIntegerPropertyOfStatsInstances(Stats::getTimePlayed, solo, duo, squad));
        lifetime.setScore(summedIntegerPropertyOfStatsInstances(Stats::getScore, solo, duo, squad));

        Stream.of(solo, duo, squad, lifetime).forEach(stats -> {
            stats.setKillToDeathRatio(divide(stats.getKills(), stats.getMatches() - stats.getWins()));
            stats.setWinPercentage(percent(stats.getWins(), stats.getMatches()));
            stats.setKillsPerMin(divide(stats.getKills(), stats.getTimePlayed()));
            stats.setKillsPerMatch(divide(stats.getKills(), stats.getMatches()));
        });

        StatsGroup statsGroup = new StatsGroup();

        statsGroup.setSolo(solo);
        statsGroup.setDuo(duo);
        statsGroup.setSquad(squad);
        statsGroup.setLifetime(lifetime);

        return statsGroup;
    }

    private double divide(double a, double b) {
        return b == 0 ? -1 : a / b;
    }

    private double percent(double a, double b) {
        return divide(a, b) * 100;
    }

    private int summedIntegerPropertyOfStatsInstances(ToIntFunction<Stats> statsToIntFunction, Stats... stats) {
        return Arrays.stream(stats)
                .mapToInt(statsToIntFunction)
                .sum();
    }

    private boolean attemptConsumeValue(StatName statName, String desiredStatType, BiConsumer<Stats, Integer> statsAndValueBiConsumer, Function<StatName, Stats> statNameToStatsFunction, IntSupplier valueSupplier) {
        if (statName.getStatType().equals(desiredStatType) && statName.getPlatform().equals(platform)) {
            statsAndValueBiConsumer.accept(statNameToStatsFunction.apply(statName), valueSupplier.getAsInt());

            return true;
        }

        return false;
    }
}
