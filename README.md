[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/fortnite-api-rest-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.robertograham/fortnite-api-rest-client)
# FortniteAPIJavaRestClient
Java client for the Fortnite API

## In the wild
I have written a small app, backed by FortniteAPIJavaRestClient, with Spring Boot 2 and React. [https://fortnite-stats-app.herokuapp.com/](https://fortnite-stats-app.herokuapp.com/)

## Install
```xml
<dependency>
    <groupId>io.github.robertograham</groupId>
    <artifactId>fortnite-api-rest-client</artifactId>
    <version>5.1.1</version>
</dependency>
```
## Acquiring an EpicGames Launcher Token and a Fortnite Client Token
Follow the steps in [this project's README](https://github.com/qlaffont/fortnite-api#init).

## Logging in
Once you instantiate a FortniteApiRestClient it will automatically attempt to authenticate itself unless auto login is disabled.

If auto login is left enabled, any errors during authentication will not be thrown
A valid session can be detected by calling `fortniteApiRestClient.isSessionValid()`
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;

public class Main {

    public static void main(String[] args) {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();
    }
}
```
Disable auto login if you want to be notified of authentication failure
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .disableAutoLogin()
                .build();

        fortniteApiRestClient.login()
                .whenCompleteAsync((voidInstance, throwable) -> System.out.println(throwable == null ? "logged in" : "failed log in"))
                .thenRun(fortniteApiRestClient::close)
                .get();
    }
}
```
## Closing
When you are finished with the client, remember to close it. If you don't, the thread to reauthenticate when a token expires will linger. Closing the client will also end your session on the Epic Games servers which prevents a too many sessions error when you login again.
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;

public class Main {

    public static void main(String[] args) {
        FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .disableAutoLogin()
                .build()
                .close();
    }
}
```

## Methods
### Account lookup
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();

        fortniteApiRestClient.account("RGrhm")
                .thenApplyAsync(Account::getId)
                .handle((accountId, nullPointerException) -> nullPointerException == null ? accountId : "Account was null")
                .thenAcceptAsync(System.out::println)
                .get();
    }
}
```
Will either print A or B, depending on if the account exists.

A: `a187f91659254929b833f6fbbd724fda`

B: `Account was null`
### Retrieve enhanced Battle Royale stats by platform
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.constant.Platform;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatWindow;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();

        fortniteApiRestClient.account("RGrhm")
                .thenCompose(account -> fortniteApiRestClient.enhancedBattleRoyaleStatsByPlatform(account.getId(), Platform.PLAYSTATION_4, StatWindow.ALL_TIME))
                .handle((enhancedBattleRoyaleStats, nullPointerException) -> nullPointerException == null ? enhancedBattleRoyaleStats : null)
                .thenAcceptAsync(System.out::println)
                .get();
    }
}
```
Will either print A or B, depending on if the account exists. All values will be 0 if the account has not played on this platform

A: `StatsGroup{solo=Stats{wins=6, top3=0, top5=0, top6=0, top10=180, top12=0, top25=306, killToDeathRatio=0.48074179743223966, winPercentage=0.8486562942008486, matches=707, kills=337, killsPerMin=0.3231064237775647, killsPerMatch=0.4766619519094767, timePlayed=1043, score=122794}, duo=Stats{wins=1, top3=0, top5=12, top6=0, top10=0, top12=34, top25=0, killToDeathRatio=0.38181818181818183, winPercentage=0.9009009009009009, matches=111, kills=42, killsPerMin=0.8076923076923077, killsPerMatch=0.3783783783783784, timePlayed=52, score=16908}, squad=Stats{wins=0, top3=10, top5=0, top6=23, top10=0, top12=0, top25=0, killToDeathRatio=0.32, winPercentage=0.0, matches=150, kills=48, killsPerMin=0.09775967413441955, killsPerMatch=0.32, timePlayed=491, score=18900}, lifetime=Stats{wins=7, top3=10, top5=12, top6=23, top10=180, top12=34, top25=306, killToDeathRatio=0.44432882414151925, winPercentage=0.7231404958677686, matches=968, kills=427, killsPerMin=0.2692307692307692, killsPerMatch=0.44111570247933884, timePlayed=1586, score=158602}}`

B: `null`
### Retrieve raw Battle Royale stats
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatWindow;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();

        fortniteApiRestClient.account("RGrhm")
                .thenCompose(account -> fortniteApiRestClient.battleRoyaleStats(account.getId(), StatWindow.ALL_TIME))
                .handle((battleRoyaleStats, nullPointerException) -> nullPointerException == null ? battleRoyaleStats : null)
                .thenAcceptAsync(System.out::println)
                .get();
    }
}
```
Will either print A or B, depending on if the account exists

