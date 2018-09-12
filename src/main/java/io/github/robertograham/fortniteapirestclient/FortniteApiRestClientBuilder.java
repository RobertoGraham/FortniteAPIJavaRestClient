package io.github.robertograham.fortniteapirestclient;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.apache.ApacheHttpTransport;
import io.github.robertograham.fortniteapirestclient.domain.Credentials;
import io.github.robertograham.fortniteapirestclient.util.Builder;

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
        HttpRequestFactory httpRequestFactory = new ApacheHttpTransport()
                .createRequestFactory(new EpicGamesRequestInitializer());

        return new FortniteApiRestClient(
                credentials,
                httpRequestFactory,
                Executors.newScheduledThreadPool(1),
                autoLoginDisabled
        );
    }
}
