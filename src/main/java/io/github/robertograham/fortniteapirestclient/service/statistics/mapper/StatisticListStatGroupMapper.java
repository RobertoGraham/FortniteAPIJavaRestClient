package io.github.robertograham.fortniteapirestclient.service.statistics.mapper;

import io.github.robertograham.fortniteapirestclient.domain.StatName;
import io.github.robertograham.fortniteapirestclient.domain.Stats;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatType;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.util.StatNameHelper;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticListStatGroupMapper {

    private static final Map<String, BiConsumer<Stats, Integer>> statTypeToStatsMutator = newMap(
            entry(StatType.PLACE_TOP_1, Stats::setWins),
            entry(StatType.PLACE_TOP_3, Stats::setTop3),
            entry(StatType.PLACE_TOP_5, Stats::setTop5),
            entry(StatType.PLACE_TOP_6, Stats::setTop6),
            entry(StatType.PLACE_TOP_10, Stats::setTop10),
            entry(StatType.PLACE_TOP_12, Stats::setTop12),
            entry(StatType.PLACE_TOP_25, Stats::setTop25),
            entry(StatType.MATCHES_PLAYED, Stats::setMatches),
            entry(StatType.KILLS, Stats::setKills),
            entry(StatType.MINUTES_PLAYED, Stats::setTimePlayed),
            entry(StatType.SCORE, Stats::setScore)
    );

    private static final Map<String, Function<StatsGroup, Stats>> partyTypeToStatsGroupStatsAccessor = newMap(
            entry(PartyType.SOLO, StatsGroup::getSolo),
            entry(PartyType.DUO, StatsGroup::getDuo),
            entry(PartyType.SQUAD, StatsGroup::getSquad)
    );

    public static StatsGroup mapFrom(List<Statistic> statistics, String platform) {
        StatsGroup statsGroup = new StatsGroup();

        Stats solo = new Stats();
        Stats duo = new Stats();
        Stats squad = new Stats();

        statsGroup.setSolo(solo);
        statsGroup.setDuo(duo);
        statsGroup.setSquad(squad);

        statistics.stream()
                .collect(Collectors.toMap(statistic -> StatNameHelper.parse(statistic.getName()), Statistic::getValue))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getPlatform().equals(platform))
                .forEach(entry -> consumeValue(statsGroup, entry.getKey(), entry.getValue()));

        Stream.of(solo, duo, squad).forEach(stats -> {
            stats.setKillToDeathRatio(divide(stats.getKills(), stats.getMatches() - stats.getWins()));
            stats.setWinPercentage(percent(stats.getWins(), stats.getMatches()));
            stats.setKillsPerMin(divide(stats.getKills(), stats.getTimePlayed()));
            stats.setKillsPerMatch(divide(stats.getKills(), stats.getMatches()));
        });

        return statsGroup;
    }

    private static double divide(double a, double b) {
        return b == 0 ? 0 : a / b;
    }

    private static double percent(double a, double b) {
        return divide(a, b) * 100;
    }

    private static void consumeValue(StatsGroup statsGroup, StatName statName, int statValue) {
        Optional.ofNullable(partyTypeToStatsGroupStatsAccessor.get(statName.getPartyType()))
                .ifPresent(statsGroupStatsAccessor ->
                        Optional.ofNullable(statTypeToStatsMutator.get(statName.getStatType()))
                                .ifPresent(statsMutator ->
                                        statsMutator.accept(statsGroupStatsAccessor.apply(statsGroup), statValue)
                                )
                );
    }

    private static <K, V> SimpleEntry<K, V> entry(K key, V value) {
        return new SimpleEntry<>(key, value);
    }

    @SafeVarargs
    private static <K, V> Map<K, V> newMap(SimpleEntry<K, V>... entries) {
        return Arrays.stream(entries)
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }
}
