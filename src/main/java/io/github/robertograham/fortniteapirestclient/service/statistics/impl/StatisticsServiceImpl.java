package io.github.robertograham.fortniteapirestclient.service.statistics.impl;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.constant.Platform;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticListStatGroupMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
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
    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public StatisticsServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public CompletableFuture<List<Statistic>> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) {
        return CompletableFuture.supplyAsync(() -> {
            HttpGet httpGet = new HttpGet(Endpoint.statsBattleRoyale(getBattleRoyaleStatisticsRequest.getAccountId(), getBattleRoyaleStatisticsRequest.getWindow()));
            httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBattleRoyaleStatisticsRequest.getAuthHeaderValue());

            Statistic[] statistics;

            try {
                statistics = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Statistic[].class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return statistics;

        }).handle(((statistics, throwable) -> {
            if (statistics == null) {
                LOG.error("Error while fetching statistics for account id {}, and window {}", getBattleRoyaleStatisticsRequest.getAccountId(), getBattleRoyaleStatisticsRequest.getWindow(), throwable);

                return null;
            }

            return Arrays.asList(statistics);
        }));
    }

    @Override
    public CompletableFuture<StatsGroup> getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) {
        return getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAccountId())
                .window(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getWindow())
                .authHeaderValue(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAuthHeaderValue())
                .build())
                .thenApplyAsync(statistics -> statistics != null ?
                        StatisticListStatGroupMapper.mapFrom(statistics, getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getPlatform())
                        : null);
    }

    @Override
    public CompletableFuture<Map<String, StatsGroup>> getSoloDuoSquadBattleRoyaleStatistics(GetSoloDuoSquadBattleRoyaleStatisticsRequest getSoloDuoSquadBattleRoyaleStatisticsRequest) {
        return getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                .accountId(getSoloDuoSquadBattleRoyaleStatisticsRequest.getAccountId())
                .window(getSoloDuoSquadBattleRoyaleStatisticsRequest.getWindow())
                .authHeaderValue(getSoloDuoSquadBattleRoyaleStatisticsRequest.getAuthHeaderValue())
                .build())
                .thenApplyAsync(statistics -> statistics != null ?
                        Stream.of(
                                getStatsGroupForPlatform(Platform.PC, statistics),
                                getStatsGroupForPlatform(Platform.XBOX_ONE, statistics),
                                getStatsGroupForPlatform(Platform.PLAYSTATION_4, statistics)
                        ).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
                        : null);
    }

    private SimpleEntry<String, StatsGroup> getStatsGroupForPlatform(String platform, List<Statistic> statistics) {
        return new SimpleEntry<>(platform, StatisticListStatGroupMapper.mapFrom(statistics, platform));
    }
}
