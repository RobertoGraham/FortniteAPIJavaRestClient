package io.github.robertograham.fortniteapirestclient.service.statistics.model.request;

import io.github.robertograham.fortniteapirestclient.domain.enumeration.StatWindow;
import io.github.robertograham.fortniteapirestclient.util.Request;

import java.util.Objects;

public class GetBattleRoyaleStatisticsRequest extends Request {

    private final String accountId;
    private final StatWindow window;
    private final String authorization;

    GetBattleRoyaleStatisticsRequest(String accountId, StatWindow window, String authorization) {
        this.accountId = accountId;
        this.window = window;
        this.authorization = authorization;
    }

    public static GetBattleRoyaleStatisticsRequestBuilder builder() {
        return new GetBattleRoyaleStatisticsRequestBuilder();
    }

    public String getAccountId() {
        return accountId;
    }

    public StatWindow getWindow() {
        return window;
    }

    public String getAuthorization() {
        return authorization;
    }

    @Override
    public String toString() {
        return "GetBattleRoyaleStatisticsRequest{" +
                "accountId='" + accountId + '\'' +
                ", window='" + window + '\'' +
                ", authorization='" + authorization + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if (!(object instanceof GetBattleRoyaleStatisticsRequest))
            return false;

        GetBattleRoyaleStatisticsRequest getBattleRoyaleStatisticsRequest = (GetBattleRoyaleStatisticsRequest) object;

        return Objects.equals(accountId, getBattleRoyaleStatisticsRequest.accountId) &&
                Objects.equals(window, getBattleRoyaleStatisticsRequest.window) &&
                Objects.equals(authorization, getBattleRoyaleStatisticsRequest.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, window, authorization);
    }
}
