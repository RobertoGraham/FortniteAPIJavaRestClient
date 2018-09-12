package io.github.robertograham.fortniteapirestclient;

import com.google.api.client.http.GenericUrl;
import io.github.robertograham.fortniteapirestclient.domain.StatName;
import io.github.robertograham.fortniteapirestclient.domain.enumeration.*;

import java.util.Set;
import java.util.stream.Collectors;

public class EpicGamesUrl extends GenericUrl {

    private EpicGamesUrl(String encodedUrl) {
        super(encodedUrl);
    }

    public static EpicGamesUrl oAuthToken() {
        return new EpicGamesUrl("https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/token");
    }

    public static EpicGamesUrl oAuthExchange() {
        return new EpicGamesUrl("https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/exchange");
    }

    public static EpicGamesUrl killSession(String accessToken) {
        return new EpicGamesUrl("https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/sessions/kill/" + accessToken);
    }

    public static EpicGamesUrl accountByUsername(String username) {
        return new EpicGamesUrl("https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + username);
    }

    public static EpicGamesUrl accountById(Set<String> ids) {
        return new EpicGamesUrl(
                "https://account-public-service-prod03.ol.epicgames.com/account/api/public/account?" + ids.stream()
                        .map(id -> "accountId=" + id)
                        .collect(Collectors.joining("&"))
        );
    }

    public static EpicGamesUrl winsLeaderBoard(Platform platform, PartyType partyType, StatWindow window, int entryCount) {
        StatName statName = new StatName();

        statName.setGameMode(GameMode.BATTLE_ROYALE);
        statName.setStatType(StatType.PLACE_TOP_1);
        statName.setPlatform(platform);
        statName.setPartyType(partyType);

        return new EpicGamesUrl(
                "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/leaderboards/type/global/stat/" + statName.formatted() +
                        "/window/" + window.getCode() +
                        "?ownertype=1&pageNumber=0&itemsPerPage=" + entryCount
        );
    }

    public static EpicGamesUrl cohort(String inAppId, Platform platform, PartyType partyType) {
        return new EpicGamesUrl(
                "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/game/v2/leaderboards/cohort/" + inAppId +
                        "?playlist=" + platform.getCode() + "_m0_" + partyType.getCode()
        );
    }

    public static EpicGamesUrl battleRoyaleStats(String accountId, StatWindow window) {
        return new EpicGamesUrl(
                "https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + accountId +
                        "/bulk/window/" + window.getCode()
        );
    }
}
