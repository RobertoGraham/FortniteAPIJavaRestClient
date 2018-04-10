package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;

public class ObjectMapperResponseHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private static final ResponseHandler<String> responseHandler = new BasicResponseHandler();

    public static <T> ResponseHandler<T> thatProduces(Class<T> clazz) {
        return response -> objectMapper.readValue(responseHandler.handleResponse(response), clazz);
    }
}
