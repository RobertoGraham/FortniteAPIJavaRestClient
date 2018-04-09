package io.github.robertograham.fortniteapirestclient.service.statistics.impl;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.statistics.StatisticsService;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticListStatGroupMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.mapper.StatisticsJsonStatisticsListMapper;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {

    private ResponseHandler<String> responseHandler;
    private StatisticsJsonStatisticsListMapper statisticsJsonStatisticsListMapper;

    public StatisticsServiceImpl() {
        responseHandler = new BasicResponseHandler();
        statisticsJsonStatisticsListMapper = new StatisticsJsonStatisticsListMapper();
    }

    @Override
    public List<Statistic> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) throws IOException {
        return statisticsJsonStatisticsListMapper.mapFrom(Request.Get(Endpoint.statsBattleRoyale(getBattleRoyaleStatisticsRequest.getAccountId()))
                .addHeader(HttpHeaders.AUTHORIZATION, getBattleRoyaleStatisticsRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(responseHandler));
    }

    @Override
    public StatsGroup getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) throws IOException {
        return new StatisticListStatGroupMapper(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getPlatform())
                .mapFrom(getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest.Builder.newInstance()
                        .accountId(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAccountId())
                        .authHeaderValue(getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.getAuthHeaderValue())
                        .build()));
    }
}
