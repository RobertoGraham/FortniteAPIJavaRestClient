package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoard;
import io.github.robertograham.fortniteapirestclient.domain.EnhancedLeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.ExternalAuth;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.LeaderBoardService;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoardEntry;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final CloseableHttpClient httpClient;
    private final ResponseHandlerProvider responseHandlerProvider;
    private final AccountService accountService;

    public LeaderBoardServiceImpl(CloseableHttpClient httpClient, ResponseHandlerProvider responseHandlerProvider, AccountService accountService) {
        this.httpClient = httpClient;
        this.responseHandlerProvider = responseHandlerProvider;
        this.accountService = accountService;
    }

    @Override
    public LeaderBoard getWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) throws IOException {
        HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(getWinsLeaderBoardRequest.getPlatform(), getWinsLeaderBoardRequest.getPartyType()));
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, getWinsLeaderBoardRequest.getAuthHeaderValue());

        return httpClient.execute(httpPost, responseHandlerProvider.handlerFor(LeaderBoard.class));
    }

    @Override
    public EnhancedLeaderBoard getEnhancedWinsLeaderBoard(GetWinsLeaderBoardRequest getWinsLeaderBoardRequest) throws IOException {
        LeaderBoard leaderBoard = getWinsLeaderBoard(getWinsLeaderBoardRequest);

        EnhancedLeaderBoard enhancedLeaderBoard = new EnhancedLeaderBoard();

        List<Account> accounts = accountService.getAccounts(GetAccountsRequest.builder(leaderBoard.getEntries().stream()
                .map(LeaderBoardEntry::getAccountId)
                .map(id -> id.replace("-", ""))
                .collect(Collectors.toSet()))
                .authHeaderValue(getWinsLeaderBoardRequest.getAuthHeaderValue())
                .build());

        enhancedLeaderBoard.setEntries(leaderBoard.getEntries().stream()
                .map(entry -> {
                    EnhancedLeaderBoardEntry enhancedLeaderBoardEntry = new EnhancedLeaderBoardEntry();

                    Account matchingAccount = accounts.stream()
                            .filter(account -> account.getId().equals(entry.getAccountId().replace("-", "")))
                            .findFirst()
                            .orElse(null);

                    if (matchingAccount != null) {
                        String displayName = matchingAccount.getDisplayName();

                        if (displayName == null) {
                            List<ExternalAuth> externalAuths = new ArrayList<>(matchingAccount.getExternalAuths().values());

                            if (externalAuths.size() > 0)
                                displayName = externalAuths.get(0).getExternalDisplayName();
                        }

                        enhancedLeaderBoardEntry.setDisplayName(displayName);
                    }

                    enhancedLeaderBoardEntry.setWins(entry.getValue());
                    enhancedLeaderBoardEntry.setRank(entry.getRank());
                    enhancedLeaderBoardEntry.setAccountId(entry.getAccountId());

                    return enhancedLeaderBoardEntry;
                })
                .collect(Collectors.toList()));

        return enhancedLeaderBoard;
    }
}
