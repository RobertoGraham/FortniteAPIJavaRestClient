package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.service.account.impl.AccountServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.authentication.impl.AuthenticationServiceImpl;
import io.github.robertograham.fortniteapirestclient.service.statistics.impl.StatisticsServiceImpl;
import io.github.robertograham.fortniteapirestclient.util.Builder;

import java.util.Objects;
import java.util.concurrent.Executors;

public class FortniteApiRestClientBuilder implements Builder<FortniteApiRestClient> {

    private final Credentials credentials;

    FortniteApiRestClientBuilder(Credentials credentials) {
        this.credentials = Objects.requireNonNull(credentials, "Credentials must not be null");
    }

    @Override
    public FortniteApiRestClient build() {
        return new FortniteApiRestClient(
                credentials,
                new AuthenticationServiceImpl(),
                new AccountServiceImpl(),
                new StatisticsServiceImpl(),
                Executors.newScheduledThreadPool(1)
        );
    }
}