A: `[Statistic{name='br_score_pc_m0_p10', value=11730, window='alltime', ownerType=1}, Statistic{name='br_score_pc_m0_p9', value=2432, window='alltime', ownerType=1}, Statistic{name='br_matchesplayed_pc_m0_p10', value=94, window='alltime', ownerType=1}, Statistic{name='br_placetop3_pc_m0_p9', value=1, window='alltime', ownerType=1}, Statistic{name='br_score_pc_m0_p2', value=7269, window='alltime', ownerType=1}, Statistic{name='br_kills_pc_m0_p2', value=20, window='alltime', ownerType=1}, Statistic{name='br_lastmodified_pc_m0_p9', value=1524323185, window='alltime', ownerType=1}, Statistic{name='br_lastmodified_pc_m0_p2', value=1524341207, window='alltime', ownerType=1}, Statistic{name='br_matchesplayed_pc_m0_p2', value=69, window='alltime', ownerType=1}, Statistic{name='br_kills_pc_m0_p9', value=2, window='alltime', ownerType=1}, Statistic{name='br_matchesplayed_pc_m0_p9', value=17, window='alltime', ownerType=1}, Statistic{name='br_placetop12_pc_m0_p10', value=21, window='alltime', ownerType=1}, Statistic{name='br_lastmodified_pc_m0_p10', value=1524338522, window='alltime', ownerType=1}, Statistic{name='br_placetop1_pc_m0_p10', value=1, window='alltime', ownerType=1}, Statistic{name='br_placetop10_pc_m0_p2', value=5, window='alltime', ownerType=1}, Statistic{name='br_placetop25_pc_m0_p2', value=13, window='alltime', ownerType=1}, Statistic{name='br_placetop5_pc_m0_p10', value=7, window='alltime', ownerType=1}, Statistic{name='br_kills_pc_m0_p10', value=15, window='alltime', ownerType=1}, Statistic{name='br_placetop6_pc_m0_p9', value=2, window='alltime', ownerType=1}]`

