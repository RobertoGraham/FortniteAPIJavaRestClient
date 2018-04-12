package io.github.robertograham.fortniteapirestclient.domain;

public class Credentials {

    private final String epicGamesEmailAddress;
    private final String epicGamesPassword;
    private final String epicGamesLauncherToken;
    private final String fortniteClientToken;

    public Credentials(String epicGamesEmailAddress, String epicGamesPassword, String epicGamesLauncherToken, String fortniteClientToken) {
        this.epicGamesEmailAddress = epicGamesEmailAddress;
        this.epicGamesPassword = epicGamesPassword;
        this.epicGamesLauncherToken = epicGamesLauncherToken;
        this.fortniteClientToken = fortniteClientToken;
    }

    public String getEpicGamesEmailAddress() {
        return epicGamesEmailAddress;
    }

    public String getEpicGamesPassword() {
        return epicGamesPassword;
    }

    public String getEpicGamesLauncherToken() {
        return epicGamesLauncherToken;
    }

    public String getFortniteClientToken() {
        return fortniteClientToken;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "epicGamesEmailAddress='" + epicGamesEmailAddress + '\'' +
                ", epicGamesPassword='" + epicGamesPassword + '\'' +
                ", epicGamesLauncherToken='" + epicGamesLauncherToken + '\'' +
                ", fortniteClientToken='" + fortniteClientToken + '\'' +
                '}';
    }
}
