package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import java.util.Objects;

public class GetBattleRoyaleStatisticsRequest {

    private final String accountId;
    private final String authHeaderValue;

    GetBattleRoyaleStatisticsRequest(String accountId, String authHeaderValue) {
        this.accountId = accountId;
        this.authHeaderValue = authHeaderValue;
    }

    public static GetBattleRoyaleStatisticsRequestBuilder builder() {
        return new GetBattleRoyaleStatisticsRequestBuilder();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAuthHeaderValue() {
        return authHeaderValue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (object == null || getClass() != object.getClass())
            return false;

        GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest = (GetBattleRoyaleStatisticsRequest) object;

        return Objects.equals(accountId, getBattleRoyaleStatisticsRequest.accountId) &&
                Objects.equals(authHeaderValue, getBattleRoyaleStatisticsRequest.authHeaderValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, authHeaderValue);
    }
}
