package io.github.robertograham.fortniteapirestclient.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class StringResponseHandler implements ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse httpResponse) throws IOException {
        int status = httpResponse.getStatusLine().getStatusCode();

        if (status >= 200 && status < 300) {
            HttpEntity responseEntity = httpResponse.getEntity();

            return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
        } else
            return null;
    }
}
