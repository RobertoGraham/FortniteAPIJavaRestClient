package io.github.robertograham.fortniteapirestclient.service.leaderBoard.impl;

import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.LeaderBoard;
import io.github.robertograham.fortniteapirestclient.service.leaderBoard.model.request.GetWinsLeaderBoardRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
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
    private ResponseHandlerProvider mockResponseHandlerProvider;

    @Mock
    private AccountServiceImpl mockAccountService;

    private LeaderBoardServiceImpl leaderBoardService;

    @BeforeEach
    void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(mockHttpClient, mockResponseHandlerProvider, mockAccountService);
    }

    @Test
    @DisplayName("getWinsLeaderBoard returns leader board produced by response handler")
    void getWinsLeaderBoard() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String partyType = "partyType";
        String platform = "platform";
        String window = "window";
        int entryCount = 1;

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(platform, partyType, window, entryCount));
                    httpPost.setEntity(new StringEntity("[]", StandardCharsets.UTF_8));
                    httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
                    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    return httpPost;
                }).get().toString()));

        LeaderBoard leaderBoard = new LeaderBoard();

        LeaderBoardResponseHandler handler = response -> leaderBoard;

        GetWinsLeaderBoardRequest mockGetWinsLeaderBoardRequest = mock(GetWinsLeaderBoardRequest.class);

        when(mockGetWinsLeaderBoardRequest.getEntryCount()).thenReturn(entryCount);
        when(mockGetWinsLeaderBoardRequest.getPartyType()).thenReturn(partyType);
        when(mockGetWinsLeaderBoardRequest.getPlatform()).thenReturn(platform);
        when(mockGetWinsLeaderBoardRequest.getWindow()).thenReturn(window);
        when(mockGetWinsLeaderBoardRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(LeaderBoard.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenReturn(handler.handleResponse(null));

        assertEquals(leaderBoardService.getWinsLeaderBoard(mockGetWinsLeaderBoardRequest).get(), leaderBoard);

        verify(mockGetWinsLeaderBoardRequest).getWindow();
        verify(mockGetWinsLeaderBoardRequest).getPlatform();
        verify(mockGetWinsLeaderBoardRequest).getPartyType();
        verify(mockGetWinsLeaderBoardRequest).getEntryCount();
        verify(mockGetWinsLeaderBoardRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(LeaderBoard.class);
        verify(mockHttpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
    }

    @Test
    @DisplayName("getWinsLeaderBoard returns null on IOException")
    void getWinsLeaderBoardReturnsNull() throws IOException, ExecutionException, InterruptedException {
        String authHeaderValue = "authHeaderValue";
        String partyType = "partyType";
        String platform = "platform";
        String window = "window";
        int entryCount = 1;

        Supplier<HttpPost> desiredHttpPostSupplier = () -> argThat(argument ->
                argument.toString().equals(((Supplier<HttpPost>) () -> {
                    HttpPost httpPost = new HttpPost(Endpoint.winsLeaderBoard(platform, partyType, window, entryCount));
                    httpPost.setEntity(new StringEntity("[]", StandardCharsets.UTF_8));
                    httpPost.addHeader(HttpHeaders.AUTHORIZATION, authHeaderValue);
                    httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    return httpPost;
                }).get().toString()));

        LeaderBoardResponseHandler handler = response -> null;

        GetWinsLeaderBoardRequest mockGetWinsLeaderBoardRequest = mock(GetWinsLeaderBoardRequest.class);

        when(mockGetWinsLeaderBoardRequest.getEntryCount()).thenReturn(entryCount);
        when(mockGetWinsLeaderBoardRequest.getPartyType()).thenReturn(partyType);
        when(mockGetWinsLeaderBoardRequest.getPlatform()).thenReturn(platform);
        when(mockGetWinsLeaderBoardRequest.getWindow()).thenReturn(window);
        when(mockGetWinsLeaderBoardRequest.getAuthHeaderValue()).thenReturn(authHeaderValue);
        when(mockResponseHandlerProvider.handlerFor(LeaderBoard.class)).thenReturn(handler);
        when(mockHttpClient.execute(desiredHttpPostSupplier.get(), eq(handler))).thenThrow(IOException.class);

        assertNull(leaderBoardService.getWinsLeaderBoard(mockGetWinsLeaderBoardRequest).get());

        verify(mockGetWinsLeaderBoardRequest).getWindow();
        verify(mockGetWinsLeaderBoardRequest).getPlatform();
        verify(mockGetWinsLeaderBoardRequest).getPartyType();
        verify(mockGetWinsLeaderBoardRequest).getEntryCount();
        verify(mockGetWinsLeaderBoardRequest).getAuthHeaderValue();
        verify(mockResponseHandlerProvider).handlerFor(LeaderBoard.class);
        verify(mockHttpClient).execute(desiredHttpPostSupplier.get(), eq(handler));
    }

    private interface LeaderBoardResponseHandler extends ResponseHandler<LeaderBoard> {
    }
}
