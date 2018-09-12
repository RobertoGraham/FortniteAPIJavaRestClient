package io.github.robertograham.fortniteapirestclient.service.statistics;

import io.github.robertograham.fortniteapirestclient.domain.StatsGroup;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.Platform;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetBattleRoyaleStatisticsRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
import io.github.robertograham.fortniteapirestclient.service.statistics.model.request.GetSoloDuoSquadBattleRoyaleStatisticsRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface StatisticsService {

    CompletableFuture<List<Statistic>> getBattleRoyaleStatistics(GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest);

    CompletableFuture<StatsGroup> getSoloDuoSquadBattleRoyaleStatisticsByPlatform(GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest);

    CompletableFuture<Map<Platform, StatsGroup>> getSoloDuoSquadBattleRoyaleStatistics(GetSoloDuoSquadBattleRoyaleStatisticsRequest getSoloDuoSquadBattleRoyaleStatisticsRequest);
}
