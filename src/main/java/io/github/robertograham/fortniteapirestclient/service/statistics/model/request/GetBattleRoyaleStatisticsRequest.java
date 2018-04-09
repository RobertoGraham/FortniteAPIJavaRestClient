package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

public class GetBattleRoyaleStatisticsRequest {

    private String accountId;
    private String authHeaderValue;

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

        private GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest;

        private Builder() {
            getBattleRoyaleStatisticsRequest = new GetBattleRoyaleStatisticsRequest();
        }

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder accountId(String accountId) {
            getBattleRoyaleStatisticsRequest.setAccountId(accountId);

            return this;
        }

        public Builder authHeaderValue(String authHeaderValue) {
            getBattleRoyaleStatisticsRequest.setAuthHeaderValue(authHeaderValue);

            return this;
        }

        public GetBattleRoyaleStatisticsRequest build() {
            return getBattleRoyaleStatisticsRequest;
        }
    }
}
