package io.github.robertograham.fortniteapirestclient.domain;

public class Credentials {

    private String epicGamesEmailAddress;
    private String epicGamesPassword;
    private String epicGamesLauncherToken;
    private String fortniteClientToken;

    public String getEpicGamesEmailAddress() {
        return epicGamesEmailAddress;
    }

    public void setEpicGamesEmailAddress(String epicGamesEmailAddress) {
        this.epicGamesEmailAddress = epicGamesEmailAddress;
    }

    public String getEpicGamesPassword() {
        return epicGamesPassword;
    }

    public void setEpicGamesPassword(String epicGamesPassword) {
        this.epicGamesPassword = epicGamesPassword;
    }

    public String getEpicGamesLauncherToken() {
        return epicGamesLauncherToken;
    }

    public void setEpicGamesLauncherToken(String epicGamesLauncherToken) {
        this.epicGamesLauncherToken = epicGamesLauncherToken;
    }

    public String getFortniteClientToken() {
        return fortniteClientToken;
    }

    public void setFortniteClientToken(String fortniteClientToken) {
        this.fortniteClientToken = fortniteClientToken;
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

    public static class Builder {

        private Credentials credentials;

        private Builder() {
            credentials = new Credentials();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder epicGamesEmailAddress(String epicGamesEmailAddress) {
            credentials.setEpicGamesEmailAddress(epicGamesEmailAddress);

            return this;
        }

        public Builder epicGamesPassword(String epicGamesPassword) {
            credentials.setEpicGamesPassword(epicGamesPassword);

            return this;
        }

        public Builder epicGamesLauncherToken(String epicGamesLauncherToken) {
            credentials.setEpicGamesLauncherToken(epicGamesLauncherToken);

            return this;
        }

        public Builder fortniteClientToken(String fortniteClientToken) {
            credentials.setFortniteClientToken(fortniteClientToken);

            return this;
        }

        public Credentials build() {
            return credentials;
        }
    }
}
