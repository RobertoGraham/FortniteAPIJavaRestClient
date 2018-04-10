package io.github.robertograham.fortniteapirestclient.service.statistics.mapper;

import io.github.robertograham.fortniteapirestclient.domain.Stats;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.util.Mapper;

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
            String key = statistic.getName();
            IntSupplier valueFunction = statistic::getValue;
            Function<String, Stats> keyToStatsFunction = statsName -> statsName.contains(PartyType.SOLO) ? solo : statsName.contains(PartyType.DUO) ? duo : squad;

            if (attemptConsumeValue(key, "placetop1", Stats::setWins, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop3", Stats::setTop3, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop5", Stats::setTop5, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop6", Stats::setTop6, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop10", Stats::setTop10, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop12", Stats::setTop12, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "placetop25", Stats::setTop25, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "matchesplayed", Stats::setMatches, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "kills", Stats::setKills, keyToStatsFunction, valueFunction))
                return;
            if (attemptConsumeValue(key, "minutesplayed", Stats::setTimePlayed, keyToStatsFunction, valueFunction))
                return;
            attemptConsumeValue(key, "score", Stats::setScore, keyToStatsFunction, valueFunction);
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
        return b == 0 ? 0 : a / b;
    }

    private double percent(double a, double b) {
        return divide(a, b) * 100;
    }

    private int summedIntegerPropertyOfStatsInstances(ToIntFunction<Stats> statsToIntFunction, Stats... stats) {
        return Arrays.stream(stats)
                .mapToInt(statsToIntFunction)
                .sum();
    }

    private boolean attemptConsumeValue(String key, String desiredKeyFragment, BiConsumer<Stats, Integer> statsAndValueBiConsumer, Function<String, Stats> keyToStatsFunction, IntSupplier valueSupplier) {
        if (key.contains(String.format("%s_%s", desiredKeyFragment, platform))) {
            statsAndValueBiConsumer.accept(keyToStatsFunction.apply(key), valueSupplier.getAsInt());

            return true;
        }

        return false;
    }
}
