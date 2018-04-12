package io.github.robertograham.fortniteapirestclient.service.statistics.impl;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticListStatGroupMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ObjectMapperResponseHandler;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public List<Statistic> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) throws IOException {
        return Arrays.asList(Request.Get(Endpoint.statsBattleRoyale(getBattleRoyaleStatisticsRequest.getAccountId()))
                .addHeader(HttpHeaders.AUTHORIZATION, getBattleRoyaleStatisticsRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(ObjectMapperResponseHandler.thatProduces(Statistic[].class)));
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
