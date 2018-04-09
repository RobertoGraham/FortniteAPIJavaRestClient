package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

public class GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest {

    private String accountId;
    private String platform;
    private String authHeaderValue;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    public void setAuthHeaderValue(String authHeaderValue) {
        this.authHeaderValue = authHeaderValue;
    }

    public static class Builder {

        private GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;

        private Builder() {
            getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest = new GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder accountId(String accountId) {
            getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.setAccountId(accountId);

            return this;
        }

        public Builder authHeaderValue(String authHeaderValue) {
            getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        public Builder platform(String platform) {
            getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest.setPlatform(platform);

            return this;
        }

        public GetSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest build() {
            return getSoloDuoSquadBattleRoyaleStatisticsByPlatformRequest;
        }
    }
}
