package io.github.robertograham.fortniteapirestclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {

    private final Logger LOG;

    public Request() {
        LOG = LoggerFactory.getLogger(getClass());
    }

    public void log() {
        LOG.debug(toString());
    }

    @Override
    public String toString() {
        return "Request{}";
    }
}
