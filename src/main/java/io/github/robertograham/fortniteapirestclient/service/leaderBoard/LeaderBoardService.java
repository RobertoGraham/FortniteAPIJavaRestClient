package io.github.robertograham.fortniteapirestclient.service.leaderBoard;

import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;

import java.io.IOException;

public interface LeaderBoardService {

    LeaderBoard getWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) throws IOException;

    EnhancedLeaderBoard getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) throws IOException;
}
