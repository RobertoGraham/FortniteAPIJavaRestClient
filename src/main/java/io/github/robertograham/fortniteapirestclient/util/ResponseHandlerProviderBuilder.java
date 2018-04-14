package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

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
        if (objectMapper == null)
            objectMapper = new ObjectMapper().findAndRegisterModules();

        return new ResponseHandlerProvider(objectMapper);
    }
}