B: `null`
### Retrieve enhanced wins leaderboard
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.constant.Platform;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatWindow;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();

        fortniteApiRestClient.enhancedWinsLeaderBoard(Platform.PC, PartyType.SOLO, StatWindow.WEEKLY, 100)
                .thenAcceptAsync(System.out::println)
                .get();
    }
}
```
Will print

`EnhancedLeaderBoard{entries=[EnhancedLeaderBoardEntry{displayName='twitch_bogdanakh', accountId='385c4d9a-b7e3-498d-b533-ff4d2d9f4c5b', rank=1, wins=915}, EnhancedLeaderBoardEntry{displayName='TwitchTV.lavak3_', accountId='155234bb-adaa-4e81-99a7-b2d413722290', rank=2, wins=811}, EnhancedLeaderBoardEntry{displayName='Agares29_Twitch', accountId='c083d220-0d65-4b25-a87c-0c48cb76c902', rank=3, wins=791}, EnhancedLeaderBoardEntry{displayName='Myboosting.com2', accountId='0041d08b-edc5-48d9-a223-0c4a28550594', rank=4, wins=776}, EnhancedLeaderBoardEntry{displayName='Twitch_DutchHawk', accountId='6f5c77ad-ef1c-4e47-bc33-f1f0c8b4b263', rank=5, wins=738}, EnhancedLeaderBoardEntry{displayName='twitchtvLIKANDOO', accountId='e94c3e05-2844-4339-8803-285171550b45', rank=6, wins=701}, EnhancedLeaderBoardEntry{displayName='Twitch APEXENITH', accountId='13b3c774-20da-4101-a213-e1f646b316a9', rank=7, wins=675}, EnhancedLeaderBoardEntry{displayName='SypherPK', accountId='cfd16ec5-4126-497c-a574-85c1ee1987dc', rank=8, wins=629}, EnhancedLeaderBoardEntry{displayName='VaxitylolMIXERtv', accountId='b94176db-4c25-4f90-99fb-2bd8e8ae0f94', rank=9, wins=627}, EnhancedLeaderBoardEntry{displayName='RuralKTmixer.com', accountId='a9467569-462d-4149-bc43-8550c03a45c9', rank=10, wins=624}, EnhancedLeaderBoardEntry{displayName='TwitchExoticChaotic', accountId='160376f1-a670-4c1b-b260-ce7b2bf94549', rank=11, wins=604}, EnhancedLeaderBoardEntry{displayName='Aêrøeu', accountId='1da00db9-d5ae-40fa-925f-e48a92bfcd09', rank=12, wins=590}, EnhancedLeaderBoardEntry{displayName='Twitch WickesyM8', accountId='ffbb0eff-7cf0-433f-8cbf-9b5b30d57202', rank=13, wins=559}, EnhancedLeaderBoardEntry{displayName='Twitch_Svennoss', accountId='bd98e3aa-14d4-4c46-9417-827242e0105c', rank=14, wins=550}, EnhancedLeaderBoardEntry{displayName='Worthyyy', accountId='f1081995-d117-471d-860e-5eb41275975c', rank=15, wins=542}, EnhancedLeaderBoardEntry{displayName='KingRichard15', accountId='28bad584-d9aa-440b-99ec-488bbd3d4e72', rank=16, wins=534}, EnhancedLeaderBoardEntry{displayName='Starke2k', accountId='501aff48-7767-4bbc-8350-a7b190db2ec3', rank=17, wins=532}, EnhancedLeaderBoardEntry{displayName='BOT Tênnp0', accountId='1169e1f9-9c1a-4cb6-ba77-282c6d84eb74', rank=18, wins=532}, EnhancedLeaderBoardEntry{displayName='babam_', accountId='54de145b-5873-4f48-8994-dd008e30f26a', rank=19, wins=520}, EnhancedLeaderBoardEntry{displayName='twitch_muddax', accountId='b5dd0491-ee8e-4e15-b32e-f8e704b47dbe', rank=20, wins=507}, EnhancedLeaderBoardEntry{displayName='FNC_Ettnix', accountId='5359db25-7029-4c59-b6ec-8f57e816f6a7', rank=21, wins=497}, EnhancedLeaderBoardEntry{displayName='TTV_WishYouLuckk', accountId='09bd41d2-a44c-46d4-97c4-ffb6dd368981', rank=22, wins=494}, EnhancedLeaderBoardEntry{displayName='ZapdiusAdiarak', accountId='afeca5d0-401f-4640-9095-b81510c265ac', rank=23, wins=488}, EnhancedLeaderBoardEntry{displayName='penutty.twitch', accountId='2b6d451f-f196-401d-b56c-7f1ba41f63fe', rank=24, wins=484}, EnhancedLeaderBoardEntry{displayName='Keepo_', accountId='0aa6c0ae-745b-440d-b246-95085002e053', rank=25, wins=467}, EnhancedLeaderBoardEntry{displayName='twitchstonde1337', accountId='f5b23934-2e7b-490d-86c9-3a5db53abf06', rank=26, wins=463}, EnhancedLeaderBoardEntry{displayName='xJeRMx.tv', accountId='93cfb726-aebb-4eb0-a5ce-4a0ea42d3498', rank=27, wins=462}, EnhancedLeaderBoardEntry{displayName='Twitch FulmerLoL', accountId='0247ee0d-eae2-432f-8113-3edaa2ae8e63', rank=28, wins=455}, EnhancedLeaderBoardEntry{displayName='ComradeDurachek', accountId='1ef002fc-41b7-46e2-afb4-ba3b23e1afad', rank=29, wins=451}, EnhancedLeaderBoardEntry{displayName='Evanggelion', accountId='d4a43646-306c-4dc5-8f53-49d89c0e9045', rank=30, wins=451}, EnhancedLeaderBoardEntry{displayName='Blatty', accountId='70639c8f-de7d-4a25-a0ad-09ecd5a2b5b6', rank=31, wins=441}, EnhancedLeaderBoardEntry{displayName='Twitch_Aphostle', accountId='ba5be2d1-7b42-4b8e-a681-3bf84648e15f', rank=32, wins=439}, EnhancedLeaderBoardEntry{displayName='Ezloow', accountId='be5497b1-0d14-4996-86bc-970130fb38cc', rank=33, wins=428}, EnhancedLeaderBoardEntry{displayName='Martoz YT', accountId='6feb4bd8-85b4-4bf8-a6ce-3b986d35407f', rank=34, wins=426}, EnhancedLeaderBoardEntry{displayName='SHlKAI', accountId='101e5904-64b8-4ad8-a465-2ce83c38de9f', rank=35, wins=425}, EnhancedLeaderBoardEntry{displayName='MLkarasawa', accountId='a3c6290a-5ece-4142-a313-8d4ea983157a', rank=36, wins=418}, EnhancedLeaderBoardEntry{displayName='g000dn on twitch', accountId='6ac8e950-ae23-4de6-800b-70db4767ab55', rank=37, wins=414}, EnhancedLeaderBoardEntry{displayName='marr0wak.twitch', accountId='c5b44b49-35e8-44b9-b5e4-963f158a35a1', rank=38, wins=413}, EnhancedLeaderBoardEntry{displayName='Pervy-', accountId='2886f616-8fb0-4169-bc66-bdcc8efb827d', rank=39, wins=413}, EnhancedLeaderBoardEntry{displayName='TwitchToNiicLive', accountId='ca9ee597-ebf7-4a04-8c74-ab7bb6246e59', rank=40, wins=411}, EnhancedLeaderBoardEntry{displayName='FI.FritoL', accountId='a0c026eb-67bb-4d47-939e-0330ee2b5560', rank=41, wins=410}, EnhancedLeaderBoardEntry{displayName='Semm1234', accountId='a70fa918-5cbd-49bc-83fb-7bcad313480b', rank=42, wins=403}, EnhancedLeaderBoardEntry{displayName='Τfue', accountId='66de7858-19ed-4c83-a994-6b987de773a3', rank=43, wins=403}, EnhancedLeaderBoardEntry{displayName='kwént', accountId='1e2c8d81-0ddb-4ea0-8705-e57f5c2a2b8f', rank=44, wins=398}, EnhancedLeaderBoardEntry{displayName='DouYuTv丶月无痕', accountId='a943e623-58c5-48d9-b51d-2b068e213e23', rank=45, wins=396}, EnhancedLeaderBoardEntry{displayName='epiqueness', accountId='af0797d2-e962-4390-964e-8825b4d81676', rank=46, wins=393}, EnhancedLeaderBoardEntry{displayName='Venndetta.', accountId='6d2d3306-5930-4669-a9b0-0ff00ed8f82a', rank=47, wins=387}, EnhancedLeaderBoardEntry{displayName='Вlind', accountId='577e9436-3250-43d9-9f1a-612d16ff7497', rank=48, wins=386}, EnhancedLeaderBoardEntry{displayName='BüzzyGOD', accountId='72bd31b3-e5ac-4f30-8ef0-88c2520c0989', rank=49, wins=381}, EnhancedLeaderBoardEntry{displayName='twitch.markplayz', accountId='069bb219-fcba-4eb5-8126-f0ec729315aa', rank=50, wins=380}]}`
### Retrieve raw wins leaderboard
```java
import io.github.robertograham.fortniteapirestclient.FortniteApiRestClient;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.domain.constant.PartyType;
import io.github.robertograham.fortniteapirestclient.domain.constant.Platform;
import io.github.robertograham.fortniteapirestclient.domain.constant.StatWindow;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FortniteApiRestClient fortniteApiRestClient = FortniteApiRestClient.builder(new Credentials("epicGamesEmailAddress", "epicGamesPassword", "epicGamesLauncherToken", "fortniteClientToken"))
                .build();

        fortniteApiRestClient.winsLeaderBoard(Platform.PC, PartyType.SOLO, StatWindow.WEEKLY, 100)
                .thenAcceptAsync(System.out::println)
                .get();
    }
}
```
Will print

`LeaderBoard{statName='br_placetop1_pc_m0_p2', statWindow='weekly', entries=[LeaderBoardEntry{accountId='385c4d9a-b7e3-498d-b533-ff4d2d9f4c5b', value=915, rank=1}, LeaderBoardEntry{accountId='155234bb-adaa-4e81-99a7-b2d413722290', value=811, rank=2}, LeaderBoardEntry{accountId='c083d220-0d65-4b25-a87c-0c48cb76c902', value=791, rank=3}, LeaderBoardEntry{accountId='0041d08b-edc5-48d9-a223-0c4a28550594', value=776, rank=4}, LeaderBoardEntry{accountId='6f5c77ad-ef1c-4e47-bc33-f1f0c8b4b263', value=738, rank=5}, LeaderBoardEntry{accountId='e94c3e05-2844-4339-8803-285171550b45', value=701, rank=6}, LeaderBoardEntry{accountId='13b3c774-20da-4101-a213-e1f646b316a9', value=675, rank=7}, LeaderBoardEntry{accountId='cfd16ec5-4126-497c-a574-85c1ee1987dc', value=629, rank=8}, LeaderBoardEntry{accountId='b94176db-4c25-4f90-99fb-2bd8e8ae0f94', value=627, rank=9}, LeaderBoardEntry{accountId='a9467569-462d-4149-bc43-8550c03a45c9', value=624, rank=10}, LeaderBoardEntry{accountId='160376f1-a670-4c1b-b260-ce7b2bf94549', value=604, rank=11}, LeaderBoardEntry{accountId='1da00db9-d5ae-40fa-925f-e48a92bfcd09', value=590, rank=12}, LeaderBoardEntry{accountId='ffbb0eff-7cf0-433f-8cbf-9b5b30d57202', value=559, rank=13}, LeaderBoardEntry{accountId='bd98e3aa-14d4-4c46-9417-827242e0105c', value=550, rank=14}, LeaderBoardEntry{accountId='f1081995-d117-471d-860e-5eb41275975c', value=542, rank=15}, LeaderBoardEntry{accountId='28bad584-d9aa-440b-99ec-488bbd3d4e72', value=534, rank=16}, LeaderBoardEntry{accountId='501aff48-7767-4bbc-8350-a7b190db2ec3', value=532, rank=17}, LeaderBoardEntry{accountId='1169e1f9-9c1a-4cb6-ba77-282c6d84eb74', value=532, rank=18}, LeaderBoardEntry{accountId='54de145b-5873-4f48-8994-dd008e30f26a', value=520, rank=19}, LeaderBoardEntry{accountId='b5dd0491-ee8e-4e15-b32e-f8e704b47dbe', value=507, rank=20}, LeaderBoardEntry{accountId='5359db25-7029-4c59-b6ec-8f57e816f6a7', value=497, rank=21}, LeaderBoardEntry{accountId='09bd41d2-a44c-46d4-97c4-ffb6dd368981', value=494, rank=22}, LeaderBoardEntry{accountId='afeca5d0-401f-4640-9095-b81510c265ac', value=488, rank=23}, LeaderBoardEntry{accountId='2b6d451f-f196-401d-b56c-7f1ba41f63fe', value=484, rank=24}, LeaderBoardEntry{accountId='0aa6c0ae-745b-440d-b246-95085002e053', value=467, rank=25}, LeaderBoardEntry{accountId='f5b23934-2e7b-490d-86c9-3a5db53abf06', value=463, rank=26}, LeaderBoardEntry{accountId='93cfb726-aebb-4eb0-a5ce-4a0ea42d3498', value=462, rank=27}, LeaderBoardEntry{accountId='0247ee0d-eae2-432f-8113-3edaa2ae8e63', value=455, rank=28}, LeaderBoardEntry{accountId='1ef002fc-41b7-46e2-afb4-ba3b23e1afad', value=451, rank=29}, LeaderBoardEntry{accountId='d4a43646-306c-4dc5-8f53-49d89c0e9045', value=451, rank=30}, LeaderBoardEntry{accountId='70639c8f-de7d-4a25-a0ad-09ecd5a2b5b6', value=441, rank=31}, LeaderBoardEntry{accountId='ba5be2d1-7b42-4b8e-a681-3bf84648e15f', value=439, rank=32}, LeaderBoardEntry{accountId='be5497b1-0d14-4996-86bc-970130fb38cc', value=428, rank=33}, LeaderBoardEntry{accountId='6feb4bd8-85b4-4bf8-a6ce-3b986d35407f', value=426, rank=34}, LeaderBoardEntry{accountId='101e5904-64b8-4ad8-a465-2ce83c38de9f', value=425, rank=35}, LeaderBoardEntry{accountId='a3c6290a-5ece-4142-a313-8d4ea983157a', value=418, rank=36}, LeaderBoardEntry{accountId='6ac8e950-ae23-4de6-800b-70db4767ab55', value=414, rank=37}, LeaderBoardEntry{accountId='c5b44b49-35e8-44b9-b5e4-963f158a35a1', value=413, rank=38}, LeaderBoardEntry{accountId='2886f616-8fb0-4169-bc66-bdcc8efb827d', value=413, rank=39}, LeaderBoardEntry{accountId='ca9ee597-ebf7-4a04-8c74-ab7bb6246e59', value=411, rank=40}, LeaderBoardEntry{accountId='a0c026eb-67bb-4d47-939e-0330ee2b5560', value=410, rank=41}, LeaderBoardEntry{accountId='a70fa918-5cbd-49bc-83fb-7bcad313480b', value=403, rank=42}, LeaderBoardEntry{accountId='66de7858-19ed-4c83-a994-6b987de773a3', value=403, rank=43}, LeaderBoardEntry{accountId='1e2c8d81-0ddb-4ea0-8705-e57f5c2a2b8f', value=398, rank=44}, LeaderBoardEntry{accountId='a943e623-58c5-48d9-b51d-2b068e213e23', value=396, rank=45}, LeaderBoardEntry{accountId='af0797d2-e962-4390-964e-8825b4d81676', value=393, rank=46}, LeaderBoardEntry{accountId='6d2d3306-5930-4669-a9b0-0ff00ed8f82a', value=387, rank=47}, LeaderBoardEntry{accountId='577e9436-3250-43d9-9f1a-612d16ff7497', value=386, rank=48}, LeaderBoardEntry{accountId='72bd31b3-e5ac-4f30-8ef0-88c2520c0989', value=381, rank=49}, LeaderBoardEntry{accountId='069bb219-fcba-4eb5-8126-f0ec729315aa', value=380, rank=50}]}`


