package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ObjectMappingResponseHandlerProducer {

    private final ObjectMapper objectMapper;

    ObjectMappingResponseHandlerProducer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static ObjectMappingResponseHandlerProducerBuilder builder() {
        return new ObjectMappingResponseHandlerProducerBuilder();
    }

    public <T> ResponseHandler<T> produceFor(Class<T> clazz) {
        return response -> {
            StatusLine statusLine = response.getStatusLine();

            HttpEntity httpEntity = response.getEntity();

            if (statusLine.getStatusCode() >= 300) {
                String responseString = null;

                try {
                    responseString = httpEntity == null ? null : EntityUtils.toString(httpEntity);
                } catch (IOException ignored) {
                }

                EntityUtils.consume(httpEntity);

                throw responseString == null ?
                        new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase())
                        : new FortniteApiErrorException(objectMapper.readValue(responseString, FortniteApiError.class), statusLine.getStatusCode());
            }

            return httpEntity == null ? null : objectMapper.readValue(EntityUtils.toString(httpEntity), clazz);
        };
    }
}
