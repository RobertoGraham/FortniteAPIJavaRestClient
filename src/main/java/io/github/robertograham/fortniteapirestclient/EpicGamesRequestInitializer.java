package io.github.robertograham.fortniteapirestclient;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpicGamesRequestInitializer implements HttpRequestInitializer, HttpUnsuccessfulResponseHandler {

    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final Logger LOG = LoggerFactory.getLogger(EpicGamesRequestInitializer.class);

    @Override
    public void initialize(HttpRequest httpRequest) {
        httpRequest.setUnsuccessfulResponseHandler(this);
        httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
    }

    @Override
    public boolean handleResponse(HttpRequest httpRequest, HttpResponse httpResponse, boolean b) {
        LOG.error("{} {}", httpResponse.getStatusCode(), httpResponse.getStatusMessage());
        return false;
    }
}
