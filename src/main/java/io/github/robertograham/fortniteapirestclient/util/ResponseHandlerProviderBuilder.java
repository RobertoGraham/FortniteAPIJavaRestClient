package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

public class ResponseHandlerProviderBuilder implements Builder<ResponseHandlerProvider> {

    private ObjectMapper objectMapper;

    ResponseHandlerProviderBuilder() {
    }

    public ResponseHandlerProviderBuilder objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper mustn't be null");

        return this;
    }

    @Override
    public ResponseHandlerProvider build() {
        return new ResponseHandlerProvider(Optional.ofNullable(objectMapper)
                .orElse(new ObjectMapper().findAndRegisterModules()));
    }
}
