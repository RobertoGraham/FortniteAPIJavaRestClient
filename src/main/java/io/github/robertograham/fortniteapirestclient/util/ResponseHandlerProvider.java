package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class ResponseHandlerProvider {

    private final ObjectMapper objectMapper;
    private final ResponseHandler<String> stringResponseHandler;

    ResponseHandlerProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.stringResponseHandler = stringResponseHandler();
    }

    public static ResponseHandlerProviderBuilder builder() {
        return new ResponseHandlerProviderBuilder();
    }

    private ResponseHandler<String> stringResponseHandler() {
        return response -> {
            HttpEntity entity = response.getEntity();

            String body = entity != null ? EntityUtils.toString(entity) : null;

            int status = response.getStatusLine().getStatusCode();

            if (status >= 200 && status < 300)
                return body;
            else
                throw body != null && !body.isEmpty() ?
                        new FortniteApiErrorException(objectMapper.readValue(body, FortniteApiError.class), status)
                        : new ClientProtocolException("Unexpected response status: " + status);

        };
    }

    public <T> ResponseHandler<T> handlerFor(Class<T> clazz) {
        return response -> objectMapper.readValue(stringResponseHandler.handleResponse(response), clazz);
    }

    public ResponseHandler<String> stringHandler() {
        return stringResponseHandler;
    }
}
