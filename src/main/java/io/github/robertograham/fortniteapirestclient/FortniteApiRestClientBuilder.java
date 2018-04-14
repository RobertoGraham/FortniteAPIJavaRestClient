package io.github.robertograham.fortniteapirestclient;

import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.util.Builder;
import io.github.robertograham.fortniteapirestclient.util.ResponseHandlerProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Objects;
import java.util.concurrent.Executors;

public class FortniteApiRestClientBuilder implements Builder<FortniteApiRestClient> {

    private final Credentials credentials;
    private boolean autoLoginDisabled;

    FortniteApiRestClientBuilder(Credentials credentials) {
        this.credentials = Objects.requireNonNull(credentials, "Credentials must not be null");
    }

    public FortniteApiRestClientBuilder disableAutoLogin() {
        autoLoginDisabled = true;

        return this;
    }

    @Override
    public FortniteApiRestClient build() {
        ResponseHandlerProvider responseHandlerProvider = ResponseHandlerProvider.builder()
                .build();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        return new FortniteApiRestClient(
                credentials,
                httpClient,
                responseHandlerProvider,
                Executors.newScheduledThreadPool(1),
                autoLoginDisabled
        );
    }
}
