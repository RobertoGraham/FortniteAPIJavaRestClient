package io.github.robertograham.fortniteapirestclient.service.statistics.impl;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticListStatGroupMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;

    public StatisticsServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
    }

    @Override
    public List<Statistic> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) throws IOException {
        HttpGet httpGet = new HttpGet(Endpoint.statsBattleRoyale(getBattleRoyaleStatisticsRequest.getAccountId()));
        httpGet.addHeader(HttpHeaders.AUTHORIZATION, getBattleRoyaleStatisticsRequest.getAuthHeaderValue());

        Statistic[] statistics = httpClient.execute(httpGet, responseHandlerProvider.handlerFor(Statistic[].class));

        return statistics != null ? Arrays.asList(statistics) : new ArrayList<>();
    }

    @Override
    public StatsGroup getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) throws IOException {
        return new StatisticListStatGroupMapper(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getPlatform())
                .mapFrom(getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.builder()
                        .accountId(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAccountId())
                        .authHeaderValue(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAuthHeaderValue())
                        .build()));
    }
}
