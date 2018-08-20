package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.Cohort;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseRequestUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class LeaderBoardServiceImplTest {

    @Mock
    private CloseableHttpClient mockHttpClient;

    @Mock
    private ResponseRequestUtil mockResponseRequestUtil;

    @Mock
    private AccountServiceImpl mockAccountService;

    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeEach
    void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(mockHttpClient, mockResponseRequestUtil, mockAccountService);
    }

    @Test
    @DisplayName("getWinsLeaderBoard returns leader board produced by response handler")
    void getWinsLeaderBoard() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String partyType = "partyType";
        String platform = "platform";
        String window = "window";
        String inAppId = "inAppId";
        int entryCount = 1;

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument instanceof HttpGet
                        && ((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.cohort(inAppId, platform, partyType));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString().equals(String.valueOf(argument)));

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument instanceof HttpPost
                        && ((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(platform, partyType, window, entryCount));
                    httpPost.setEntity(new StringEntity("[]", StandardCharsets.UTF_8));
                    httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
                    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    return httpPost;
                }).get().toString().equals(String.valueOf(argument)));

        LeaderBoard leaderBoard = new LeaderBoard();
        Cohort cohort = new Cohort();

        LeaderBoardResponseHandler leaderBoardResponseHandler = response -> leaderBoard;
        CohortResponseHandler cohortResponseHandler = response -> cohort;

        GetWinsLeaderBoardRequest mockGetWinsLeaderBoardRequest = mock(GetWinsLeaderBoardRequest.class);

        when(mockGetWinsLeaderBoardRequest.getEntryCount()).thenReturn(entryCount);
        when(mockGetWinsLeaderBoardRequest.getPartyType()).thenReturn(partyType);
        when(mockGetWinsLeaderBoardRequest.getPlatform()).thenReturn(platform);
        when(mockGetWinsLeaderBoardRequest.getWindow()).thenReturn(window);
        when(mockGetWinsLeaderBoardRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockGetWinsLeaderBoardRequest.getInAppId()).thenReturn(inAppId);
        doReturn(cohortResponseHandler).when(mockResponseRequestUtil).responseHandlerFor(Cohort.class);
        doReturn(leaderBoardResponseHandler).when(mockResponseRequestUtil).responseHandlerFor(LeaderBoard.class);
        doReturn(cohortResponseHandler.handleResponse(null)).when(mockHttpClient).execute(any(), eq(cohortResponseHandler));
        doReturn(leaderBoardResponseHandler.handleResponse(null)).when(mockHttpClient).execute(any(), eq(leaderBoardResponseHandler));

        assertEquals(leaderBoardService.getWinsLeaderBoard(mockGetWinsLeaderBoardRequest).get(), leaderBoard);

        verify(mockGetWinsLeaderBoardRequest).getWindow();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getPlatform();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getPartyType();
        verify(mockGetWinsLeaderBoardRequest).getEntryCount();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getAuthHeaderValue();
        verify(mockGetWinsLeaderBoardRequest).getInAppId();
        verify(mockResponseRequestUtil).responseHandlerFor(Cohort.class);
        verify(mockResponseRequestUtil).responseHandlerFor(LeaderBoard.class);
        verify(mockHttpClient).execute(any(), eq(cohortResponseHandler));
        verify(mockHttpClient).execute(any(), eq(leaderBoardResponseHandler));
    }

    @Test
    @DisplayName("getWinsLeaderBoard returns null on IOException")
    void getWinsLeaderBoardReturnsNull() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String partyType = "partyType";
        String platform = "platform";
        String window = "window";
        String inAppId = "inAppId";
        int entryCount = 1;

        Supplier<HttpGet> desiredHttpGetSupplier = () -> argThat(argument ->
                argument instanceof HttpGet
                        && ((Supplier<HttpGet>) () -> {
                    HttpGet httpGet = new HttpGet(Endpoint.cohort(inAppId, platform, partyType));
                    httpGet.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);

                    return httpGet;
                }).get().toString().equals(String.valueOf(argument)));

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument instanceof HttpPost
                        && ((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(platform, partyType, window, entryCount));
                    httpPost.setEntity(new StringEntity("[]", StandardCharsets.UTF_8));
                    httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
                    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    return httpPost;
                }).get().toString().equals(String.valueOf(argument)));

        Cohort cohort = new Cohort();

        LeaderBoardResponseHandler leaderBoardResponseHandler = response -> null;
        CohortResponseHandler cohortResponseHandler = response -> cohort;

        GetWinsLeaderBoardRequest mockGetWinsLeaderBoardRequest = mock(GetWinsLeaderBoardRequest.class);

        when(mockGetWinsLeaderBoardRequest.getEntryCount()).thenReturn(entryCount);
        when(mockGetWinsLeaderBoardRequest.getPartyType()).thenReturn(partyType);
        when(mockGetWinsLeaderBoardRequest.getPlatform()).thenReturn(platform);
        when(mockGetWinsLeaderBoardRequest.getWindow()).thenReturn(window);
        when(mockGetWinsLeaderBoardRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockGetWinsLeaderBoardRequest.getInAppId()).thenReturn(inAppId);
        doReturn(cohortResponseHandler).when(mockResponseRequestUtil).responseHandlerFor(Cohort.class);
        doReturn(leaderBoardResponseHandler).when(mockResponseRequestUtil).responseHandlerFor(LeaderBoard.class);
        doReturn(cohortResponseHandler.handleResponse(null)).when(mockHttpClient).execute(any(), eq(cohortResponseHandler));
        doThrow(IOException.class).when(mockHttpClient).execute(any(), eq(leaderBoardResponseHandler));

        assertNull(leaderBoardService.getWinsLeaderBoard(mockGetWinsLeaderBoardRequest).get());

        verify(mockGetWinsLeaderBoardRequest).getWindow();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getPlatform();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getPartyType();
        verify(mockGetWinsLeaderBoardRequest).getEntryCount();
        verify(mockGetWinsLeaderBoardRequest, times(2)).getAuthHeaderValue();
        verify(mockGetWinsLeaderBoardRequest).getInAppId();
        verify(mockResponseRequestUtil).responseHandlerFor(Cohort.class);
        verify(mockResponseRequestUtil).responseHandlerFor(LeaderBoard.class);
        verify(mockHttpClient).execute(any(), eq(cohortResponseHandler));
        verify(mockHttpClient).execute(any(), eq(leaderBoardResponseHandler));
    }

    private interface LeaderBoardResponseHandler extends ResponseHandler<LeaderBoard> {
    }

    private interface CohortResponseHandler extends ResponseHandler<Cohort> {
    }
}
