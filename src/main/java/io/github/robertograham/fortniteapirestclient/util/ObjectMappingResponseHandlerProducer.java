package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ResponseHandler;

public class ObjectMappingResponseHandlerProducer {

    private final ObjectMapper objectMapper;
    private final ResponseHandler<String> responseHandler;

    ObjectMappingResponseHandlerProducer(ObjectMapper objectMapper, ResponseHandler<String> responseHandler) {
        this.objectMapper = objectMapper;
        this.responseHandler = responseHandler;
    }

    public static ObjectMappingResponseHandlerProducerBuilder builder() {
        return new ObjectMappingResponseHandlerProducerBuilder();
    }

    public <T> ResponseHandler<T> produceFor(Class<T> clazz) {
        return response -> objectMapper.readValue(responseHandler.handleResponse(response), clazz);
    }
}
