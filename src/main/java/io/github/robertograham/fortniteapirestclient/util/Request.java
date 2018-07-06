package io.github.robertograham.fortniteapirestclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {

    private final Logger log;

    public Request() {
        log = LoggerFactory.getLogger(getClass());
    }

    public void log() {
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "Request{}";
    }
}
