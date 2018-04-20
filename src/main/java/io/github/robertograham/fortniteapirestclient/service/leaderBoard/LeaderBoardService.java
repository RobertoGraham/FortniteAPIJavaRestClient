package io.github.robertograham.fortniteapirestclient.service.leaderBoard;

import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface LeaderBoardService {

    CompletableFuture<LeaderBoard> getWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest);

    CompletableFuture<EnhancedLeaderBoard> getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest);
}
