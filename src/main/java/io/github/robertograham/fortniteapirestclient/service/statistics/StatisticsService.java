package io.github.robertograham.fortniteapirestclient.service.statistics;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;

import java.io.IOException;
import java.util.List;

public interface StatisticsService {

    List<Statistic> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest) throws IOException;

    StatsGroup getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest) throws IOException;
}
