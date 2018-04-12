package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.util.Objects;

public class ObjectMappingResponseHandlerProducerBuilder implements Builder<ObjectMappingResponseHandlerProducer> {

    private ObjectMapper objectMapper;

    ObjectMappingResponseHandlerProducerBuilder() {
    }

    public ObjectMappingResponseHandlerProducerBuilder objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper mustn't be null");

        return this;
    }

    @Override
    public ObjectMappingResponseHandlerProducer build() {
        if (objectMapper == null)
            objectMapper = new ObjectMapper().findAndRegisterModules();

        return new ObjectMappingResponseHandlerProducer(objectMapper);
    }
}
