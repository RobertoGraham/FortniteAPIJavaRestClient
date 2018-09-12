package io.github.robertograham.fortniteapirestclient.service.statistics.impl;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import io.github.robertograham.fortniteapirestclient.EpicGamesUrl;
import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticListStatGroupMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private final HttpRequestFactory httpRequestFactory;

    public StatisticsServiceImpl(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }

    @Override
    public CompletableFuture<List<Statistic>> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) {
        getBattleRoyaleStatisticsRequest.log();

        return CompletableFuture.supplyAsync(() -> {
            Statistic[] statistics;

            try {
                statistics = httpRequestFactory.buildGetRequest(EpicGamesUrl.battleRoyaleStats(getBattleRoyaleStatisticsRequest.getAccountId(), getBattleRoyaleStatisticsRequest.getWindow()))
                        .setHeaders(new HttpHeaders().setAuthorization(getBattleRoyaleStatisticsRequest.getAuthorization()))
                        .execute()
                        .parseAs(Statistic[].class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return statistics;

        }).handle((statistics, throwable) -> {
            if (statistics == null) {
                if (throwable == null)
                    LOG.warn("No statistics found for account id {}, and window {}", getBattleRoyaleStatisticsRequest.getAccountId(), getBattleRoyaleStatisticsRequest.getWindow());
                else
                    LOG.error("Error while fetching statistics for account id {}, and window {}", getBattleRoyaleStatisticsRequest.getAccountId(), getBattleRoyaleStatisticsRequest.getWindow(), throwable);

                return null;
            }

            return Arrays.asList(statistics);
        });
    }

    @Override
    public CompletableFuture<StatsGroup> getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) {
        return getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAccountId())
                .window(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getWindow())
                .authorization(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAuthorization())
                .build())
                .thenApplyAsync(statistics -> statistics != null ?
                        StatisticListStatGroupMapper.mapFrom(statistics, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getPlatform())
                        : null);
    }

    @Override
    public CompletableFuture<Map<Platform, StatsGroup>> getSoloDuoSquadBattleRoyaleStatistics(GetSoloDuoSquadBattleRoyaleStatisticsRequest getSoloDuoSquadBattleRoyaleStatisticsRequest) {
        return getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(getSoloDuoSquadBattleRoyaleStatisticsRequest.getAccountId())
                .window(getSoloDuoSquadBattleRoyaleStatisticsRequest.getWindow())
                .authorization(getSoloDuoSquadBattleRoyaleStatisticsRequest.getAuthorization())
                .build())
                .thenApplyAsync(statistics -> statistics != null ?
                        Stream.of(
                                getStatsGroupForPlatform(Platform.PC, statistics),
                                getStatsGroupForPlatform(Platform.XBOX_ONE, statistics),
                                getStatsGroupForPlatform(Platform.PLAYSTATION_4, statistics),
                                getStatsGroupForPlatform(Platform.NINTENDO_SWITCH, statistics),
                                getStatsGroupForPlatform(Platform.IOS, statistics)
                        ).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
                        : null);
    }

    private SimpleEntry<Platform, StatsGroup> getStatsGroupForPlatform(Platform platform, List<Statistic> statistics) {
        return new SimpleEntry<>(platform, StatisticListStatGroupMapper.mapFrom(statistics, platform));
    }
}
