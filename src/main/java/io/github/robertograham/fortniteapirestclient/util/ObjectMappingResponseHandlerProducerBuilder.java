package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

import java.util.Objects;

public class ObjectMappingResponseHandlerProducerBuilder implements Builder<ObjectMappingResponseHandlerProducer> {

    private ObjectMapper objectMapper;
    private ResponseHandler<String> responseHandler;

    ObjectMappingResponseHandlerProducerBuilder() {
    }

    public ObjectMappingResponseHandlerProducerBuilder objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper mustn't be null");

        return this;
    }

    public ObjectMappingResponseHandlerProducerBuilder responseHandler(ResponseHandler<String> responseHandler) {
        this.responseHandler = Objects.requireNonNull(responseHandler, "Response handler mustn't be null");

        return this;
    }

    @Override
    public ObjectMappingResponseHandlerProducer build() {
        if (objectMapper == null)
            objectMapper = new ObjectMapper().findAndRegisterModules();

        if (responseHandler == null)
            responseHandler = new BasicResponseHandler();

        return new ObjectMappingResponseHandlerProducer(objectMapper, responseHandler);
    }
}
