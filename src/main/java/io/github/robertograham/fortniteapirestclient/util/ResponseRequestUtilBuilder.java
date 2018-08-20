package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.Optional;

public class ResponseRequestUtilBuilder implements Builder<ResponseRequestUtil> {

    private ObjectMapper objectMapper;

    ResponseRequestUtilBuilder() {
    }

    public ResponseRequestUtilBuilder objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper mustn't be null");

        return this;
    }

    @Override
    public ResponseRequestUtil build() {
        return new ResponseRequestUtil(Optional.ofNullable(objectMapper)
                .orElse(new ObjectMapper().findAndRegisterModules()));
    }
}
